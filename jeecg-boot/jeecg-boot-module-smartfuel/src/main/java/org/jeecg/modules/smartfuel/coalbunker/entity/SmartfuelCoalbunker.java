package org.jeecg.modules.smartfuel.coalbunker.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 煤仓分层细节
 * @Author: jeecg-boot
 * @Date:   2022-02-24
 * @Version: V1.0
 */
@Data
@TableName("smartfuel_coalbunker")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="smartfuel_coalbunker对象", description="煤仓分层细节")
public class SmartfuelCoalbunker implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**所属煤仓的编号*/
	@Excel(name = "所属煤仓的编号", width = 15)
    @ApiModelProperty(value = "所属煤仓的编号")
    private java.lang.String bunkerId;
	/**煤种名称*/
	@Excel(name = "煤种名称", width = 15)
    @ApiModelProperty(value = "煤种名称")
    private java.lang.String coalName;
	/**煤种的Guid*/
	@Excel(name = "煤种的Guid", width = 15)
    @ApiModelProperty(value = "煤种的Guid")
    private java.lang.String coalId;
	/**煤种密度*/
	@Excel(name = "煤种密度", width = 15)
    @ApiModelProperty(value = "煤种密度")
    private java.lang.Double density;
	/**加仓时间*/
	@Excel(name = "加仓时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "加仓时间")
    private java.util.Date bunkerTime;
	/**煤仓高度*/
	@Excel(name = "煤仓高度", width = 15)
    @ApiModelProperty(value = "煤仓高度")
    private java.lang.Double bunkerHeight;
	/**煤种体积*/
	@Excel(name = "煤种体积", width = 15)
    @ApiModelProperty(value = "煤种体积")
    private java.lang.Double bunkerVolumn;
	/**煤层的编号*/
	@Excel(name = "煤层的编号", width = 15)
    @ApiModelProperty(value = "煤层的编号")
    private java.lang.Integer bunkerIndex;
}
