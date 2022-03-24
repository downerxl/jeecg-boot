package org.jiyitech.modules.smartfuel.systemInfo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jiyitech.modules.smartfuel.systemInfo.entity.SmartfuelSystemInfo;
import org.jiyitech.modules.smartfuel.systemInfo.mapper.SmartfuelSystemInfoMapper;
import org.jiyitech.modules.smartfuel.systemInfo.service.SmartfuelSystemInfoService;
import org.springframework.stereotype.Service;

/**
 * @Description: system_info
 * @Author: jeecg-boot
 * @Date: 2022-03-09
 * @Version: V1.0
 */
@Service
public class SmartfuelSystemInfoServiceImpl extends ServiceImpl<SmartfuelSystemInfoMapper, SmartfuelSystemInfo> implements SmartfuelSystemInfoService {


    @Override
    public SmartfuelSystemInfo getSystemInfo() {
        return this.baseMapper.getSystemInfo();
    }

}
