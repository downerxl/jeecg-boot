package org.jiyitech.modules.smartfuel.device.service;

import org.jiyitech.modules.smartfuel.device.entity.SmartfuelDeviceAttribute;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 设备属性
 * @Author: jeecg-boot
 * @Date:   2022-03-24
 * @Version: V1.0
 */
public interface ISmartfuelDeviceAttributeService extends IService<SmartfuelDeviceAttribute> {

	public List<SmartfuelDeviceAttribute> selectByMainId(String mainId);
}
