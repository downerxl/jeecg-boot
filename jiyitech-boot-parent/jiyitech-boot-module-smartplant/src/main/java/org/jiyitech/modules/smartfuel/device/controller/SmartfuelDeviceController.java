package org.jiyitech.modules.smartfuel.device.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jiyitech.modules.smartfuel.device.entity.SmartfuelDeviceTwin;
import org.jiyitech.modules.smartfuel.device.entity.SmartfuelDeviceAttribute;
import org.jiyitech.modules.smartfuel.device.entity.SmartfuelDevice;
import org.jiyitech.modules.smartfuel.device.vo.DebiceTwinDataVo;
import org.jiyitech.modules.smartfuel.device.vo.SmartfuelDevicePage;
import org.jiyitech.modules.smartfuel.device.service.ISmartfuelDeviceService;
import org.jiyitech.modules.smartfuel.device.service.ISmartfuelDeviceTwinService;
import org.jiyitech.modules.smartfuel.device.service.ISmartfuelDeviceAttributeService;
import org.jiyitech.modules.smartfuel.util.ClazzUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 设备
 * @Author: jeecg-boot
 * @Date:   2022-03-24
 * @Version: V1.0
 */
@Api(tags="设备")
@RestController
@RequestMapping("/device/smartfuelDevice")
@Slf4j
public class SmartfuelDeviceController {
	@Autowired
	private ISmartfuelDeviceService smartfuelDeviceService;
	@Autowired
	private ISmartfuelDeviceTwinService smartfuelDeviceTwinService;
	@Autowired
	private ISmartfuelDeviceAttributeService smartfuelDeviceAttributeService;
	
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
	@ApiOperation(value="设备-分页列表查询", notes="设备-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(SmartfuelDevice smartfuelDevice,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<SmartfuelDevice> queryWrapper = QueryGenerator.initQueryWrapper(smartfuelDevice, req.getParameterMap());
		Page<SmartfuelDevice> page = new Page<SmartfuelDevice>(pageNo, pageSize);
		IPage<SmartfuelDevice> pageList = smartfuelDeviceService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	 /**
	  * 获取debiceTwin指定时间内的历史曲线
	  * @param debiceTwinDataVo 设备参数集合
	  * @return
	  */
	@ApiOperation(value = "获取debiceTwin指定时间内的历史曲线",notes = "获取debiceTwin指定时间内的历史曲线")
	@GetMapping(value = "/getDeviceHistoryCurve")
	public Result<?> getDeviceHistoryCurve(DebiceTwinDataVo debiceTwinDataVo) {
		List<String> clazzName = ClazzUtils.getClazzName("org.jiyitech.modules.smartfuel.job", false);
		return Result.OK(smartfuelDeviceService.getHistoryCurve(debiceTwinDataVo));
	}

	 /**
	  * 获取debiceTwin指定时间内大于某一个阈值的时长(小时)
	  * @param debiceTwinDataVo
	  * @return
	  */
	@ApiOperation(value = "获取debiceTwin指定时间内大于某一个阈值的时长(小时)",notes = "获取debiceTwin指定时间内大于某一个阈值的时长(小时)")
	@GetMapping(value = "/getDeviceTwinGeRange")
	public Result<?> getDeviceTwinGeRange(DebiceTwinDataVo debiceTwinDataVo) {
		return Result.OK(smartfuelDeviceService.getDeviceTwinGeRange(debiceTwinDataVo));
	}
	
	/**
	 *   添加
	 *
	 * @param smartfuelDevicePage
	 * @return
	 */
	@AutoLog(value = "设备-添加")
	@ApiOperation(value="设备-添加", notes="设备-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody SmartfuelDevicePage smartfuelDevicePage) {
		SmartfuelDevice smartfuelDevice = new SmartfuelDevice();
		BeanUtils.copyProperties(smartfuelDevicePage, smartfuelDevice);
		smartfuelDeviceService.saveMain(smartfuelDevice, smartfuelDevicePage.getSmartfuelDeviceTwinList(),smartfuelDevicePage.getSmartfuelDeviceAttributeList());
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param smartfuelDevicePage
	 * @return
	 */
	@AutoLog(value = "设备-编辑")
	@ApiOperation(value="设备-编辑", notes="设备-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<?> edit(@RequestBody SmartfuelDevicePage smartfuelDevicePage) {
		SmartfuelDevice smartfuelDevice = new SmartfuelDevice();
		BeanUtils.copyProperties(smartfuelDevicePage, smartfuelDevice);
		SmartfuelDevice smartfuelDeviceEntity = smartfuelDeviceService.getById(smartfuelDevice.getId());
		if(smartfuelDeviceEntity==null) {
			return Result.error("未找到对应数据");
		}
		smartfuelDeviceService.updateMain(smartfuelDevice, smartfuelDevicePage.getSmartfuelDeviceTwinList(),smartfuelDevicePage.getSmartfuelDeviceAttributeList());
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "设备-通过id删除")
	@ApiOperation(value="设备-通过id删除", notes="设备-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		smartfuelDeviceService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "设备-批量删除")
	@ApiOperation(value="设备-批量删除", notes="设备-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.smartfuelDeviceService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "设备-通过id查询")
	@ApiOperation(value="设备-通过id查询", notes="设备-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		SmartfuelDevice smartfuelDevice = smartfuelDeviceService.getById(id);
		if(smartfuelDevice==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(smartfuelDevice);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "孪生属性通过主表ID查询")
	@ApiOperation(value="孪生属性主表ID查询", notes="孪生属性-通主表ID查询")
	@GetMapping(value = "/querySmartfuelDeviceTwinByMainId")
	public Result<?> querySmartfuelDeviceTwinListByMainId(@RequestParam(name="id",required=true) String id) {
		List<SmartfuelDeviceTwin> smartfuelDeviceTwinList = smartfuelDeviceTwinService.selectByMainId(id);
		return Result.OK(smartfuelDeviceTwinList);
	}
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "设备属性通过主表ID查询")
	@ApiOperation(value="设备属性主表ID查询", notes="设备属性-通主表ID查询")
	@GetMapping(value = "/querySmartfuelDeviceAttributeByMainId")
	public Result<?> querySmartfuelDeviceAttributeListByMainId(@RequestParam(name="id",required=true) String id) {
		List<SmartfuelDeviceAttribute> smartfuelDeviceAttributeList = smartfuelDeviceAttributeService.selectByMainId(id);
		return Result.OK(smartfuelDeviceAttributeList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param smartfuelDevice
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SmartfuelDevice smartfuelDevice) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<SmartfuelDevice> queryWrapper = QueryGenerator.initQueryWrapper(smartfuelDevice, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<SmartfuelDevice> queryList = smartfuelDeviceService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<SmartfuelDevice> smartfuelDeviceList = new ArrayList<SmartfuelDevice>();
      if(oConvertUtils.isEmpty(selections)) {
          smartfuelDeviceList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          smartfuelDeviceList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<SmartfuelDevicePage> pageList = new ArrayList<SmartfuelDevicePage>();
      for (SmartfuelDevice main : smartfuelDeviceList) {
          SmartfuelDevicePage vo = new SmartfuelDevicePage();
          BeanUtils.copyProperties(main, vo);
          List<SmartfuelDeviceTwin> smartfuelDeviceTwinList = smartfuelDeviceTwinService.selectByMainId(main.getId());
          vo.setSmartfuelDeviceTwinList(smartfuelDeviceTwinList);
          List<SmartfuelDeviceAttribute> smartfuelDeviceAttributeList = smartfuelDeviceAttributeService.selectByMainId(main.getId());
          vo.setSmartfuelDeviceAttributeList(smartfuelDeviceAttributeList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "设备列表");
      mv.addObject(NormalExcelConstants.CLASS, SmartfuelDevicePage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("设备数据", "导出人:"+sysUser.getRealname(), "设备"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
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
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<SmartfuelDevicePage> list = ExcelImportUtil.importExcel(file.getInputStream(), SmartfuelDevicePage.class, params);
              for (SmartfuelDevicePage page : list) {
                  SmartfuelDevice po = new SmartfuelDevice();
                  BeanUtils.copyProperties(page, po);
                  smartfuelDeviceService.saveMain(po, page.getSmartfuelDeviceTwinList(),page.getSmartfuelDeviceAttributeList());
              }
              return Result.OK("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.OK("文件导入失败！");
    }

}
