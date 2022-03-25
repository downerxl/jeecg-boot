package org.jiyitech.modules.smartfuel.device.service.impl;

import org.jiyitech.modules.smartfuel.device.entity.SmartfuelDeviceAttribute;
import org.jiyitech.modules.smartfuel.device.mapper.SmartfuelDeviceAttributeMapper;
import org.jiyitech.modules.smartfuel.device.service.ISmartfuelDeviceAttributeService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 设备属性
 * @Author: jeecg-boot
 * @Date:   2022-03-24
 * @Version: V1.0
 */
@Service
public class SmartfuelDeviceAttributeServiceImpl extends ServiceImpl<SmartfuelDeviceAttributeMapper, SmartfuelDeviceAttribute> implements ISmartfuelDeviceAttributeService {
	
	@Autowired
	private SmartfuelDeviceAttributeMapper smartfuelDeviceAttributeMapper;
	
	@Override
	public List<SmartfuelDeviceAttribute> selectByMainId(String mainId) {
		return smartfuelDeviceAttributeMapper.selectByMainId(mainId);
	}
}
