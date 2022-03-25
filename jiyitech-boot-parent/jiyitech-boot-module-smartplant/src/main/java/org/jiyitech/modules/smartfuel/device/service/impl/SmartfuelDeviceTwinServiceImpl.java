package org.jiyitech.modules.smartfuel.device.service.impl;

import org.jiyitech.modules.smartfuel.device.entity.SmartfuelDeviceTwin;
import org.jiyitech.modules.smartfuel.device.mapper.SmartfuelDeviceTwinMapper;
import org.jiyitech.modules.smartfuel.device.service.ISmartfuelDeviceTwinService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 孪生属性
 * @Author: jeecg-boot
 * @Date:   2022-03-24
 * @Version: V1.0
 */
@Service
public class SmartfuelDeviceTwinServiceImpl extends ServiceImpl<SmartfuelDeviceTwinMapper, SmartfuelDeviceTwin> implements ISmartfuelDeviceTwinService {
	
	@Autowired
	private SmartfuelDeviceTwinMapper smartfuelDeviceTwinMapper;
	
	@Override
	public List<SmartfuelDeviceTwin> selectByMainId(String mainId) {
		return smartfuelDeviceTwinMapper.selectByMainId(mainId);
	}
}
