package org.jiyitech.modules.smartfuel.modules.device.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jiyitech.modules.smartfuel.modules.device.entity.SmartfuelDeviceTwin;

import java.util.List;

/**
 * @Description: 孪生属性
 * @Author: jeecg-boot
 * @Date: 2022-03-27
 * @Version: V1.0
 */
public interface SmartfuelDeviceTwinMapper extends BaseMapper<SmartfuelDeviceTwin> {

    public boolean deleteByMainId(@Param("mainId") String mainId);

    public List<SmartfuelDeviceTwin> selectByMainId(@Param("mainId") String mainId);

}
