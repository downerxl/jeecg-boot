package org.jiyitech.modules.smartfuel.device.service;

import org.jiyitech.modules.smartfuel.device.entity.SmartfuelDeviceTwin;
import org.jiyitech.modules.smartfuel.device.entity.SmartfuelDeviceAttribute;
import org.jiyitech.modules.smartfuel.device.entity.SmartfuelDevice;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jiyitech.modules.smartfuel.device.vo.DebiceTwinDataVo;
import org.jiyitech.modules.smartfuel.entity.InfluxDBEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @Description: 设备
 * @Author: jeecg-boot
 * @Date:   2022-03-24
 * @Version: V1.0
 */
public interface ISmartfuelDeviceService extends IService<SmartfuelDevice> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(SmartfuelDevice smartfuelDevice,List<SmartfuelDeviceTwin> smartfuelDeviceTwinList,List<SmartfuelDeviceAttribute> smartfuelDeviceAttributeList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(SmartfuelDevice smartfuelDevice,List<SmartfuelDeviceTwin> smartfuelDeviceTwinList,List<SmartfuelDeviceAttribute> smartfuelDeviceAttributeList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);


	HashMap<String, List<Double>> getHistoryCurve(DebiceTwinDataVo debiceTwinDataVo);

	/**
	 * 获取debiceTwin指定时间内大于某一个阈值的时长(秒)
	 * @param debiceTwinDataVo 请求数据集
	 * @return Long（单位：小时）
	 */
	double getDeviceTwinGeRange(DebiceTwinDataVo debiceTwinDataVo);
}
