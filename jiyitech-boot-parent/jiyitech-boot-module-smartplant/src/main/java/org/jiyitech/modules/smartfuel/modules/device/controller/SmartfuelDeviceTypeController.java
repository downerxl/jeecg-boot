package org.jiyitech.modules.smartfuel.modules.device.controller;

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
import org.jiyitech.modules.smartfuel.modules.device.entity.SmartfuelDeviceType;
import org.jiyitech.modules.smartfuel.modules.device.service.ISmartfuelDeviceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @Description: 设备类型
 * @Author: jeecg-boot
 * @Date: 2022-03-27
 * @Version: V1.0
 */
@Api(tags = "设备类型")
@RestController
@RequestMapping("/modules/device/smartfuelDeviceType")
@Slf4j
public class SmartfuelDeviceTypeController extends JeecgController<SmartfuelDeviceType, ISmartfuelDeviceTypeService> {
    @Autowired
    private ISmartfuelDeviceTypeService smartfuelDeviceTypeService;

    /**
     * 分页列表查询
     *
     * @param smartfuelDeviceType
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "设备类型-分页列表查询")
    @ApiOperation(value = "设备类型-分页列表查询", notes = "设备类型-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<SmartfuelDeviceType>> queryPageList(SmartfuelDeviceType smartfuelDeviceType,
                                                            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                            HttpServletRequest req) {
        QueryWrapper<SmartfuelDeviceType> queryWrapper = QueryGenerator.initQueryWrapper(smartfuelDeviceType, req.getParameterMap());
        Page<SmartfuelDeviceType> page = new Page<SmartfuelDeviceType>(pageNo, pageSize);
        IPage<SmartfuelDeviceType> pageList = smartfuelDeviceTypeService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param smartfuelDeviceType
     * @return
     */
    @AutoLog(value = "设备类型-添加")
    @ApiOperation(value = "设备类型-添加", notes = "设备类型-添加")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody SmartfuelDeviceType smartfuelDeviceType) {
        smartfuelDeviceTypeService.save(smartfuelDeviceType);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param smartfuelDeviceType
     * @return
     */
    @AutoLog(value = "设备类型-编辑")
    @ApiOperation(value = "设备类型-编辑", notes = "设备类型-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody SmartfuelDeviceType smartfuelDeviceType) {
        smartfuelDeviceTypeService.updateById(smartfuelDeviceType);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "设备类型-通过id删除")
    @ApiOperation(value = "设备类型-通过id删除", notes = "设备类型-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        smartfuelDeviceTypeService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "设备类型-批量删除")
    @ApiOperation(value = "设备类型-批量删除", notes = "设备类型-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.smartfuelDeviceTypeService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "设备类型-通过id查询")
    @ApiOperation(value = "设备类型-通过id查询", notes = "设备类型-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<SmartfuelDeviceType> queryById(@RequestParam(name = "id", required = true) String id) {
        SmartfuelDeviceType smartfuelDeviceType = smartfuelDeviceTypeService.getById(id);
        if (smartfuelDeviceType == null) {
            return Result.error("未找到对应数据", null);
        }
        return Result.OK(smartfuelDeviceType);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param smartfuelDeviceType
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SmartfuelDeviceType smartfuelDeviceType) {
        return super.exportXls(request, smartfuelDeviceType, SmartfuelDeviceType.class, "设备类型");
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
        return super.importExcel(request, response, SmartfuelDeviceType.class);
    }

}
