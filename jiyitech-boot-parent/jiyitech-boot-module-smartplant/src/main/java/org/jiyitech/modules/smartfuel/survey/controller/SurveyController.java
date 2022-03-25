package org.jiyitech.modules.smartfuel.survey.controller;

import cn.hutool.core.io.resource.ClassPathResource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jiyitech.modules.smartfuel.survey.service.ISurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
    private ISurveyService surveyService;

    /**
     * 获取当前每煤层信息
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
     * 获取指定煤场所有分层信息
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

        var result = surveyService.GetStockyardMaterials(StockyardID, LevelHeight, X1, X2);
        if (result == null) {
            return Result.error("查询结果为null或者查询失败");
        }
        return Result.OK(result);
    }

    /**
     * 获取斗轮机当前作业模型对应的CoalId信息
     * @param BucketWheelID 斗轮机编号
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
     * 获取煤场纵截面指定间隔的最大高度
     * @param StockyardID 煤场（煤堆）编号
     * @param OffsetX 截面距离轨道距离
     * @param Dists 点间距，逗号分割的字符串
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
     * 获取三维程序版本号
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

    /**
     * 解析某时刻相应的json数据
     * @param jsonTime 时间
     * @return
     * @throws ParseException
     */
    @AutoLog(value = "解析某时刻相应的json数据")
    @ApiOperation(value = "解析某时刻相应的json数据", notes = "解析某时刻相应的json数据")
    @GetMapping(value = "/GetJsonResultsByTime")
    public Result<?> GetJsonResultsByTime(String jsonTime) throws ParseException {

        // String str = "2022-03-15 18:50:11";

        StringBuilder sb = new StringBuilder();
        try {
            String encoding = "UTF-8";
            ClassPathResource classPathResource = new ClassPathResource("pro/1.txt");
            InputStream inputStream = classPathResource.getStream();
            InputStreamReader read = new InputStreamReader(inputStream, encoding); // 考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                sb.append(lineTxt);
            }
            read.close();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("读取文件内容出错");
        }

        if (sb.toString().isEmpty() && sb.toString().equals("")) { // 判断文件读取内容是否为空
            return Result.error("文件内容为空");
        } else {
            String a = "\\[\\[\\{";
            String b = "\":[[{";
            String c = "}]]";
            String d = "}]],\"";
            String e = "{\"";
            String f = "}";
            // 自定义处理字符串格式
            String replaceStr = sb.toString().replaceAll(a, b); // 将[[{  替换成 ":[[{
            String replaceStr1 = replaceStr.replaceAll(c, d); // 将}]]   替换成  }]],"
            String replaceStr2 = e + replaceStr1; // 在头部增加{“
            StringBuilder sb1 = new StringBuilder();
            sb1.append(replaceStr2).replace(sb1.length() - 2, sb1.length() - 1, f); // 将尾部," 替换成 }
            // 将json封装成map
            Map maps = (Map) JSON.parse(sb1.toString());
            for (Object map : maps.entrySet()) {
                // 遍历map取出key值并格式化，因为自定义处理的时候导致key有空格
                String key = ((Map.Entry) map).getKey().toString();
                SimpleDateFormat lsdStrFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date strD = lsdStrFormat.parse(key);
                String formatDate = lsdStrFormat.format(strD);

                // 如果有相同时间的key，直接返回value
                if (formatDate.equals(jsonTime)) {
                    // 处理value，每个对象按height高度倒序输出
                    JSONArray jsonArray = JSONArray.parseArray(((Map.Entry) map).getValue().toString());
                    List list = JSON.parseArray(((Map.Entry) map).getValue().toString()); // 将value转为list对象
                    List list2 = new ArrayList<>(); // 存储排序之后的结果

                    for (int i = 0; i < list.size(); i++) { // 遍历value list对象，处理每一条结果
                        List list1 = new ArrayList<>();
                        list1 = JSON.parseArray(list.get(i).toString()); // 存储每一条结果的list

                        var last = list1.get(list1.size() - 1);
                        list1.remove(list1.size() - 1); // 去除每一条结果的最后一个对象数据 {"weight": 441.0,"height": "总量"}

                        List<JSONObject> list3 = new ArrayList<JSONObject>();
                        for (int j = 0; j < list1.size(); j++) { // 将每一条结果转化为JSONObject对象list
                            list3.add((JSONObject) list1.get(j));
                        }

                        // 对JSONObject对象list中，按照height排序
                        Collections.sort(
                                list3,
                                new Comparator<JSONObject>() {
                                    private static final String KEY_NAME = "height";

                                    @Override
                                    public int compare(JSONObject a, JSONObject b) {
                                        BigDecimal valA = null;
                                        BigDecimal valB = null;
                                        try {
                                            valA = (BigDecimal) a.get(KEY_NAME);
                                            valB = (BigDecimal) b.get(KEY_NAME);
                                        } catch (JSONException e) {
                                            log.error("排序失败");
                                        }
                                        return -valA.compareTo(valB);
                                    }
                                });

                        list3.add((JSONObject) last); // 加入最后一条数据 {"weight": 441.0,"height": "总量"}
                        list2.add(list3); // 将排序好的存入返回list2中
                    }
                    return Result.OK(list2);
                }
            }
            return Result.error("没有查询到结果");
        }
    }

    /**
     * 解析json数据返回时间戳
     * @return
     * @throws ParseException
     */
    @AutoLog(value = "解析json数据返回时间戳")
    @ApiOperation(value = "解析json数据返回时间戳", notes = "解析json数据返回时间戳")
    @GetMapping(value = "/GetJsonTime")
    public Result<?> GetJsonTime() throws ParseException {

        // String str = "2022-03-15 18:50:11";

        StringBuilder sb = new StringBuilder();
        try {
            String encoding = "UTF-8";
            ClassPathResource classPathResource = new ClassPathResource("pro/1.txt");
            InputStream inputStream = classPathResource.getStream();
            InputStreamReader read = new InputStreamReader(inputStream, encoding); // 考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                sb.append(lineTxt);
            }
            read.close();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("读取文件内容出错");
        }
        if (sb.toString().isEmpty() && sb.toString().equals("")) { // 判断文件读取内容是否为空
            return Result.error("文件内容为空");
        } else {
            String a = "\\[\\[\\{";
            String b = "\":[[{";
            String c = "}]]";
            String d = "}]],\"";
            String e = "{\"";
            String f = "}";
            // 自定义处理字符串格式
            String replaceStr = sb.toString().replaceAll(a, b); // 将[[{  替换成 ":[[{
            String replaceStr1 = replaceStr.replaceAll(c, d); // 将}]]   替换成  }]],"
            String replaceStr2 = e + replaceStr1; // 在头部增加{“
            StringBuilder sb1 = new StringBuilder();
            sb1.append(replaceStr2).replace(sb1.length() - 2, sb1.length() - 1, f); // 将尾部," 替换成 }
            // 将json封装成map
            Map maps = (Map) JSON.parse(sb1.toString());
            List<String> dateList = new ArrayList<>();
            List<String> dateList1 = new ArrayList<>();
            for (Object map : maps.entrySet()) {
                // 遍历map取出key值并格式化，因为自定义处理的时候导致key有空格
                String key = ((Map.Entry) map).getKey().toString();
                SimpleDateFormat lsdStrFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date strD = lsdStrFormat.parse(key);
                String formatDate = lsdStrFormat.format(strD);
                dateList.add(formatDate);
            }
            dateList1 = dateList.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());//返回时间倒序排序
            return Result.OK(dateList1);
        }
    }
}

