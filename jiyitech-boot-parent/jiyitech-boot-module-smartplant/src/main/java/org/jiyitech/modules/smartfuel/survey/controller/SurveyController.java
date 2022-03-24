package org.jiyitech.modules.smartfuel.survey.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jiyitech.modules.smartfuel.survey.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: jiyitech-boot01
 * @description: SurveyController
 * @author: xiongwenjun
 * @create: 2022-03-24 13:21
 **/
@Api(tags = "survey")
@RestController
@RequestMapping("/survey")
@Slf4j
public class SurveyController {

    @Autowired(required = false)
    private SurveyService surveyService;
    /**
     * 获取当前每煤层信息 http://SuveryServer/coalresults/interfaces/FieldsResults.json
     *
     * @return
     */
    @AutoLog(value = "获取当前每煤层信息")
    @ApiOperation(value = "获取当前每煤层信息", notes = "获取当前每煤层信息")
    @GetMapping(value = "/GetCoalresults")
    public Result<?> GetCoalresults() {
        var result = surveyService.getCoalresults();
        if (result == null) {
            return Result.error("查询结果为null或者查询失败");
        }
        return Result.OK(result);
    }

    /**
     * 获取指定煤场所有分层信息 http://SuveryServer:2467/BucketWheel/GetStockyardMaterials
     *
     * @return
     */
    @AutoLog(value = "获取指定煤场所有分层信息")
    @ApiOperation(value = "获取指定煤场所有分层信息", notes = "获取指定煤场所有分层信息")
    @GetMapping(value = "/GetStockyardMaterials")
    public Result<?> GetStockyardMaterials(
            int StockyardID, double LevelHeight, double X1, double X2) {

        var result = surveyService.GetStockyardMaterials(StockyardID, LevelHeight, X1, X2);
        if (result == null) {
            return Result.error("查询结果为null或者查询失败");
        }
        return Result.OK(result);
    }

    /**
     * 获取斗轮机当前作业模型对应的CoalId信息 http://SuveryServer:2467/BucketWheel/GetCurMat
     *
     * @return
     */
    @AutoLog(value = "获取斗轮机当前作业模型对应的CoalId信息")
    @ApiOperation(value = "获取斗轮机当前作业模型对应的CoalId信息", notes = "获取斗轮机当前作业模型对应的CoalId信息")
    @GetMapping(value = "/GetCurMat")
    public Result<?> GetCurMat(int BucketWheelID) {

        var result = surveyService.GetCurMat(BucketWheelID);
        if (result == null) {
            return Result.error("查询结果为null或者查询失败");
        }
        return Result.OK(result);
    }

    /**
     * 获取煤场纵截面指定间隔的最大高度 http://SuveryServer:2467/BucketWheel/GetFieldHeights
     *
     * @return
     */
    @AutoLog(value = "获取煤场纵截面指定间隔的最大高度")
    @ApiOperation(value = "获取煤场纵截面指定间隔的最大高度", notes = "获取煤场纵截面指定间隔的最大高度")
    @GetMapping(value = "/GetFieldHeights")
    public Result<?> GetFieldHeights(int StockyardID, double OffsetX, String Dists) {
        var result = surveyService.GetFieldHeights(StockyardID, OffsetX, Dists);
        if (result == null) {
            return Result.error("查询结果为null或者查询失败");
        }
        return Result.OK(result);
    }

    /**
     * 获取三维程序版本号 http://SuveryServer:2467/BucietWheel/Version
     *
     * @return
     */
    @AutoLog(value = "获取三维程序版本号")
    @ApiOperation(value = "获取三维程序版本号", notes = "获取三维程序版本号")
    @GetMapping(value = "/GetVersion")
    public Result<?> GetVersion() {
        var result = surveyService.GetVersion();
        if (result == null) {
            return Result.error("查询结果为null或者查询失败");
        }
        return Result.OK(result);
    }
}

