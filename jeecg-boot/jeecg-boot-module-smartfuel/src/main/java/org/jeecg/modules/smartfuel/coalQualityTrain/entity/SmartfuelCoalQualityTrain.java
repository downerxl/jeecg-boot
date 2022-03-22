package org.jeecg.modules.smartfuel.coalQualityTrain.entity;

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
 * @Description: 煤质数据
 * @Author: jeecg-boot
 * @Date:   2022-03-21
 * @Version: V1.0
 */
@Data
@TableName("smartfuel_coal_quality_train")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="smartfuel_coal_quality_train对象", description="煤质数据")
public class SmartfuelCoalQualityTrain implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**检验类型*/
	@Excel(name = "检验类型", width = 15, dicCode = "check_type")
	@Dict(dicCode = "check_type")
    @ApiModelProperty(value = "检验类型")
    private java.lang.String checkType;
	/**煤运信息ID*/
	@Excel(name = "煤运信息ID", width = 15)
    @ApiModelProperty(value = "煤运信息ID")
    private java.lang.String trainInfoId;
	/**干燥无灰基挥发分*/
	@Excel(name = "干燥无灰基挥发分", width = 15)
    @ApiModelProperty(value = "干燥无灰基挥发分")
    private java.math.BigDecimal vdaf;
	/**干燥基全硫*/
	@Excel(name = "干燥基全硫", width = 15)
    @ApiModelProperty(value = "干燥基全硫")
    private java.math.BigDecimal std;
	/**收到基低位发热量*/
	@Excel(name = "收到基低位发热量", width = 15)
    @ApiModelProperty(value = "收到基低位发热量")
    private java.lang.Double qnetar;
	/**全水分*/
	@Excel(name = "全水分", width = 15)
    @ApiModelProperty(value = "全水分")
    private java.math.BigDecimal mt;
	/**内水分*/
	@Excel(name = "内水分", width = 15)
    @ApiModelProperty(value = "内水分")
    private java.math.BigDecimal mad;
	/**空干基挥发分*/
	@Excel(name = "空干基挥发分", width = 15)
    @ApiModelProperty(value = "空干基挥发分")
    private java.math.BigDecimal vad;
	/**空干基固定碳*/
	@Excel(name = "空干基固定碳", width = 15)
    @ApiModelProperty(value = "空干基固定碳")
    private java.lang.Double fcad;
	/**空干基硫分*/
	@Excel(name = "空干基硫分", width = 15)
    @ApiModelProperty(value = "空干基硫分")
    private java.math.BigDecimal stad;
	/**收到基灰分*/
	@Excel(name = "收到基灰分", width = 15)
    @ApiModelProperty(value = "收到基灰分")
    private java.math.BigDecimal aar;
	/**弹筒热值*/
	@Excel(name = "弹筒热值", width = 15)
    @ApiModelProperty(value = "弹筒热值")
    private java.lang.Double qbad;
	/**灰熔点（软化温度）*/
	@Excel(name = "灰熔点（软化温度）", width = 15)
    @ApiModelProperty(value = "灰熔点（软化温度）")
    private java.math.BigDecimal st;
	/**流动温度*/
	@Excel(name = "流动温度", width = 15)
    @ApiModelProperty(value = "流动温度")
    private java.math.BigDecimal ft;
	/**半球温度*/
	@Excel(name = "半球温度", width = 15)
    @ApiModelProperty(value = "半球温度")
    private java.math.BigDecimal ht;
	/**变形温度*/
	@Excel(name = "变形温度", width = 15)
    @ApiModelProperty(value = "变形温度")
    private java.math.BigDecimal dt;
	/**空干基灰分*/
	@Excel(name = "空干基灰分", width = 15)
    @ApiModelProperty(value = "空干基灰分")
    private java.math.BigDecimal aad;
	/**空干基氢含量*/
	@Excel(name = "空干基氢含量", width = 15)
    @ApiModelProperty(value = "空干基氢含量")
    private java.math.BigDecimal had;
	/**收到基硫含量*/
	@Excel(name = "收到基硫含量", width = 15)
    @ApiModelProperty(value = "收到基硫含量")
    private java.math.BigDecimal star;
	/**干燥基灰分*/
	@Excel(name = "干燥基灰分", width = 15)
    @ApiModelProperty(value = "干燥基灰分")
    private java.math.BigDecimal ad;
	/**收到基挥发分*/
	@Excel(name = "收到基挥发分", width = 15)
    @ApiModelProperty(value = "收到基挥发分")
    private java.math.BigDecimal var;
	/**干燥基挥发分*/
	@Excel(name = "干燥基挥发分", width = 15)
    @ApiModelProperty(value = "干燥基挥发分")
    private java.math.BigDecimal vd;
	/**低位发热量MJ*/
	@Excel(name = "低位发热量MJ", width = 15)
    @ApiModelProperty(value = "低位发热量MJ")
    private java.lang.Double qnetarMj;
	/**采样编码*/
	@Excel(name = "采样编码", width = 15)
    @ApiModelProperty(value = "采样编码")
    private java.lang.String cybm;
	/**ggrd_mj*/
	@Excel(name = "ggrd_mj", width = 15)
    @ApiModelProperty(value = "ggrd_mj")
    private java.lang.Double ggrdMj;
	/**ggrad_mj*/
	@Excel(name = "ggrad_mj", width = 15)
    @ApiModelProperty(value = "ggrad_mj")
    private java.lang.Double ggradMj;
	/**ggrar_kcal*/
	@Excel(name = "ggrar_kcal", width = 15)
    @ApiModelProperty(value = "ggrar_kcal")
    private java.lang.Double ggrarKcal;
	/**煤质颜色*/
	@Excel(name = "煤质颜色", width = 15)
    @ApiModelProperty(value = "煤质颜色")
    private java.lang.String color;
}
