package cn.rmfield.website.service.arknights;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.*;

class ArknightsDiamondDataHanler {
    private Integer diamondGrossIncome;
    private Integer diamondGrossExpenses;
    private Set<Integer> tses;
    private List<ArknightsDiamondDataEach> diamondDataEachList;

    public ArknightsDiamondDataHanler(ArknightsDiamondData arknightsDiamondData) {
        this.diamondGrossIncome = arknightsDiamondData.getDiamondGrossIncome();
        this.diamondGrossExpenses = arknightsDiamondData.getDiamondGrossExpenses();
        this.tses = arknightsDiamondData.getTses();
        this.diamondDataEachList = arknightsDiamondData.getDiamondDataEachList();
    }

    public ArknightsDiamondData diamondHandler(String token){
        //请求地址
        String url = "https://ak.hypergryph.com/user/api/inquiry/diamond";

        //总页数
        int totalPageNum = 0;
        String resultPageNum = ArknightsDataRequest.getDataFromRemote(url,token,20);
        int codePageNum = (Integer) JSON.parseObject(resultPageNum).get("code");
        if (codePageNum != 0) {
            return null;
        }
        totalPageNum = (Integer) JSON.parseObject(resultPageNum).getJSONObject("data").getJSONObject("pagination").get("total");
        int pageMax = totalPageNum / 10 + 1;

        //查询每页
        for (int currentPage = 1; currentPage <= pageMax; currentPage++) {
            String result = ArknightsDataRequest.getDataFromRemote(url,token,currentPage);
            int code = (Integer) JSON.parseObject(result).get("code");
            if (code != 0) {
                return null;
            }

            //Json数据解析，提取每一抽数据
            JSONArray jsonArrayList = JSON.parseObject(result).getJSONObject("data").getJSONArray("list");
            for (Object obj : jsonArrayList) {
                JSONObject jsonListEach = (JSONObject) obj;
                //筛选之前查询过的数据后再进一步处理
                Integer ts = (Integer) jsonListEach.get("ts");
                if (!tses.contains(ts)) {
                    String operation = (String) jsonListEach.get("operation");
                    JSONArray changes = jsonListEach.getJSONArray("changes");
                    Map<String,Object> changeDetail = JSON.parseObject(changes.toString(),new TypeReference<>(){});
                    String platform = (String) changeDetail.get("type");
                    Integer currentNum = (Integer) changeDetail.get("after");
                    Integer changeNum = currentNum - (Integer) changeDetail.get("before");
                    //打包单次变动数据
                    ArknightsDiamondDataEach diamondDataEach = new ArknightsDiamondDataEach(ts,operation,platform,changeNum,currentNum);
                    diamondDataEachList.add(diamondDataEach);
                    if(changeNum>0){
                        diamondGrossIncome+=changeNum;
                    }else if (changeNum<0){
                        diamondGrossExpenses+=changeNum;
                    }
                }
            }
        }
        return new ArknightsDiamondData(diamondGrossIncome,diamondGrossExpenses,diamondDataEachList);
    }
}


