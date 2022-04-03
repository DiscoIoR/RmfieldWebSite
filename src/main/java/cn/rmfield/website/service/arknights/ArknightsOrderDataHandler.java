package cn.rmfield.website.service.arknights;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ArknightsOrderDataHandler {
    private Integer totalCost;
    private Set<String> orderIdSet;
    private List<ArknightsOrderDataEach> orderDataEachList;

    public ArknightsOrderDataHandler(ArknightsOrderData arknightsOrderData) {
        this.totalCost = arknightsOrderData.getTotalCost();
        this.orderIdSet = arknightsOrderData.getOrderIdSet();
        this.orderDataEachList = arknightsOrderData.getOrderDataEachList();
    }

    public ArknightsOrderData orderHandler(String token){
        String url = "https://as.hypergryph.com/u8/pay/v1/recent";
        String result = ArknightsDataRequest.postDataFromRemote(url,token);
        if(result == null){
            return null;
        }
        int status = (Integer) JSON.parseObject(result).get("status");
        if(status!=0){
            return null;
        }
        JSONArray data = JSON.parseObject(result).getJSONArray("data");
        List<Map<String,Object>> orderList = JSON.parseObject(data.toString(),new TypeReference<>(){});
        for(Map<String,Object> orderEach:orderList){
            String orderId = (String) orderEach.get("orderId");
            if(!orderIdSet.contains(orderId)){
                Integer platformCode = (Integer) orderEach.get("platform");
                String platform = null;
                if(platformCode==0){
                    platform = "iOS";
                }else if (platformCode == 1){
                    platform = "Android";
                }
                Integer amount = (Integer) orderEach.get("amount");
                String productName = (String) orderEach.get("productName");
                Integer payTime = (Integer) orderEach.get("payTime");
                ArknightsOrderDataEach orderDataEach = new ArknightsOrderDataEach(orderId,platform,amount,productName,payTime);
                orderDataEachList.add(orderDataEach);
                totalCost+=amount;
            }
        }
        return new ArknightsOrderData(totalCost,orderDataEachList);
    }
}
