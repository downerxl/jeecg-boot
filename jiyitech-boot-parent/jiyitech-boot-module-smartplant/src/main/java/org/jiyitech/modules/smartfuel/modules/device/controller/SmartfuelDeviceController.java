package org.jiyitech.modules.smartfuel.modules.device.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jiyitech.modules.smartfuel.modules.device.entity.SmartfuelDevice;
import org.jiyitech.modules.smartfuel.modules.device.entity.SmartfuelDeviceAttribute;
import org.jiyitech.modules.smartfuel.modules.device.entity.SmartfuelDeviceTwin;
import org.jiyitech.modules.smartfuel.modules.device.service.ISmartfuelDeviceAttributeService;
import org.jiyitech.modules.smartfuel.modules.device.service.ISmartfuelDeviceService;
import org.jiyitech.modules.smartfuel.modules.device.service.ISmartfuelDeviceTwinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: 设备
 * @Author: jeecg-boot
 * @Date: 2022-03-27
 * @Version: V1.0
 */
@Api(tags = "设备")
@RestController
@RequestMapping("/device/smartfuelDevice")
@Slf4j
public class SmartfuelDeviceController extends JeecgController<SmartfuelDevice, ISmartfuelDeviceService> {

    @Autowired
    private ISmartfuelDeviceService smartfuelDeviceService;

    @Autowired
    private ISmartfuelDeviceTwinService smartfuelDeviceTwinService;

    @Autowired
    private ISmartfuelDeviceAttributeService smartfuelDeviceAttributeService;


    /*---------------------------------主表处理-begin-------------------------------------*/

    /**
     * 分页列表查询
     *
     * @param smartfuelDevice
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "设备-分页列表查询")
    @ApiOperation(value = "设备-分页列表查询", notes = "设备-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(SmartfuelDevice smartfuelDevice,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<SmartfuelDevice> queryWrapper = QueryGenerator.initQueryWrapper(smartfuelDevice, req.getParameterMap());
        Page<SmartfuelDevice> page = new Page<SmartfuelDevice>(pageNo, pageSize);
        IPage<SmartfuelDevice> pageList = smartfuelDeviceService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param smartfuelDevice
     * @return
     */
    @AutoLog(value = "设备-添加")
    @ApiOperation(value = "设备-添加", notes = "设备-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody SmartfuelDevice smartfuelDevice) {
        smartfuelDeviceService.save(smartfuelDevice);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param smartfuelDevice
     * @return
     */
    @AutoLog(value = "设备-编辑")
    @ApiOperation(value = "设备-编辑", notes = "设备-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<?> edit(@RequestBody SmartfuelDevice smartfuelDevice) {
        smartfuelDeviceService.updateById(smartfuelDevice);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "设备-通过id删除")
    @ApiOperation(value = "设备-通过id删除", notes = "设备-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        smartfuelDeviceService.delMain(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "设备-批量删除")
    @ApiOperation(value = "设备-批量删除", notes = "设备-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.smartfuelDeviceService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 导出
     *
     * @return
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SmartfuelDevice smartfuelDevice) {
        return super.exportXls(request, smartfuelDevice, SmartfuelDevice.class, "设备");
    }

    /**
     * 导入
     *
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, SmartfuelDevice.class);
    }
    /*---------------------------------主表处理-end-------------------------------------*/


    /*--------------------------------子表处理-孪生属性-begin----------------------------------------------*/

    /**
     * 通过主表ID查询
     *
     * @return
     */
    @AutoLog(value = "孪生属性-通过主表ID查询")
    @ApiOperation(value = "孪生属性-通过主表ID查询", notes = "孪生属性-通过主表ID查询")
    @GetMapping(value = "/listSmartfuelDeviceTwinByMainId")
    public Result<?> listSmartfuelDeviceTwinByMainId(SmartfuelDeviceTwin smartfuelDeviceTwin,
                                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                     HttpServletRequest req) {
        QueryWrapper<SmartfuelDeviceTwin> queryWrapper = QueryGenerator.initQueryWrapper(smartfuelDeviceTwin, req.getParameterMap());
        Page<SmartfuelDeviceTwin> page = new Page<SmartfuelDeviceTwin>(pageNo, pageSize);
        IPage<SmartfuelDeviceTwin> pageList = smartfuelDeviceTwinService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param smartfuelDeviceTwin
     * @return
     */
    @AutoLog(value = "孪生属性-添加")
    @ApiOperation(value = "孪生属性-添加", notes = "孪生属性-添加")
    @PostMapping(value = "/addSmartfuelDeviceTwin")
    public Result<?> addSmartfuelDeviceTwin(@RequestBody SmartfuelDeviceTwin smartfuelDeviceTwin) {
        smartfuelDeviceTwinService.save(smartfuelDeviceTwin);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param smartfuelDeviceTwin
     * @return
     */
    @AutoLog(value = "孪生属性-编辑")
    @ApiOperation(value = "孪生属性-编辑", notes = "孪生属性-编辑")
    @RequestMapping(value = "/editSmartfuelDeviceTwin", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<?> editSmartfuelDeviceTwin(@RequestBody SmartfuelDeviceTwin smartfuelDeviceTwin) {
        smartfuelDeviceTwinService.updateById(smartfuelDeviceTwin);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "孪生属性-通过id删除")
    @ApiOperation(value = "孪生属性-通过id删除", notes = "孪生属性-通过id删除")
    @DeleteMapping(value = "/deleteSmartfuelDeviceTwin")
    public Result<?> deleteSmartfuelDeviceTwin(@RequestParam(name = "id", required = true) String id) {
        smartfuelDeviceTwinService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "孪生属性-批量删除")
    @ApiOperation(value = "孪生属性-批量删除", notes = "孪生属性-批量删除")
    @DeleteMapping(value = "/deleteBatchSmartfuelDeviceTwin")
    public Result<?> deleteBatchSmartfuelDeviceTwin(@RequestParam(name = "ids", required = true) String ids) {
        this.smartfuelDeviceTwinService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 导出
     *
     * @return
     */
    @RequestMapping(value = "/exportSmartfuelDeviceTwin")
    public ModelAndView exportSmartfuelDeviceTwin(HttpServletRequest request, SmartfuelDeviceTwin smartfuelDeviceTwin) {
        // Step.1 组装查询条件
        QueryWrapper<SmartfuelDeviceTwin> queryWrapper = QueryGenerator.initQueryWrapper(smartfuelDeviceTwin, request.getParameterMap());
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        // Step.2 获取导出数据
        List<SmartfuelDeviceTwin> pageList = smartfuelDeviceTwinService.list(queryWrapper);
        List<SmartfuelDeviceTwin> exportList = null;

        // 过滤选中数据
        String selections = request.getParameter("selections");
        if (oConvertUtils.isNotEmpty(selections)) {
            List<String> selectionList = Arrays.asList(selections.split(","));
            exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        } else {
            exportList = pageList;
        }

        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "孪生属性"); //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.CLASS, SmartfuelDeviceTwin.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("孪生属性报表", "导出人:" + sysUser.getRealname(), "孪生属性"));
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }

    /**
     * 导入
     *
     * @return
     */
    @RequestMapping(value = "/importSmartfuelDeviceTwin/{mainId}")
    public Result<?> importSmartfuelDeviceTwin(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<SmartfuelDeviceTwin> list = ExcelImportUtil.importExcel(file.getInputStream(), SmartfuelDeviceTwin.class, params);
                for (SmartfuelDeviceTwin temp : list) {
                    temp.setDeviceId(mainId);
                }
                long start = System.currentTimeMillis();
                smartfuelDeviceTwinService.saveBatch(list);
                log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
                return Result.OK("文件导入成功！数据行数：" + list.size());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.error("文件导入失败！");
    }

    /*--------------------------------子表处理-孪生属性-end----------------------------------------------*/

    /*--------------------------------子表处理-设备属性-begin----------------------------------------------*/

    /**
     * 通过主表ID查询
     *
     * @return
     */
    @AutoLog(value = "设备属性-通过主表ID查询")
    @ApiOperation(value = "设备属性-通过主表ID查询", notes = "设备属性-通过主表ID查询")
    @GetMapping(value = "/listSmartfuelDeviceAttributeByMainId")
    public Result<?> listSmartfuelDeviceAttributeByMainId(SmartfuelDeviceAttribute smartfuelDeviceAttribute,
                                                          @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                          @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                          HttpServletRequest req) {
        QueryWrapper<SmartfuelDeviceAttribute> queryWrapper = QueryGenerator.initQueryWrapper(smartfuelDeviceAttribute, req.getParameterMap());
        Page<SmartfuelDeviceAttribute> page = new Page<SmartfuelDeviceAttribute>(pageNo, pageSize);
        IPage<SmartfuelDeviceAttribute> pageList = smartfuelDeviceAttributeService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param smartfuelDeviceAttribute
     * @return
     */
    @AutoLog(value = "设备属性-添加")
    @ApiOperation(value = "设备属性-添加", notes = "设备属性-添加")
    @PostMapping(value = "/addSmartfuelDeviceAttribute")
    public Result<?> addSmartfuelDeviceAttribute(@RequestBody SmartfuelDeviceAttribute smartfuelDeviceAttribute) {
        smartfuelDeviceAttributeService.save(smartfuelDeviceAttribute);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param smartfuelDeviceAttribute
     * @return
     */
    @AutoLog(value = "设备属性-编辑")
    @ApiOperation(value = "设备属性-编辑", notes = "设备属性-编辑")
    @RequestMapping(value = "/editSmartfuelDeviceAttribute", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<?> editSmartfuelDeviceAttribute(@RequestBody SmartfuelDeviceAttribute smartfuelDeviceAttribute) {
        smartfuelDeviceAttributeService.updateById(smartfuelDeviceAttribute);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "设备属性-通过id删除")
    @ApiOperation(value = "设备属性-通过id删除", notes = "设备属性-通过id删除")
    @DeleteMapping(value = "/deleteSmartfuelDeviceAttribute")
    public Result<?> deleteSmartfuelDeviceAttribute(@RequestParam(name = "id", required = true) String id) {
        smartfuelDeviceAttributeService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "设备属性-批量删除")
    @ApiOperation(value = "设备属性-批量删除", notes = "设备属性-批量删除")
    @DeleteMapping(value = "/deleteBatchSmartfuelDeviceAttribute")
    public Result<?> deleteBatchSmartfuelDeviceAttribute(@RequestParam(name = "ids", required = true) String ids) {
        this.smartfuelDeviceAttributeService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 导出
     *
     * @return
     */
    @RequestMapping(value = "/exportSmartfuelDeviceAttribute")
    public ModelAndView exportSmartfuelDeviceAttribute(HttpServletRequest request, SmartfuelDeviceAttribute smartfuelDeviceAttribute) {
        // Step.1 组装查询条件
        QueryWrapper<SmartfuelDeviceAttribute> queryWrapper = QueryGenerator.initQueryWrapper(smartfuelDeviceAttribute, request.getParameterMap());
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        // Step.2 获取导出数据
        List<SmartfuelDeviceAttribute> pageList = smartfuelDeviceAttributeService.list(queryWrapper);
        List<SmartfuelDeviceAttribute> exportList = null;

        // 过滤选中数据
        String selections = request.getParameter("selections");
        if (oConvertUtils.isNotEmpty(selections)) {
            List<String> selectionList = Arrays.asList(selections.split(","));
            exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        } else {
            exportList = pageList;
        }

        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "设备属性"); //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.CLASS, SmartfuelDeviceAttribute.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("设备属性报表", "导出人:" + sysUser.getRealname(), "设备属性"));
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }

    /**
     * 导入
     *
     * @return
     */
    @RequestMapping(value = "/importSmartfuelDeviceAttribute/{mainId}")
    public Result<?> importSmartfuelDeviceAttribute(HttpServletRequest request, HttpServletResponse response, @PathVariable("mainId") String mainId) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<SmartfuelDeviceAttribute> list = ExcelImportUtil.importExcel(file.getInputStream(), SmartfuelDeviceAttribute.class, params);
                for (SmartfuelDeviceAttribute temp : list) {
                    temp.setDeviceId(mainId);
                }
                long start = System.currentTimeMillis();
                smartfuelDeviceAttributeService.saveBatch(list);
                log.info("消耗时间" + (System.currentTimeMillis() - start) + "毫秒");
                return Result.OK("文件导入成功！数据行数：" + list.size());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.error("文件导入失败！");
    }

    /*--------------------------------子表处理-设备属性-end----------------------------------------------*/


}
