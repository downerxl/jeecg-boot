package org.jeecg.modules.smartfuel.coalbunker.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.smartfuel.coalbunker.entity.SmartfuelCoalbunker;
import org.jeecg.modules.smartfuel.coalbunker.service.ISmartfuelCoalbunkerService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 煤仓分层细节
 * @Author: jeecg-boot
 * @Date:   2022-02-24
 * @Version: V1.0
 */
@Api(tags="煤仓分层细节")
@RestController
@RequestMapping("/coalbunker/smartfuelCoalbunker")
@Slf4j
public class SmartfuelCoalbunkerController extends JeecgController<SmartfuelCoalbunker, ISmartfuelCoalbunkerService> {
	@Autowired
	private ISmartfuelCoalbunkerService smartfuelCoalbunkerService;
	
	/**
	 * 分页列表查询
	 *
	 * @param smartfuelCoalbunker
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "煤仓分层细节-分页列表查询")
	@ApiOperation(value="煤仓分层细节-分页列表查询", notes="煤仓分层细节-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(SmartfuelCoalbunker smartfuelCoalbunker,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<SmartfuelCoalbunker> queryWrapper = QueryGenerator.initQueryWrapper(smartfuelCoalbunker, req.getParameterMap());
		Page<SmartfuelCoalbunker> page = new Page<SmartfuelCoalbunker>(pageNo, pageSize);
		IPage<SmartfuelCoalbunker> pageList = smartfuelCoalbunkerService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param smartfuelCoalbunker
	 * @return
	 */
	@AutoLog(value = "煤仓分层细节-添加")
	@ApiOperation(value="煤仓分层细节-添加", notes="煤仓分层细节-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody SmartfuelCoalbunker smartfuelCoalbunker) {
		smartfuelCoalbunkerService.save(smartfuelCoalbunker);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param smartfuelCoalbunker
	 * @return
	 */
	@AutoLog(value = "煤仓分层细节-编辑")
	@ApiOperation(value="煤仓分层细节-编辑", notes="煤仓分层细节-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody SmartfuelCoalbunker smartfuelCoalbunker) {
		smartfuelCoalbunkerService.updateById(smartfuelCoalbunker);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "煤仓分层细节-通过id删除")
	@ApiOperation(value="煤仓分层细节-通过id删除", notes="煤仓分层细节-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		smartfuelCoalbunkerService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "煤仓分层细节-批量删除")
	@ApiOperation(value="煤仓分层细节-批量删除", notes="煤仓分层细节-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.smartfuelCoalbunkerService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "煤仓分层细节-通过id查询")
	@ApiOperation(value="煤仓分层细节-通过id查询", notes="煤仓分层细节-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		SmartfuelCoalbunker smartfuelCoalbunker = smartfuelCoalbunkerService.getById(id);
		if(smartfuelCoalbunker==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(smartfuelCoalbunker);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param smartfuelCoalbunker
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SmartfuelCoalbunker smartfuelCoalbunker) {
        return super.exportXls(request, smartfuelCoalbunker, SmartfuelCoalbunker.class, "煤仓分层细节");
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
        return super.importExcel(request, response, SmartfuelCoalbunker.class);
    }

}
