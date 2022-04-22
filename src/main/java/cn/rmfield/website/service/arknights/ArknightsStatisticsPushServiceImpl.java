package cn.rmfield.website.service.arknights;

import cn.rmfield.website.entity.arknights.ArknightsDiamondHistory;
import cn.rmfield.website.entity.arknights.ArknightsGachaHistory;
import cn.rmfield.website.entity.arknights.ArknightsOrderHistory;
import cn.rmfield.website.entity.arknights.ArknightsStatistics;
import cn.rmfield.website.security.LoginUser;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArknightsStatisticsPushServiceImpl implements ArknightsStatisticsPushService {

    private ArknightsStatistics as;

    @Override
    public Map<String, Object> getGeneral() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.as = loginUser.getRfUser().getArknightsStatistics();
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("username", as.getArknights_nickName());
        data.put("uid", as.getArknights_uid());
        data.put("six_num", as.getSixCount());
        data.put("five_num", as.getFiveCount());
        data.put("four_num", as.getFourCount());
        data.put("three_num", as.getThreeCount());
        data.put("pools", getGachaPerPool(as.getArknightsGachaHistories()));
        data.put("dates", getGachaPerMonth(as.getArknightsGachaHistories()));
        data.put("diamond_income", as.getDiamondGrossIncome());
        data.put("diamond_expenses", as.getDiamondGrossExpenses());
        data.put("order_total", as.getTotalCost());
        return data;
    }

    private List<Map<String, Object>> getGachaPerPool(List<ArknightsGachaHistory> gachaHistoryList) {
        List<Map<String, Object>> pools = new ArrayList<>();
        String currentPoolName;
        int currentPoolNum;
        try {
            currentPoolName = gachaHistoryList.get(0).getPool();
            currentPoolNum = 0;
        } catch (Exception e) {
            e.printStackTrace();
            return pools;
        }
        for (ArknightsGachaHistory gachaHistory : gachaHistoryList) {
            if (currentPoolName.equals(gachaHistory.getPool())) {
                currentPoolNum++;
            } else {
                Map<String, Object> currentPool = new LinkedHashMap<>();
                currentPool.put("pool", currentPoolName);
                currentPool.put("gacha_num", currentPoolNum);
                pools.add(currentPool);
                currentPoolName = gachaHistory.getPool();
                currentPoolNum = 1;
            }
        }
        Map<String, Object> currentPool = new LinkedHashMap<>();
        currentPool.put("pool", currentPoolName);
        currentPool.put("gacha_num", currentPoolNum);
        pools.add(currentPool);
        return pools;
    }

    private List<Map<String, Object>> getGachaPerMonth(List<ArknightsGachaHistory> gachaHistoryList) {

        List<Map<String, Object>> dates = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        long currentTimestamp;
        long timestamp;
        String currentDateYM;
        String dateYM;
        int currentDateNum;
        try {
            //获取第一条记录的 year-month
            currentTimestamp = gachaHistoryList.get(0).getTs().longValue();
            cal.setTimeInMillis(currentTimestamp * 1000);
            currentDateYM = Integer.toString(cal.get(Calendar.YEAR)) + "." + (cal.get(Calendar.MONTH) + 1);
            currentDateNum = 0;
        } catch (Exception e) {
            e.printStackTrace();
            return dates;
        }
        for (ArknightsGachaHistory gachaHistory : gachaHistoryList) {
            //获取每一条记录的 year-month
            timestamp = gachaHistory.getTs().longValue();
            cal.setTimeInMillis(timestamp * 1000);
            dateYM = Integer.toString(cal.get(Calendar.YEAR)) + "." + (cal.get(Calendar.MONTH) + 1);

            if (currentDateYM.equals(dateYM)) {
                currentDateNum++;
            } else {
                Map<String, Object> currentDate = new LinkedHashMap<>();
                currentDate.put("date", currentDateYM);
                currentDate.put("gacha_num", currentDateNum);
                dates.add(currentDate);
                //更新 year-month
                currentTimestamp = gachaHistory.getTs().longValue();
                cal.setTimeInMillis(currentTimestamp * 1000);
                currentDateYM = Integer.toString(cal.get(Calendar.YEAR)) + "." + (cal.get(Calendar.MONTH) + 1);
                currentDateNum = 1;
            }
        }
        Map<String, Object> currentDate = new LinkedHashMap<>();
        currentDate.put("date", currentDateYM);
        currentDate.put("gacha_num", currentDateNum);
        dates.add(currentDate);

        return dates;
    }

    @Override
    public List<Map<String, Object>> gachaDetail() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.as = loginUser.getRfUser().getArknightsStatistics();
        List<ArknightsGachaHistory> gachaHistoryList = as.getArknightsGachaHistories();
        //调整取出的数据顺序（新->旧）
        Collections.reverse(gachaHistoryList);

        List<Map<String, Object>> gachaDetailList = new ArrayList<>();
        for (ArknightsGachaHistory gachaHistory : gachaHistoryList) {
            Map<String, Object> gachaDetail = new HashMap<>();
            gachaDetail.put("ts", gachaHistory.getTs());
            gachaDetail.put("pool", gachaHistory.getPool());
            gachaDetail.put("name", gachaHistory.getName());
            gachaDetail.put("rarity", gachaHistory.getRarity());
            gachaDetail.put("isNew", gachaHistory.getIsNew());
            gachaDetailList.add(gachaDetail);
        }
        return gachaDetailList;
    }

    @Override
    public List<Map<String, Object>> diamondDetail() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.as = loginUser.getRfUser().getArknightsStatistics();
        List<ArknightsDiamondHistory> diamondHistoryList = as.getArknightsDiamondHistories();
        //调整取出的数据顺序（新->旧）
        Collections.reverse(diamondHistoryList);

        List<Map<String, Object>> diamondDetailList = new ArrayList<>();
        for (ArknightsDiamondHistory diamondHistory : diamondHistoryList) {
            Map<String, Object> diamondDetail = new HashMap<>();
            diamondDetail.put("ts", diamondHistory.getTs());
            diamondDetail.put("operation", diamondHistory.getOperation());
            diamondDetail.put("platform", diamondHistory.getPlatform());
            diamondDetail.put("changeNum", diamondHistory.getChangeNum());
            diamondDetail.put("currentNum", diamondHistory.getCurrentNum());
            diamondDetailList.add(diamondDetail);
        }
        return diamondDetailList;
    }

    @Override
    public List<Map<String, Object>> orderDetail() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.as = loginUser.getRfUser().getArknightsStatistics();
        List<ArknightsOrderHistory> orderHistoryList = as.getArknightsOrderHistories();
        //调整取出的数据顺序（新->旧）
        Collections.reverse(orderHistoryList);

        List<Map<String, Object>> orderDetailList = new ArrayList<>();
        for (ArknightsOrderHistory orderHistory : orderHistoryList) {
            Map<String, Object> orderDetail = new HashMap<>();
            orderDetail.put("orderId", orderHistory.getOrderId());
            orderDetail.put("platform", orderHistory.getPlatform());
            orderDetail.put("amount", orderHistory.getAmount());
            orderDetail.put("productName", orderHistory.getProductName());
            orderDetail.put("payTime", orderHistory.getPayTime());
            orderDetailList.add(orderDetail);
        }
        return orderDetailList;
    }

}
