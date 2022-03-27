package org.jiyitech.modules.smartfuel.modules.device.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jiyitech.modules.smartfuel.modules.device.entity.SmartfuelDevice;
import org.jiyitech.modules.smartfuel.modules.device.mapper.SmartfuelDeviceAttributeMapper;
import org.jiyitech.modules.smartfuel.modules.device.mapper.SmartfuelDeviceMapper;
import org.jiyitech.modules.smartfuel.modules.device.mapper.SmartfuelDeviceTwinMapper;
import org.jiyitech.modules.smartfuel.modules.device.service.ISmartfuelDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 设备
 * @Author: jeecg-boot
 * @Date: 2022-03-27
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
        for (Serializable id : idList) {
            smartfuelDeviceTwinMapper.deleteByMainId(id.toString());
            smartfuelDeviceAttributeMapper.deleteByMainId(id.toString());
            smartfuelDeviceMapper.deleteById(id);
        }
    }

}
