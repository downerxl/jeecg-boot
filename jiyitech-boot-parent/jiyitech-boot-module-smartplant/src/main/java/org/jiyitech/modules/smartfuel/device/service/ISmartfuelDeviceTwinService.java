package org.jiyitech.modules.smartfuel.device.service;

import org.jiyitech.modules.smartfuel.device.entity.SmartfuelDeviceTwin;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 孪生属性
 * @Author: jeecg-boot
 * @Date:   2022-03-24
 * @Version: V1.0
 */
public interface ISmartfuelDeviceTwinService extends IService<SmartfuelDeviceTwin> {

	public List<SmartfuelDeviceTwin> selectByMainId(String mainId);
}
