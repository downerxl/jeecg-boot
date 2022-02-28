package org.jeecg.modules.smartfuel.coalbunker.service.impl;

import cn.hutool.core.date.DateTime;
import lombok.var;
import org.jeecg.modules.smartfuel.coalbunker.entity.SmartfuelCoalbunker;
import org.jeecg.modules.smartfuel.coalbunker.mapper.SmartfuelCoalbunkerMapper;
import org.jeecg.modules.smartfuel.coalbunker.service.ISmartfuelCoalbunkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 煤仓分层细节
 * @Author: jeecg-boot
 * @Date:   2022-02-24
 * @Version: V1.0
 */
@Service
public class SmartfuelCoalbunkerServiceImpl extends ServiceImpl<SmartfuelCoalbunkerMapper, SmartfuelCoalbunker> implements ISmartfuelCoalbunkerService {

    @Autowired
    SmartfuelCoalbunkerMapper smartfuelCoalbunkerMapper;
    /**
     * 得到指定煤仓前一次的煤仓分层记录
     * @param index 煤仓编号
     * @return
     */
    @Override
    public List<SmartfuelCoalbunker> GetHistoryCoalBunkers(String index) {
        //数据源暂时没有
        List<SmartfuelCoalbunker> alllist = new ArrayList<SmartfuelCoalbunker>();
        var bunkerHeight = GetCurrentHeight(index);
        var coalbunkers=  alllist.stream().filter(x->x.getBunkerId()==index).collect(Collectors.toList());//获取某个煤仓全部煤层
        if(coalbunkers.size()==0){//当煤仓中一个煤层都没有时，全用默认煤种填充
            var New = new SmartfuelCoalbunker();
            New.setCoalId("一个默认值");
            New.setBunkerTime(DateTime.now());
            New.setBunkerHeight(bunkerHeight);
            New.setBunkerIndex(0);
            New.setBunkerId(index);
            New.setBunkerVolumn(GetBunkerVolumn(bunkerHeight));
            coalbunkers.add(New);
        }
        coalbunkers.sort((o1, o2) -> o1.getBunkerIndex()-o2.getBunkerIndex());//按照煤层顺序排列
        return coalbunkers;
    }

    /**
     * 根据高度获取煤仓体积
     * @param height
     * @return
     */
    private double GetBunkerVolumn(double height){
        return 999;
    }

    /**
     * 根据体积获取煤仓高度
     * @param volumn
     * @return
     */
    private double GetBunkerHeight(double volumn){
        return 999;
    }

    /**
     * 获取一个煤仓的出仓量
     * @param index 煤仓编号
     * @return
     */
    private double GetCoalOutOfTheBunker(String index){
        var bunkerSpeed = 3;//煤仓出仓速度,数据暂时用定值代替
        var time = 10;//程序执行的间隔
        return bunkerSpeed * time;
    }

    /**
     * 获取指定煤仓的实时煤位高度
     * @param index 煤仓编号
     * @return
     */
    private double GetCurrentHeight(String index){
        return 15.0;
    }
    /**
     * 经过一段时间后煤仓的实际分层
     * @param bunker_id 煤仓编号
     * @param increase 是否有煤进仓
     * @param CoalID 新进仓的煤种Guid
     * @return
     */
    public List<SmartfuelCoalbunker> GetCurrentCoalBunker(String bunker_id, Boolean increase,String CoalID){
        var bunkerHeight = GetCurrentHeight(bunker_id);//煤仓实时高度,数据暂时用定值代替
        var quality = GetCoalOutOfTheBunker(bunker_id);
        var coalbunkers = GetHistoryCoalBunkers(bunker_id);

        Integer deleteBunker = 0;//出仓后煤量到了哪个煤层
        Double totalQuality = 0.0;
        for (var item :
                coalbunkers) {
            totalQuality += item.getBunkerVolumn()*item.getDensity();
            if (totalQuality-quality>0){
                deleteBunker = item.getBunkerIndex();
                item.setBunkerVolumn(totalQuality-quality);
            }
        }
        //删除放空的煤层，修改剩下煤层的层次编号和煤层高度
        Integer finalDeleteBunker = deleteBunker;
        coalbunkers.removeIf(x->x.getBunkerIndex()< finalDeleteBunker);
        if(coalbunkers.size()>0){
            coalbunkers.stream().map(x->{
                x.setBunkerIndex(x.getBunkerIndex()-finalDeleteBunker);
                x.setBunkerHeight(GetBunkerHeight(x.getBunkerVolumn()));
                return x;
            });
        }
        if(!increase){
            //当没有煤种入仓时,将最上层煤层高度同步为实时的煤层高度，并更新最上层的煤量
            if(coalbunkers.size()>0){
                coalbunkers.get(coalbunkers.size()-1).setBunkerHeight(bunkerHeight);
                var height = coalbunkers.size()>1?coalbunkers.get(coalbunkers.size()-2).getBunkerHeight():0.0;
                coalbunkers.get(coalbunkers.size()-1).setBunkerVolumn(GetBunkerVolumn(bunkerHeight)-GetBunkerVolumn(height));
            }
        }else{
            if(coalbunkers.size()==0){
                //煤层的其他属性如煤种名等其他模块上线再更新
                var New = new SmartfuelCoalbunker();
                New.setCoalId(CoalID);
                New.setBunkerTime(DateTime.now());
                New.setBunkerHeight(bunkerHeight);
                New.setBunkerIndex(0);
                New.setBunkerId(bunker_id);
                New.setBunkerVolumn(GetBunkerVolumn(bunkerHeight));
                coalbunkers.add(New);
            }else {
                var Pre = coalbunkers.get(coalbunkers.size()-1);//煤仓最上层煤的信息
                if(Pre.getCoalId()==CoalID){
                    Pre.setBunkerTime(DateTime.now());
                    Pre.setBunkerHeight(bunkerHeight);
                    var height = coalbunkers.size()>1?coalbunkers.get(coalbunkers.size()-2).getBunkerHeight():0.0;
                    Pre.setBunkerVolumn(GetBunkerVolumn(bunkerHeight)-GetBunkerVolumn(height));
                }else{
                    //煤层的其他属性如煤种名等其他模块上线再更新
                    var New = new SmartfuelCoalbunker();
                    New.setCoalId(CoalID);
                    New.setBunkerTime(DateTime.now());
                    New.setBunkerHeight(bunkerHeight);
                    New.setBunkerIndex(coalbunkers.size()+1);
                    New.setBunkerId(bunker_id);
                    New.setBunkerVolumn(GetBunkerVolumn(bunkerHeight)-GetBunkerVolumn(Pre.getBunkerHeight()));
                    coalbunkers.add(New);
                }
            }
        }
        return coalbunkers;
    }

    /**
     * 煤仓分层主流程
     */
    public void BuankerMainProcess(){
        for (var i= 0;i<5;i++){//代表了有几个煤仓要算
            GetCurrentCoalBunker("",true,"");
        }
    }

    /**
     * 修改煤层高度时根据煤层的位置判断是否超过上下两层高度区间
     * @param height 修改的高度
     * @param number 煤层的层次编号
     * @param list 煤仓中所有煤层
     * @return
     */
    private Boolean IsSection(double height, int number, List<SmartfuelCoalbunker> list)
    {
        if (number<list.size())//当修改层数据不是最上层
        {
            return (height <= list.get(number).getBunkerHeight() && height >= (number == 1 ? 0 : list.get(number-2).getBunkerHeight()));
        }
        else if ( number==list.size() )//当修改层数据是最上层
        {
            return (height >= (number == 1 ? 0 : list.get(number-2).getBunkerHeight()));
        }
        else return false;
    }

    /**
     * 煤仓分层合并重复煤层和去掉为0的煤层
     * @param list 一个煤仓所有煤层
     * @param number 煤层号
     * @param guid 煤种特征码
     * @return
     */
    private List<SmartfuelCoalbunker> MerageAndRemoveZero(List<SmartfuelCoalbunker> list, int number, String guid)
    {
        //将相同煤层合并
        if (list.size() > number)//上层有煤层的情况
        {
            if (list.get(number).getCoalId() == guid)
            {
                list.get(number-1).setBunkerHeight(list.get(number).getBunkerHeight());
                list.get(number-1).setBunkerVolumn(list.get(number).getBunkerVolumn()+list.get(number-1).getBunkerVolumn());
                list.remove(number);
            }
        }
        if (number > 1)//下层有煤层
        {
            if (list.get(number-2).getCoalId() == guid)
            {
                list.get(number-2).setBunkerHeight(list.get(number-1).getBunkerHeight());
                list.get(number-2).setBunkerVolumn(list.get(number-2).getBunkerVolumn()+list.get(number-1).getBunkerVolumn());
                list.remove(number - 1);
            }
        }
        //去掉煤量为0的煤层
        for (int i = list.size() - 1; i > 0; i--)
        {
            if (list.get(i).getBunkerVolumn() == 0)
            {
                list.remove(i);
            }
        }
        return list;
    }

    /**
     * 手动修改煤仓煤层
     * @param coalbunker 煤层数据
     */
    @Transactional
    public void BunkerUpdate(SmartfuelCoalbunker coalbunker){
        var currentHeight = GetCurrentHeight(coalbunker.getBunkerId());
        var coalbunkers = GetHistoryCoalBunkers(coalbunker.getBunkerId());
        var exist = coalbunkers;//存放修改前的煤层
        if(!IsSection(coalbunker.getBunkerHeight(),coalbunker.getBunkerIndex(),coalbunkers)){
            return;
        }
        var Preheight = coalbunker.getBunkerIndex()==1?0.0:coalbunkers.get(coalbunker.getBunkerIndex()-2).getBunkerHeight();
        coalbunker.setBunkerVolumn(GetBunkerVolumn(coalbunker.getBunkerHeight())-GetBunkerVolumn(Preheight));//根据修改后的高度修改煤种体积
        if(coalbunker.getBunkerIndex()==coalbunkers.size()){//当修改的是最上层煤层时
            //煤层的其他属性如煤种名等其他模块上线再更新
            var New = new SmartfuelCoalbunker();
            New.setCoalId("一个默认值");
            New.setBunkerTime(DateTime.now());
            New.setBunkerHeight(currentHeight);
            New.setBunkerIndex(coalbunkers.size()+1);
            New.setBunkerId(coalbunker.getBunkerId());
            New.setBunkerVolumn(GetBunkerVolumn(currentHeight)-GetBunkerVolumn(coalbunker.getBunkerHeight()));
            if(New.getBunkerVolumn()>0) coalbunkers.add(New);//若修改煤层高度下降其余用默认煤种填充
        }else{
            var LaterIndex = coalbunkers.get(coalbunker.getBunkerIndex());//在修改煤层上一层的煤种
            LaterIndex.setBunkerVolumn(GetBunkerVolumn(LaterIndex.getBunkerHeight())-GetBunkerVolumn(coalbunker.getBunkerHeight()));//修改上一层煤种的体积
            if(LaterIndex.getBunkerVolumn()==0)//如果体积修改为0则删除
                coalbunkers.remove(coalbunker.getBunkerIndex());
        }
        //将coalbunkers和修改前的对比进行删改增加
        for (var item :
                exist) {
            var R = coalbunkers.stream().filter(x->x.getId()==item.getId()).collect(Collectors.toList());//判断是否需要删除
            if(R.size()==0){
                smartfuelCoalbunkerMapper.deleteById(item.getId());
                continue;
            }else {
                var Index = R.stream().findFirst().get();
                smartfuelCoalbunkerMapper.updateById(Index);
            }
        }
        for (var item :
                coalbunkers) {
            var R = exist.stream().filter(x -> x.getId() == item.getId()).collect(Collectors.toList());
            if (R.size()==0) smartfuelCoalbunkerMapper.insert(item);
        }
    }

}
