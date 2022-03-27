package org.jiyitech.modules.smartfuel.modules.device.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jiyitech.modules.smartfuel.modules.device.entity.SmartfuelDeviceTwin;

import java.util.List;

/**
 * @Description: 孪生属性
 * @Author: jeecg-boot
 * @Date: 2022-03-27
 * @Version: V1.0
 */
public interface ISmartfuelDeviceTwinService extends IService<SmartfuelDeviceTwin> {

    public List<SmartfuelDeviceTwin> selectByMainId(String mainId);
}
