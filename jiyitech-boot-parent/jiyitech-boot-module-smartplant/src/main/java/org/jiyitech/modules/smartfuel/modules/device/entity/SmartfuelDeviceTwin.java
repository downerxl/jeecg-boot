package org.jiyitech.modules.smartfuel.modules.device.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: 孪生属性
 * @Author: jeecg-boot
 * @Date: 2022-03-27
 * @Version: V1.0
 */
@Data
@TableName("smartfuel_device_twin")
@ApiModel(value = "smartfuel_device_twin对象", description = "孪生属性")
public class SmartfuelDeviceTwin implements Serializable {
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
     * 孪生属性名称
     */
    @Excel(name = "孪生属性名称", width = 15)
    @ApiModelProperty(value = "孪生属性名称")
    private java.lang.String name;
    /**
     * 设备主表id
     */
    @Excel(name = "设备名称", width = 15, dictTable = "smartfuel_device", dicText = "name", dicCode = "id")
    @ApiModelProperty(value = "设备主表id")
    private java.lang.String deviceId;
    /**
     * 值
     */
    @Excel(name = "值", width = 15)
    @ApiModelProperty(value = "值")
    private java.lang.Double value;
    /**
     * 标记
     */
    @Excel(name = "标记", width = 15)
    @ApiModelProperty(value = "标记")
    private java.lang.String tag;
    /**
     * 数据源
     */
    @Excel(name = "数据源", width = 15)
    @ApiModelProperty(value = "数据源")
    private java.lang.Integer source;
    /**
     * 数值更新时间
     */
    @Excel(name = "数值更新时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "数值更新时间")
    private java.util.Date valueUpdateTime;
}
