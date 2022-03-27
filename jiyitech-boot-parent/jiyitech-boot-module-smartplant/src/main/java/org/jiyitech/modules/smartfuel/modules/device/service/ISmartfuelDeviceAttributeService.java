package org.jiyitech.modules.smartfuel.modules.device.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jiyitech.modules.smartfuel.modules.device.entity.SmartfuelDeviceAttribute;

import java.util.List;

/**
 * @Description: 设备属性
 * @Author: jeecg-boot
 * @Date: 2022-03-27
 * @Version: V1.0
 */
public interface ISmartfuelDeviceAttributeService extends IService<SmartfuelDeviceAttribute> {

    public List<SmartfuelDeviceAttribute> selectByMainId(String mainId);
}
