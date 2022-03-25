package org.jiyitech.modules.smartfuel.device.mapper;

import java.util.List;
import org.jiyitech.modules.smartfuel.device.entity.SmartfuelDeviceTwin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 孪生属性
 * @Author: jeecg-boot
 * @Date:   2022-03-24
 * @Version: V1.0
 */
public interface SmartfuelDeviceTwinMapper extends BaseMapper<SmartfuelDeviceTwin> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<SmartfuelDeviceTwin> selectByMainId(@Param("mainId") String mainId);
}
