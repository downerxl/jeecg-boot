package org.jeecg.modules.smartfuel.coalQualityTrain.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.smartfuel.coalQualityTrain.entity.SmartfuelCoalQualityTrain;
import org.jeecg.modules.smartfuel.coalQualityTrain.service.ISmartfuelCoalQualityTrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 煤质数据 @Author: jeecg-boot @Date: 2022-03-21 @Version: V1.0
 */
@Api(tags = "煤质数据")
@RestController
@RequestMapping("/coalQualityTrain/smartfuelCoalQualityTrain")
@Slf4j
public class SmartfuelCoalQualityTrainController
    extends JeecgController<SmartfuelCoalQualityTrain, ISmartfuelCoalQualityTrainService> {
  @Autowired private ISmartfuelCoalQualityTrainService smartfuelCoalQualityTrainService;

  /**
   * 分页列表查询
   *
   * @param smartfuelCoalQualityTrain
   * @param pageNo
   * @param pageSize
   * @param req
   * @return
   */
  // @AutoLog(value = "煤质数据-分页列表查询")
  @ApiOperation(value = "煤质数据-分页列表查询", notes = "煤质数据-分页列表查询")
  @GetMapping(value = "/list")
  public Result<IPage<SmartfuelCoalQualityTrain>> queryPageList(
      SmartfuelCoalQualityTrain smartfuelCoalQualityTrain,
      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
      HttpServletRequest req) {
    QueryWrapper<SmartfuelCoalQualityTrain> queryWrapper =
        QueryGenerator.initQueryWrapper(smartfuelCoalQualityTrain, req.getParameterMap());
    Page<SmartfuelCoalQualityTrain> page = new Page<SmartfuelCoalQualityTrain>(pageNo, pageSize);
    IPage<SmartfuelCoalQualityTrain> pageList =
        smartfuelCoalQualityTrainService.page(page, queryWrapper);
    return Result.OK(pageList);
  }

  /**
   * 煤化验基准换算
   *
   * @param smartfuelCoalQualityTrain
   * @return
   */
  private Map<String, BigDecimal> calculateBase(
      SmartfuelCoalQualityTrain smartfuelCoalQualityTrain) {
    // assay_benchmark 煤化验基准 收到基(ar)收到基挥发分  空气干燥基(ad)空干基挥发分 干燥基(d)干燥基挥发分 干燥无灰基(daf)干燥无灰基挥发分
    String assay_benchmark = null;
    Map<String, BigDecimal> map = new HashMap<>();
    BigDecimal var, vad, vd, vdaf, mad, mt, aad, aar, ad;
    var = smartfuelCoalQualityTrain.getVar();
    vad = smartfuelCoalQualityTrain.getVad();
    vd = smartfuelCoalQualityTrain.getVd();
    vdaf = smartfuelCoalQualityTrain.getVdaf();
    // 下面是必填，有数据 收到基灰分aar  空干基灰分aad  干燥基灰分ad
    aad = smartfuelCoalQualityTrain.getAad();
    aar = smartfuelCoalQualityTrain.getAar();
    ad = smartfuelCoalQualityTrain.getAd();
    BigDecimal hundred = BigDecimal.valueOf(100);
    mad = smartfuelCoalQualityTrain.getMad();
    mt = smartfuelCoalQualityTrain.getMt();

    // 判断煤化验基准哪个有值，正常只有一个有值.(假如都有值，目前暂时先收到基ar为基准)
    if (var != null) {
      assay_benchmark = "ar";
    } else if (vad != null) {
      assay_benchmark = "ad";
    } else if (vd != null) {
      assay_benchmark = "d";
    } else if (vdaf != null) {
      assay_benchmark = "daf";
    } else {
      assay_benchmark = null;
    }
    // 判断煤化验基准是否都没有数据，如果没有，就直接返回空map
    if (assay_benchmark == null) {
      return map;
    } else {
      switch (assay_benchmark) {
        case "ad":
          map.put("vad", vad);
          if (mad.equals("")) {
            // 内水为空，什么都算不出来
          } else {
            if (mt.equals("")) {
              // 内水不为空，全水为空。可以算干燥基d
              vd =
                  (hundred.multiply(vad))
                      .divide(hundred.subtract(mad), 2, BigDecimal.ROUND_HALF_UP);
              map.put("vd", vd);
            } else {
              // 内水,全水都不为空,可以算全部，但是干燥无灰基还需要aad
              var =
                  ((hundred.subtract(mt)).multiply(vad))
                      .divide(hundred.subtract(mad), 2, BigDecimal.ROUND_HALF_UP);
              vdaf =
                  (hundred.multiply(vad))
                      .divide(hundred.subtract(mad.add(aad)), 2, BigDecimal.ROUND_HALF_UP);
              vd =
                  (hundred.multiply(vad))
                      .divide(hundred.subtract(mad), 2, BigDecimal.ROUND_HALF_UP);
              map.put("var", var);
              map.put("vdaf", vdaf);
              map.put("vd", vd);
            }
          }

          break;
        case "ar":
          map.put("var", var);
          if (mt.equals("")) {
            // 全水为空，什么都算不出来
          } else {
            if (mad.equals("")) {
              // 全水不为空，内水为空。可以算干燥基d
              vd =
                  (hundred.multiply(var)).divide(hundred.subtract(mt), 2, BigDecimal.ROUND_HALF_UP);
              map.put("vd", vd);
              // 也可以算干燥无灰基daf，但需要aar
              vdaf =
                  (hundred.multiply(var))
                      .divide(hundred.subtract(mt.add(aar)), 2, BigDecimal.ROUND_HALF_UP);
              map.put("vdaf", vdaf);

            } else {
              // 内水,全水都不为空,可以算空气干燥基(ad)
              vad =
                  ((hundred.subtract(mad)).multiply(var))
                      .divide(hundred.subtract(mt), 2, BigDecimal.ROUND_HALF_UP);
              vd =
                  (hundred.multiply(var)).divide(hundred.subtract(mt), 2, BigDecimal.ROUND_HALF_UP);
              vdaf =
                  (hundred.multiply(var))
                      .divide(hundred.subtract(mt.add(aar)), 2, BigDecimal.ROUND_HALF_UP);
              map.put("vad", vad);
              map.put("vdaf", vdaf);
              map.put("vd", vd);
            }
          }
          break;

        case "d":
          map.put("vd", vd);
          if (!mt.equals("") && !mad.equals("")) {
            // 都不为空，什么都算出来
            vad =
                ((hundred.subtract(mad)).multiply(vd)).divide(hundred, 2, BigDecimal.ROUND_HALF_UP);
            vdaf = (hundred.multiply(vd)).divide(hundred.subtract(ad), 2, BigDecimal.ROUND_HALF_UP);
            var =
                ((hundred.subtract(mt)).multiply(vd)).divide(hundred, 2, BigDecimal.ROUND_HALF_UP);
            map.put("vad", vad);
            map.put("vdaf", vdaf);
            map.put("var", var);

          } else if (mt.equals("") && !mad.equals("")) {
            // 全水为空，内水不为空,可以算空气干燥基(ad),也可以算干燥无灰基daf,但需要Ad
            vad =
                ((hundred.subtract(mad)).multiply(vd)).divide(hundred, 2, BigDecimal.ROUND_HALF_UP);
            vdaf = (hundred.multiply(vd)).divide(hundred.subtract(ad), 2, BigDecimal.ROUND_HALF_UP);
            map.put("vad", vad);
            map.put("vdaf", vdaf);

          } else if (!mt.equals("") && mad.equals("")) {
            // 全水不为空，内水为空。可以算收到基ar
            var =
                ((hundred.subtract(mt)).multiply(vd)).divide(hundred, 2, BigDecimal.ROUND_HALF_UP);
            map.put("var", var);
          } else {
            // 都为空，什么都算不出来
          }
          break;

        case "daf":
          map.put("vdaf", vdaf);
          if (!mt.equals("") && !mad.equals("")) {
            // 都不为空，都可以出来
            // Aad,Aar,Ad??需要判断为空么？？
            vad =
                ((hundred.subtract(mad.add(aad))).multiply(vdaf))
                    .divide(hundred, 2, BigDecimal.ROUND_HALF_UP);
            var =
                ((hundred.subtract(mt.add(aar))).multiply(vdaf))
                    .divide(hundred, 2, BigDecimal.ROUND_HALF_UP);
            vd =
                ((hundred.subtract(ad)).multiply(vdaf))
                    .divide(hundred, 2, BigDecimal.ROUND_HALF_UP);
            map.put("vad", vad);
            map.put("vd", vd);
            map.put("var", var);
          } else if (mt.equals("") && !mad.equals("")) {
            // 全水为空，内水不为空,可以算空气干燥基(ad),但需要aar,也可以算干燥基(d),但需要ad
            vad =
                ((hundred.subtract(mad.add(aad))).multiply(vdaf))
                    .divide(hundred, 2, BigDecimal.ROUND_HALF_UP);
            vd =
                ((hundred.subtract(ad)).multiply(vdaf))
                    .divide(hundred, 2, BigDecimal.ROUND_HALF_UP);
            map.put("vad", vad);
            map.put("vd", vd);
          } else if (!mt.equals("") && mad.equals("")) {
            // 全水不为空，内水为空。可以算收到基ar
            var =
                ((hundred.subtract(mt.add(aar))).multiply(vdaf))
                    .divide(hundred, 2, BigDecimal.ROUND_HALF_UP);
            vd =
                ((hundred.subtract(ad)).multiply(vdaf))
                    .divide(hundred, 2, BigDecimal.ROUND_HALF_UP);
            map.put("vd", vd);
            map.put("var", var);
          } else {
            // 都为空，什么都算不出来
          }
          break;
        default:
          System.out.println("未匹配到基,出错啦");
          log.error("未匹配到基,出错啦");
          break;
      }
      return map;
    }
  }
  /**
   * 添加
   *
   * @param smartfuelCoalQualityTrain
   * @return
   */
  @AutoLog(value = "煤质数据-添加")
  @ApiOperation(value = "煤质数据-添加", notes = "煤质数据-添加")
  @PostMapping(value = "/add")
  public Result<?> add(@RequestBody SmartfuelCoalQualityTrain smartfuelCoalQualityTrain) {

    Map<String, BigDecimal> map = calculateBase(smartfuelCoalQualityTrain); // 煤化验基准换算结果
    if (map.isEmpty()) {
      return Result.error("未匹配到煤基准或者基准输入为空");
    } else {
      if (map.containsKey("vd")) {
        smartfuelCoalQualityTrain.setVd(map.get("vd"));
      }
      if (map.containsKey("var")) {
        smartfuelCoalQualityTrain.setVar(map.get("var"));
      }
      if (map.containsKey("vad")) {
        smartfuelCoalQualityTrain.setVad(map.get("vad"));
      }
      if (map.containsKey("vdaf")) {
        smartfuelCoalQualityTrain.setVdaf(map.get("vdaf"));
      }
      smartfuelCoalQualityTrainService.save(smartfuelCoalQualityTrain);
      return Result.OK("添加成功！");
    }
  }

  /**
   * 编辑
   *
   * @param smartfuelCoalQualityTrain
   * @return
   */
  @AutoLog(value = "煤质数据-编辑")
  @ApiOperation(value = "煤质数据-编辑", notes = "煤质数据-编辑")
  @RequestMapping(
      value = "/edit",
      method = {RequestMethod.PUT, RequestMethod.POST})
  public Result<String> edit(@RequestBody SmartfuelCoalQualityTrain smartfuelCoalQualityTrain) {
    Map<String, BigDecimal> map = calculateBase(smartfuelCoalQualityTrain);
    if (map.isEmpty()) {
      return Result.error("未匹配到煤基准或者基准输入为空");
    } else {
      if (map.containsKey("vd")) {
        smartfuelCoalQualityTrain.setVd(map.get("vd"));
      }
      if (map.containsKey("var")) {
        smartfuelCoalQualityTrain.setVar(map.get("var"));
      }
      if (map.containsKey("vad")) {
        smartfuelCoalQualityTrain.setVad(map.get("vad"));
      }
      if (map.containsKey("vdaf")) {
        smartfuelCoalQualityTrain.setVdaf(map.get("vdaf"));
      }
      smartfuelCoalQualityTrainService.updateById(smartfuelCoalQualityTrain);
      return Result.OK("编辑成功!");
    }
  }

  /**
   * 通过id删除
   *
   * @param id
   * @return
   */
  @AutoLog(value = "煤质数据-通过id删除")
  @ApiOperation(value = "煤质数据-通过id删除", notes = "煤质数据-通过id删除")
  @DeleteMapping(value = "/delete")
  public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
    smartfuelCoalQualityTrainService.removeById(id);
    return Result.OK("删除成功!");
  }

  /**
   * 批量删除
   *
   * @param ids
   * @return
   */
  @AutoLog(value = "煤质数据-批量删除")
  @ApiOperation(value = "煤质数据-批量删除", notes = "煤质数据-批量删除")
  @DeleteMapping(value = "/deleteBatch")
  public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
    this.smartfuelCoalQualityTrainService.removeByIds(Arrays.asList(ids.split(",")));
    return Result.OK("批量删除成功!");
  }

  /**
   * 通过id查询
   *
   * @param id
   * @return
   */
  // @AutoLog(value = "煤质数据-通过id查询")
  @ApiOperation(value = "煤质数据-通过id查询", notes = "煤质数据-通过id查询")
  @GetMapping(value = "/queryById")
  public Result<SmartfuelCoalQualityTrain> queryById(
      @RequestParam(name = "id", required = true) String id) {
    SmartfuelCoalQualityTrain smartfuelCoalQualityTrain =
        smartfuelCoalQualityTrainService.getById(id);
    if (smartfuelCoalQualityTrain == null) {
      return Result.error("未找到对应数据", null);
    }
    return Result.OK(smartfuelCoalQualityTrain);
  }

  /**
   * 导出excel
   *
   * @param request
   * @param smartfuelCoalQualityTrain
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(
      HttpServletRequest request, SmartfuelCoalQualityTrain smartfuelCoalQualityTrain) {
    return super.exportXls(
        request, smartfuelCoalQualityTrain, SmartfuelCoalQualityTrain.class, "煤质数据");
  }

  /**
   * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
    return super.importExcel(request, response, SmartfuelCoalQualityTrain.class);
  }
}
