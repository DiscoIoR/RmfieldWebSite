package cn.rmfield.website.service.arknights;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.*;

public class ArknightsStatisticsGachaDataHandler {
    private Integer totalCount;
    private Integer sixCount;
    private Integer fiveCount;
    private Integer fourCount;
    private Integer threeCount;
    private Double sixRate;
    private Double fiveRate;
    private Double fourRate;
    private Double threeRate;
    private Set<Integer> tses;
    private List<ArknightsStatisticsGachaDataEach> gachaDataEachList;

    public ArknightsStatisticsGachaDataHandler(ArknightsStatisticsGachaData arknightsStatisticsGachaData) {
        this.totalCount = arknightsStatisticsGachaData.getTotalCount();
        this.sixCount = arknightsStatisticsGachaData.getSixCount();
        this.fiveCount = arknightsStatisticsGachaData.getFiveCount();
        this.fourCount = arknightsStatisticsGachaData.getFourCount();
        this.threeCount = arknightsStatisticsGachaData.getThreeCount();
        this.sixRate = arknightsStatisticsGachaData.getSixRate();
        this.fiveRate = arknightsStatisticsGachaData.getFiveRate();
        this.fourRate = arknightsStatisticsGachaData.getFourRate();
        this.threeRate = arknightsStatisticsGachaData.getThreeRate();
        this.tses = arknightsStatisticsGachaData.getTses();
        this.gachaDataEachList = arknightsStatisticsGachaData.getArknightsStatisticsDataEaches();
    }

    //获取并筛选所需数据
    public ArknightsStatisticsGachaData gachaHandler(String token) {
        //请求地址
        String url = "https://ak.hypergryph.com/user/api/inquiry/gacha";

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
                String pool = (String) jsonListEach.get("pool");
                JSONArray jsonChars = new JSONArray();
                if (!tses.contains(ts)) {
                    jsonChars.addAll(jsonListEach.getJSONArray("chars"));
                    List<Map<String, Object>> times = JSON.parseObject(jsonChars.toString(), new TypeReference<>() {
                    });
                    for (Map<String, Object> eachTime : times) {
                        totalCount++;
                        String name = (String) eachTime.get("name");
                        Integer rarity = (Integer) eachTime.get("rarity");
                        Boolean isNew = (Boolean) eachTime.get("isNew");
                        //打包单抽数据
                        ArknightsStatisticsGachaDataEach asde = new ArknightsStatisticsGachaDataEach(ts,pool,name,rarity,isNew);
                        gachaDataEachList.add(asde);
                        switch (rarity) {
                            case 5 -> sixCount++;
                            case 4 -> fiveCount++;
                            case 3 -> fourCount++;
                            case 2 -> threeCount++;
                        }
                    }
                }
            }
        }

        //抽卡数据分析
        sixRate = sixCount / totalCount.doubleValue();
        fiveRate = fiveCount / totalCount.doubleValue();
        fourRate = fourCount / totalCount.doubleValue();
        threeRate = threeCount / totalCount.doubleValue();

        return new ArknightsStatisticsGachaData(
                totalCount,
                sixCount, fiveCount, fourCount, threeCount,
                sixRate, fiveRate, fourRate, threeRate,
                gachaDataEachList
        );
    }
}
