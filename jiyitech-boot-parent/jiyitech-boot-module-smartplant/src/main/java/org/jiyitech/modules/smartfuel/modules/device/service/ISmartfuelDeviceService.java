package org.jiyitech.modules.smartfuel.modules.device.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jiyitech.modules.smartfuel.modules.device.entity.SmartfuelDevice;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 设备
 * @Author: jeecg-boot
 * @Date: 2022-03-27
 * @Version: V1.0
 */
public interface ISmartfuelDeviceService extends IService<SmartfuelDevice> {

    /**
     * 删除一对多
     */
    public void delMain(String id);

    /**
     * 批量删除一对多
     */
    public void delBatchMain(Collection<? extends Serializable> idList);


}
