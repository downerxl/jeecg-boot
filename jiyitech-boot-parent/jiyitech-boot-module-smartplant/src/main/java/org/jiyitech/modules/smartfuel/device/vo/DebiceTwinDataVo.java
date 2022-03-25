package org.jiyitech.modules.smartfuel.device.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 设备管理接收debiceTwin参数
 */
@Data
public class DebiceTwinDataVo {

    /**
     * 设备ID
     */
    @ApiModelProperty(value = "设备ID")
    private String deviceId;

    /**
     * 孪生属性标签名
     */
    @ApiModelProperty(value = "孪生属性标签名")
    private String tag;

    /**
     * 孪生属性数据源
     */
    @ApiModelProperty(value = "孪生属性数据源")
    private String source;

    /**
     * 时间，分钟数(整型)
     */
    @ApiModelProperty(value = "时间，分钟数(整型)")
    private Integer time;

    /**
     * 起始时间
     */
    @ApiModelProperty(value = "起始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endTime;

    /**
     * 阈值
     */
    @ApiModelProperty(value = "阈值")
    private BigDecimal threshold;
}
