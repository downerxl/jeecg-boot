package org.jiyitech.modules.smartfuel.device.vo;

import java.util.List;
import org.jiyitech.modules.smartfuel.device.entity.SmartfuelDevice;
import org.jiyitech.modules.smartfuel.device.entity.SmartfuelDeviceTwin;
import org.jiyitech.modules.smartfuel.device.entity.SmartfuelDeviceAttribute;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 设备
 * @Author: jeecg-boot
 * @Date:   2022-03-24
 * @Version: V1.0
 */
@Data
@ApiModel(value="smartfuel_devicePage对象", description="设备")
public class SmartfuelDevicePage {

	/**主键*/
	@ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
	@ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
	@ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**设备名称*/
	@Excel(name = "设备名称", width = 15)
	@ApiModelProperty(value = "设备名称")
    private String name;
	/**设备类型*/
	@Excel(name = "设备类型", width = 15)
	@ApiModelProperty(value = "设备类型")
    private String deviceType;
	/**设备状态*/
	@Excel(name = "设备状态", width = 15, dicCode = "device_status")
    @Dict(dicCode = "device_status")
	@ApiModelProperty(value = "设备状态")
    private Integer deviceStatus;

	@ExcelCollection(name="孪生属性")
	@ApiModelProperty(value = "孪生属性")
	private List<SmartfuelDeviceTwin> smartfuelDeviceTwinList;

	@ExcelCollection(name="设备属性")
	@ApiModelProperty(value = "设备属性")
	private List<SmartfuelDeviceAttribute> smartfuelDeviceAttributeList;

}
