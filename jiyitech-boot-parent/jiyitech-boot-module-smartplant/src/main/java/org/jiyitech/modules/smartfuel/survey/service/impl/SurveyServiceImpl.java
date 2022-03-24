package org.jiyitech.modules.smartfuel.survey.service.impl;

import com.alibaba.fastjson.JSON;

import lombok.var;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jeecg.common.api.vo.Result;
import org.jiyitech.modules.smartfuel.survey.service.SurveyService;
import org.springframework.beans.factory.annotation.Value;

/**
 * @program: jiyitech-boot01
 * @description: SurveyService接口实现类
 * @author: xiongwenjun
 * @create: 2022-03-24 13:19
 **/

public class SurveyServiceImpl implements SurveyService {

    @Value("${SurveyAddress}")
    private String ip;

    @Override
    public Object getCoalresults() {
        String url = "http://" + ip + "/coalresults/interfaces/FieldsResults.json";
        final Request request = new Request.Builder().url(url).get().build();
        Response response = null;
        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                var result = JSON.parse(response.body().string());
                return result;
            }
        } catch (Exception e) {
            return Result.error("获取失败！"); // 获取失败，直接返回error
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    @Override
    public Object GetStockyardMaterials(int StockyardID, double LevelHeight, double X1, double X2) {
        String url =
                "http://"
                        + ip
                        + ":2467/BucketWheel/GetStockyardMaterials?StockyardID="
                        + StockyardID
                        + "&LevelHeight="
                        + LevelHeight
                        + "&X1="
                        + X1
                        + "&X2="
                        + X2;
        final Request request = new Request.Builder().url(url).get().build();
        Response response = null;
        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                var result = JSON.parse(response.body().string());
                return result; // 若成功直接返回结果
            }
        } catch (Exception e) {
            return Result.error("获取失败！"); // 获取失败，直接返回error
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    @Override
    public Object GetCurMat(int BucketWheelID) {
        String url = "http://" + ip + ":2467/BucketWheel/GetCurMat?BucketWheelID=" + BucketWheelID;
        final Request request = new Request.Builder().url(url).get().build();
        Response response = null;
        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                var result = JSON.parse(response.body().string());
                return result; // 若成功直接返回结果
            }
        } catch (Exception e) {
            return Result.error("获取失败！"); // 获取失败，直接返回error
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    @Override
    public Object GetFieldHeights(int StockyardID, double OffsetX, String Dists) {
        String url =
                "http://"
                        + ip
                        + ":2467/BucketWheel/GetFieldHeights?StockyardID="
                        + StockyardID
                        + "&OffsetX="
                        + OffsetX
                        + "&Dists="
                        + Dists;
        final Request request = new Request.Builder().url(url).get().build();
        Response response = null;
        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                var result = JSON.parse(response.body().string());
                return result; // 若成功直接返回结果
            }
        } catch (Exception e) {
            return Result.error("获取失败！"); // 获取失败，直接返回error
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    @Override
    public Object GetVersion() {
        String url = "http://" + ip + ":2467/BucietWheel/Version";
        final Request request = new Request.Builder().url(url).get().build();
        Response response = null;
        OkHttpClient okHttpClient = new OkHttpClient();
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                var result = JSON.parse(response.body().string());
                return result; // 若成功直接返回结果
            }
        } catch (Exception e) {
            return Result.error("获取失败！"); // 获取失败，直接返回error
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }
}

