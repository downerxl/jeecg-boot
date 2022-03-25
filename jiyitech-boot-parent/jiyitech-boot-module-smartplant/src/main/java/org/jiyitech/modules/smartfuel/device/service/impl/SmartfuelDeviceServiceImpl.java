package org.jiyitech.modules.smartfuel.device.service.impl;

import org.jiyitech.modules.smartfuel.device.entity.SmartfuelDevice;
import org.jiyitech.modules.smartfuel.device.entity.SmartfuelDeviceTwin;
import org.jiyitech.modules.smartfuel.device.entity.SmartfuelDeviceAttribute;
import org.jiyitech.modules.smartfuel.device.mapper.SmartfuelDeviceTwinMapper;
import org.jiyitech.modules.smartfuel.device.mapper.SmartfuelDeviceAttributeMapper;
import org.jiyitech.modules.smartfuel.device.mapper.SmartfuelDeviceMapper;
import org.jiyitech.modules.smartfuel.device.service.ISmartfuelDeviceService;
import org.jiyitech.modules.smartfuel.device.vo.DebiceTwinDataVo;
import org.jiyitech.modules.smartfuel.entity.InfluxDBEntity;
import org.jiyitech.modules.smartfuel.util.InfluxDBUtil;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 设备
 * @Author: jeecg-boot
 * @Date:   2022-03-24
 * @Version: V1.0
 */
@Service
public class SmartfuelDeviceServiceImpl extends ServiceImpl<SmartfuelDeviceMapper, SmartfuelDevice> implements ISmartfuelDeviceService {

	@Autowired
	private SmartfuelDeviceMapper smartfuelDeviceMapper;
	@Autowired
	private SmartfuelDeviceTwinMapper smartfuelDeviceTwinMapper;
	@Autowired
	private SmartfuelDeviceAttributeMapper smartfuelDeviceAttributeMapper;
	@Autowired
	private InfluxDBUtil influxDBUtil;
	
	@Override
	@Transactional
	public void saveMain(SmartfuelDevice smartfuelDevice, List<SmartfuelDeviceTwin> smartfuelDeviceTwinList,List<SmartfuelDeviceAttribute> smartfuelDeviceAttributeList) {
		smartfuelDeviceMapper.insert(smartfuelDevice);
		if(smartfuelDeviceTwinList!=null && smartfuelDeviceTwinList.size()>0) {
			for(SmartfuelDeviceTwin entity:smartfuelDeviceTwinList) {
				//外键设置
				entity.setDeviceId(smartfuelDevice.getId());
				smartfuelDeviceTwinMapper.insert(entity);
			}
		}
		if(smartfuelDeviceAttributeList!=null && smartfuelDeviceAttributeList.size()>0) {
			for(SmartfuelDeviceAttribute entity:smartfuelDeviceAttributeList) {
				//外键设置
				entity.setDeviceId(smartfuelDevice.getId());
				smartfuelDeviceAttributeMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(SmartfuelDevice smartfuelDevice,List<SmartfuelDeviceTwin> smartfuelDeviceTwinList,List<SmartfuelDeviceAttribute> smartfuelDeviceAttributeList) {
		smartfuelDeviceMapper.updateById(smartfuelDevice);
		
		//1.先删除子表数据
		smartfuelDeviceTwinMapper.deleteByMainId(smartfuelDevice.getId());
		smartfuelDeviceAttributeMapper.deleteByMainId(smartfuelDevice.getId());
		
		//2.子表数据重新插入
		if(smartfuelDeviceTwinList!=null && smartfuelDeviceTwinList.size()>0) {
			for(SmartfuelDeviceTwin entity:smartfuelDeviceTwinList) {
				//外键设置
				entity.setDeviceId(smartfuelDevice.getId());
				smartfuelDeviceTwinMapper.insert(entity);
			}
		}
		if(smartfuelDeviceAttributeList!=null && smartfuelDeviceAttributeList.size()>0) {
			for(SmartfuelDeviceAttribute entity:smartfuelDeviceAttributeList) {
				//外键设置
				entity.setDeviceId(smartfuelDevice.getId());
				smartfuelDeviceAttributeMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		smartfuelDeviceTwinMapper.deleteByMainId(id);
		smartfuelDeviceAttributeMapper.deleteByMainId(id);
		smartfuelDeviceMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			smartfuelDeviceTwinMapper.deleteByMainId(id.toString());
			smartfuelDeviceAttributeMapper.deleteByMainId(id.toString());
			smartfuelDeviceMapper.deleteById(id);
		}
	}

	@Override
	public HashMap<String, List<Double>> getHistoryCurve(DebiceTwinDataVo debiceTwinDataVo) {
		//计算开始时间
		LocalDateTime startTime = LocalDateTime.now().plusMinutes(-debiceTwinDataVo.getTime());
		//tag标签集合封装
		ArrayList<String> tagList = new ArrayList<>();
		tagList.add(debiceTwinDataVo.getTag());
		//调用influxDB查询方法
		HashMap<String, List<InfluxDBEntity>> result = influxDBUtil.Search(startTime, LocalDateTime.now(), tagList, "", debiceTwinDataVo.getSource());
		//封装后的结果集
		HashMap<String, List<Double>> returnData = new HashMap<>();
		for(Map.Entry<String, List<InfluxDBEntity>> entry : result.entrySet()){
			//取结果集重新封装
			List<Double> collect = entry.getValue().stream().map(item -> {
				return item.getValue();
			}).collect(Collectors.toList());
			returnData.put(entry.getKey(),collect);
		}
		return returnData;
	}

	@Override
	public double getDeviceTwinGeRange(DebiceTwinDataVo debiceTwinDataVo) {
		//tag标签集合封装
		String tag = debiceTwinDataVo.getTag();
		ArrayList<String> tagList = new ArrayList<>();
		tagList.add(tag);
		HashMap<String, Long> tagRangeMap = influxDBUtil.GetRangeUpTimeSpan(dateToLocalDateTimeUtil(debiceTwinDataVo.getStartTime()),
				dateToLocalDateTimeUtil(debiceTwinDataVo.getEndTime()),
				tagList,
				"",
				debiceTwinDataVo.getSource(),
				debiceTwinDataVo.getThreshold().doubleValue(),
				Double.MAX_VALUE);
		return tagRangeMap.get(tag) / 3600.00;
	}

	/**
	 * date格式转换为localDateTime
	 * @param date
	 * @return
	 */
	public LocalDateTime dateToLocalDateTimeUtil(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

}
