package org.jiyitech.modules.smartfuel.modules.device.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jiyitech.modules.smartfuel.modules.device.entity.SmartfuelDeviceTwin;
import org.jiyitech.modules.smartfuel.modules.device.mapper.SmartfuelDeviceTwinMapper;
import org.jiyitech.modules.smartfuel.modules.device.service.ISmartfuelDeviceTwinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 孪生属性
 * @Author: jeecg-boot
 * @Date: 2022-03-27
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
