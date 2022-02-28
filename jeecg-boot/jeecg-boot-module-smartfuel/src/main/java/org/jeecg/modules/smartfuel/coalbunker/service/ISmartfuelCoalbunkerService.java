package org.jeecg.modules.smartfuel.coalbunker.service;

import org.jeecg.modules.smartfuel.coalbunker.entity.SmartfuelCoalbunker;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 煤仓分层细节
 * @Author: jeecg-boot
 * @Date:   2022-02-24
 * @Version: V1.0
 */
public interface ISmartfuelCoalbunkerService extends IService<SmartfuelCoalbunker> {
    /**
     * 得到指定煤仓前一次的煤仓分层记录
     * @param index 煤仓编号
     * @return
     */
    List<SmartfuelCoalbunker> GetHistoryCoalBunkers(String index);
}
