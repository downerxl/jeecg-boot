package org.jiyitech.modules.smartfuel.systemInfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jiyitech.modules.smartfuel.systemInfo.entity.SmartfuelSystemInfo;

/**
 * @Description: system_info
 * @Author: jeecg-boot
 * @Date: 2022-03-09
 * @Version: V1.0
 */
public interface SmartfuelSystemInfoMapper extends BaseMapper<SmartfuelSystemInfo> {


    SmartfuelSystemInfo getSystemInfo();


}
