package org.jiyitech.modules.smartfuel.modules.device.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: 设备
 * @Author: jeecg-boot
 * @Date: 2022-03-27
 * @Version: V1.0
 */
@Data
@TableName("smartfuel_device")
@ApiModel(value = "smartfuel_device对象", description = "设备")
public class SmartfuelDevice implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
    /**
     * 设备名称
     */
    @Excel(name = "设备名称", width = 15)
    @ApiModelProperty(value = "设备名称")
    private java.lang.String name;
    /**
     * 设备类型
     */
    @Excel(name = "设备类型", width = 15, dictTable = "smartfuel_device_type", dicText = "name", dicCode = "code")
    @Dict(dictTable = "smartfuel_device_type", dicText = "name", dicCode = "code")
    @ApiModelProperty(value = "设备类型")
    private java.lang.String deviceType;
    /**
     * 设备状态
     */
    @Excel(name = "设备状态", width = 15, dicCode = "device_status")
    @Dict(dicCode = "device_status")
    @ApiModelProperty(value = "设备状态")
    private java.lang.Integer deviceStatus;
}
