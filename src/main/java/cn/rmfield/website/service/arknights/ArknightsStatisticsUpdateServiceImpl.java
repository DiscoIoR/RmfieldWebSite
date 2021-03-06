package cn.rmfield.website.service.arknights;

import cn.rmfield.website.entity.arknights.ArknightsDiamondHistory;
import cn.rmfield.website.entity.arknights.ArknightsOrderHistory;
import cn.rmfield.website.entity.arknights.ArknightsStatistics;
import cn.rmfield.website.entity.arknights.ArknightsGachaHistory;
import cn.rmfield.website.entity.RfUser;
import cn.rmfield.website.repository.arknights.ArknightsDiamondHistoryRepository;
import cn.rmfield.website.repository.arknights.ArknightsGachaHistoryRepository;
import cn.rmfield.website.repository.arknights.ArknightsOrderHistoryRepository;
import cn.rmfield.website.repository.arknights.ArknightsStatisticsRepostiory;
import cn.rmfield.website.repository.UserRepository;
import cn.rmfield.website.security.LoginUser;
import cn.rmfield.website.utils.RedisCache;
import cn.rmfield.website.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArknightsStatisticsUpdateServiceImpl implements ArknightsStatisticsUpdateService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ArknightsStatisticsRepostiory asRepo;
    @Autowired
    private ArknightsGachaHistoryRepository gachaHistoryRepo;
    @Autowired
    private ArknightsDiamondHistoryRepository diamondHistoryRepo;
    @Autowired
    private ArknightsOrderHistoryRepository orderHistoryRepo;
    @Autowired
    RedisCache redisCache;

    private ArknightsStatistics as;

    @Override
    public ResponseResult updateData(String token) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        RfUser rfUser = userRepo.findByUsername(name);
        this.as = asRepo.findByRfUser_id(rfUser.getId());
        Boolean userinfoResult = saveUserinfo(token);
        Boolean gachaResult = updateGacha(token);
        Boolean diamondResult = updateDiamond(token);
        Boolean orderResult = updateOrder(token);
        asRepo.save(as);
        if(userinfoResult && gachaResult && diamondResult && orderResult) {
            //??????redis????????????????????????
            int rfUserId = 0;
            try {
                LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                rfUserId = loginUser.getRfUser().getId();
                loginUser.getRfUser().setArknightsStatistics(as);
                redisCache.setCacheObject("login:" + rfUserId, loginUser);
                return new ResponseResult(0,"OK");
            } catch (Exception e) {
                e.printStackTrace();
                //???????????????????????????????????????
                try {
                    redisCache.deleteObject("login:" + rfUserId);
                    return new ResponseResult(5,"??????????????????");
                }catch (Exception e2){
                    e2.printStackTrace();
                    return new ResponseResult(5,"??????????????????,??????????????????");
                }
            }
        }else {
            return new ResponseResult(5,"??????????????????");
        }
    }

    public Boolean updateGacha(String token) {
        //???????????????????????????????????????
        List<ArknightsGachaHistory> gachaHistoryList = gachaHistoryRepo.findByArknightsStatistics_id(as.getId());
        Set<Integer> tses = new HashSet<>();
        for (ArknightsGachaHistory gachaHistory : gachaHistoryList) {
            tses.add(gachaHistory.getTs());
        }
        ArknightsGachaData gachaData = new ArknightsGachaData(
                as.getTotalCount(),
                as.getSixCount(), as.getFiveCount(), as.getFourCount(), as.getThreeCount(),
                as.getSixRate(), as.getFiveRate(), as.getFourRate(), as.getThreeRate(),
                tses
        );

        //?????????????????????????????????
        ArknightsGachaDataHandler gachaDataHandler = new ArknightsGachaDataHandler(gachaData);
        ArknightsGachaData gachaData_New = gachaDataHandler.gachaHandler(token);
        List<ArknightsGachaDataEach> gachaDataEachList_New;
        try {
            gachaDataEachList_New = gachaData_New.getArknightsStatisticsDataEaches();
            //?????????????????????????????????->??????
            Collections.reverse(gachaDataEachList_New);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        for (ArknightsGachaDataEach gachaDataEach_New : gachaDataEachList_New) {
            ArknightsGachaHistory gachaHistory_New = new ArknightsGachaHistory(
                    gachaDataEach_New.getTs(),
                    gachaDataEach_New.getPool(),
                    gachaDataEach_New.getName(),
                    gachaDataEach_New.getRarity(),
                    gachaDataEach_New.getIsNew(),
                    as
            );
            gachaHistoryRepo.save(gachaHistory_New);
        }
        as.setSixCount(gachaData_New.getSixCount());
        as.setFiveCount(gachaData_New.getFiveCount());
        as.setFourCount(gachaData_New.getFourCount());
        as.setThreeCount(gachaData_New.getThreeCount());
        as.setSixRate(gachaData_New.getSixRate());
        as.setFiveRate(gachaData_New.getFiveRate());
        as.setFourRate(gachaData_New.getFourRate());
        as.setThreeRate(gachaData_New.getThreeRate());
        as.setTotalCount(gachaData_New.getTotalCount());
        return true;
    }

    public Boolean updateDiamond(String token) {
        //???????????????????????????????????????
        List<ArknightsDiamondHistory> diamondHistoryList = diamondHistoryRepo.findByArknightsStatistics_id(as.getId());
        Set<Integer> tses = new HashSet<>();
        for (ArknightsDiamondHistory diamondHistory : diamondHistoryList) {
            tses.add(diamondHistory.getTs());
        }
        ArknightsDiamondData diamondData = new ArknightsDiamondData(
                as.getDiamondGrossIncome(), as.getDiamondGrossExpenses(), tses);

        //?????????????????????????????????
        ArknightsDiamondDataHanler diamondDataHanler = new ArknightsDiamondDataHanler(diamondData);
        ArknightsDiamondData diamondData_New = diamondDataHanler.diamondHandler(token);
        List<ArknightsDiamondDataEach> diamondDataEachList_New;
        try {
            diamondDataEachList_New = diamondData_New.getDiamondDataEachList();
            //?????????????????????????????????->??????
            Collections.reverse(diamondDataEachList_New);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        for (ArknightsDiamondDataEach diamondDataEach_New : diamondDataEachList_New) {
            ArknightsDiamondHistory diamondHistory_New = new ArknightsDiamondHistory(
                    diamondDataEach_New.getTs(),
                    diamondDataEach_New.getOperation(),
                    diamondDataEach_New.getPlatform(),
                    diamondDataEach_New.getChangeNum(),
                    diamondDataEach_New.getCurrentNum(),
                    as
            );
            diamondHistoryRepo.save(diamondHistory_New);
        }
        as.setDiamondGrossIncome(diamondData_New.getDiamondGrossIncome());
        as.setDiamondGrossExpenses(diamondData_New.getDiamondGrossExpenses());
        return true;
    }

    public Boolean updateOrder(String token) {
        //???????????????????????????????????????
        List<ArknightsOrderHistory> orderHistoryList = orderHistoryRepo.findByArknightsStatistics_id(as.getId());
        Set<String> orderIdSet = new HashSet<>();
        for(ArknightsOrderHistory orderHistory:orderHistoryList){
            orderIdSet.add(orderHistory.getOrderId());
        }
        ArknightsOrderData orderData = new ArknightsOrderData(as.getTotalCost(),orderIdSet);

        //?????????????????????????????????
        ArknightsOrderDataHandler orderDataHandler = new ArknightsOrderDataHandler(orderData);
        ArknightsOrderData orderData_New = orderDataHandler.orderHandler(token);
        List<ArknightsOrderDataEach> orderDataEachList_New;
        try {
            orderDataEachList_New = orderData_New.getOrderDataEachList();
            //?????????????????????????????????->??????
            Collections.reverse(orderDataEachList_New);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        for (ArknightsOrderDataEach orderDataEach_New:orderDataEachList_New){
            ArknightsOrderHistory orderHistory_New = new ArknightsOrderHistory(
                    orderDataEach_New.getOrderId(),
                    orderDataEach_New.getPlatform(),
                    orderDataEach_New.getAmount(),
                    orderDataEach_New.getProductName(),
                    orderDataEach_New.getPayTime(),
                    as
            );
            orderHistoryRepo.save(orderHistory_New);
        }
        as.setTotalCost(orderData_New.getTotalCost());
        return true;
    }

    public Boolean saveUserinfo(String token) {
        String uid = as.getArknights_uid();
        String nickName = as.getArknights_nickName();
        if (uid.equals("unknow") && nickName.equals("unknow")) {
            Map<String, Object> userinfo = ArknightsUserinfoHandler.getUserinfo(token);
            if (userinfo == null) {
                return false;
            }
            uid = (String) userinfo.get("uid");
            nickName = (String) userinfo.get("nickName");
            as.setArknights_uid(uid);
            as.setArknights_nickName(nickName);
        }
        return true;
    }
}
