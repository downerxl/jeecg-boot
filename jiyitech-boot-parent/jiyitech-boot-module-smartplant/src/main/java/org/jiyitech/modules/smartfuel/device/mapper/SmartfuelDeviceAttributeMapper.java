package org.jiyitech.modules.smartfuel.device.mapper;

import java.util.List;
import org.jiyitech.modules.smartfuel.device.entity.SmartfuelDeviceAttribute;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 设备属性
 * @Author: jeecg-boot
 * @Date:   2022-03-24
 * @Version: V1.0
 */
public interface SmartfuelDeviceAttributeMapper extends BaseMapper<SmartfuelDeviceAttribute> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<SmartfuelDeviceAttribute> selectByMainId(@Param("mainId") String mainId);
}
