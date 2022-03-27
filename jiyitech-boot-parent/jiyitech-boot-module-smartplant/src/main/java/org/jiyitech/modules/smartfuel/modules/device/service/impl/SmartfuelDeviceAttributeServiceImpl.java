package org.jiyitech.modules.smartfuel.modules.device.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jiyitech.modules.smartfuel.modules.device.entity.SmartfuelDeviceAttribute;
import org.jiyitech.modules.smartfuel.modules.device.mapper.SmartfuelDeviceAttributeMapper;
import org.jiyitech.modules.smartfuel.modules.device.service.ISmartfuelDeviceAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 设备属性
 * @Author: jeecg-boot
 * @Date: 2022-03-27
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
