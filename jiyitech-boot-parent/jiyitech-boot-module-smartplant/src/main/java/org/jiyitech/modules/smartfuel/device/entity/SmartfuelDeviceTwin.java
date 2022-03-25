package org.jiyitech.modules.smartfuel.device.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;

/**
 * @Description: 孪生属性
 * @Author: jeecg-boot
 * @Date:   2022-03-24
 * @Version: V1.0
 */
@ApiModel(value="smartfuel_device_twin对象", description="孪生属性")
@Data
@TableName("smartfuel_device_twin")
public class SmartfuelDeviceTwin implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
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
	/**孪生属性名称*/
	@Excel(name = "孪生属性名称", width = 15)
    @ApiModelProperty(value = "孪生属性名称")
    private String name;
	/**设备主表id*/
    @ApiModelProperty(value = "设备主表id")
    private String deviceId;
	/**值*/
	@Excel(name = "值", width = 15)
    @ApiModelProperty(value = "值")
    private Double value;
	/**标记*/
	@Excel(name = "标记", width = 15)
    @ApiModelProperty(value = "标记")
    private String tag;
	/**数据源*/
	@Excel(name = "数据源", width = 15)
    @ApiModelProperty(value = "数据源")
    private Integer source;
	/**数值更新时间*/
	@Excel(name = "数值更新时间", width = 15)
    @ApiModelProperty(value = "数值更新时间")
    private Date valueUpdateTime;
}
