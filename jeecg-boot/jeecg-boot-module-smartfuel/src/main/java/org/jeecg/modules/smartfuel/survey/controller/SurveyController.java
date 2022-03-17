package org.jeecg.modules.smartfuel.survey.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.smartfuel.survey.service.ISurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "survey")
@RestController
@RequestMapping("/survey")
@Slf4j
public class SurveyController {

  @Value("${SurveyAddress}")
  private String ip;

  @Autowired(required = false)
  private ISurveyService iSurveyService;

  /**
   * @return
   */
  @AutoLog(value = "获取当前每煤层信息")
  @ApiOperation(value = "获取当前每煤层信息", notes = "获取当前每煤层信息")
  @GetMapping(value = "/GetCoalresults")
  public Result<?> GetCoalresults() {
    var result = iSurveyService.getCoalresults();
    if (result == null) {
      return Result.error("查询结果为null或者查询失败");
    }
    return Result.OK(result);
  }

  /**
   * @param StockyardID 煤场编号
   * @param LevelHeight 分层高度
   * @param X1 起点距离煤堆内侧距离
   * @param X2 终点距离煤堆内侧距离
   * @return
   */
  @AutoLog(value = "获取指定煤场所有分层信息")
  @ApiOperation(value = "获取指定煤场所有分层信息", notes = "获取指定煤场所有分层信息")
  @GetMapping(value = "/GetStockyardMaterials")
  public Result<?> GetStockyardMaterials(
      int StockyardID, double LevelHeight, double X1, double X2) {

    var result = iSurveyService.GetStockyardMaterials(StockyardID, LevelHeight, X1, X2);
    if (result == null) {
      return Result.error("查询结果为null或者查询失败");
    }
    return Result.OK(result);
  }

  /**
   * @param BucketWheelID 斗轮机编号
   * @return
   */
  @AutoLog(value = "获取斗轮机当前作业模型对应的CoalId信息")
  @ApiOperation(value = "获取斗轮机当前作业模型对应的CoalId信息", notes = "获取斗轮机当前作业模型对应的CoalId信息")
  @GetMapping(value = "/GetCurMat")
  public Result<?> GetCurMat(int BucketWheelID) {

    var result = iSurveyService.GetCurMat(BucketWheelID);
    if (result == null) {
      return Result.error("查询结果为null或者查询失败");
    }
    return Result.OK(result);
  }

  /**
   * @param StockyardID 煤场（煤堆）编号
   * @param OffsetX 截面距离轨道距离
   * @param Dists 点间距
   * @return
   */
  @AutoLog(value = "获取煤场纵截面指定间隔的最大高度")
  @ApiOperation(value = "获取煤场纵截面指定间隔的最大高度", notes = "获取煤场纵截面指定间隔的最大高度")
  @GetMapping(value = "/GetFieldHeights")
  public Result<?> GetFieldHeights(int StockyardID, double OffsetX, String Dists) {
    var result = iSurveyService.GetFieldHeights(StockyardID, OffsetX, Dists);
    if (result == null) {
      return Result.error("查询结果为null或者查询失败");
    }
    return Result.OK(result);
  }

  /**
   * @return
   */
  @AutoLog(value = "获取三维程序版本号")
  @ApiOperation(value = "获取三维程序版本号", notes = "获取三维程序版本号")
  @GetMapping(value = "/GetVersion")
  public Result<?> GetVersion() {
    var result = iSurveyService.GetVersion();
    if (result == null) {
      return Result.error("查询结果为null或者查询失败");
    }
    return Result.OK(result);
  }
}
