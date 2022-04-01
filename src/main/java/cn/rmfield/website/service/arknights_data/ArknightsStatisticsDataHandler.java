package cn.rmfield.website.service.arknights_data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.*;

public class ArknightsStatisticsDataHandler {
    private Integer total;
    private Integer sixCount;
    private Integer fiveCount;
    private Integer fourCount;
    private Integer threeCount;
    private Double sixRate;
    private Double fiveRate;
    private Double fourRate;
    private Double threeRate;
    private Set<Integer> tses;
    private List<ArknightsStatisticsDataEach> asdes;

    public ArknightsStatisticsDataHandler(ArknightsStatisticsData arknightsStatisticsData) {
        this.total = arknightsStatisticsData.getTotal();
        this.sixCount = arknightsStatisticsData.getSixCount();
        this.fiveCount = arknightsStatisticsData.getFiveCount();
        this.fourCount = arknightsStatisticsData.getFourCount();
        this.threeCount = arknightsStatisticsData.getThreeCount();
        this.sixRate = arknightsStatisticsData.getSixRate();
        this.fiveRate = arknightsStatisticsData.getFiveRate();
        this.fourRate = arknightsStatisticsData.getFourRate();
        this.threeRate = arknightsStatisticsData.getThreeRate();
        this.tses = arknightsStatisticsData.getTses();
        this.asdes = arknightsStatisticsData.getArknightsStatisticsDataEaches();
    }

    public String getDataFromRemote(String token, Integer page) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            String url = "https://ak.hypergryph.com/user/api/inquiry/gacha";
            URIBuilder uri = new URIBuilder(url);
            List<NameValuePair> list = new LinkedList<>();
            BasicNameValuePair param1 = new BasicNameValuePair("token", token);
            BasicNameValuePair param2 = new BasicNameValuePair("page", page.toString());
            list.add(param1);
            list.add(param2);
            uri.setParameters(list);
            HttpGet httpGet = new HttpGet(uri.build());
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
            httpGet.addHeader("Accepted-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
            httpGet.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response == null) {
                return null;
            }
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntityPageNum = response.getEntity();
                String result = null;
                try {
                    result = EntityUtils.toString(httpEntityPageNum, "UTF-8");
                    EntityUtils.consume(httpEntityPageNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取并筛选所需数据
    public ArknightsStatisticsData dataInquireAndFiltrate(String token) {
        //总页数
        int totalPageNum = 0;
        String resultPageNum = getDataFromRemote(token, 20);
        int codePageNum = (Integer) JSON.parseObject(resultPageNum).get("code");
        if (codePageNum != 0) {
            return null;
        }
        totalPageNum = (Integer) JSON.parseObject(resultPageNum).getJSONObject("data").getJSONObject("pagination").get("total");
        int pageMax = totalPageNum / 10 + 1;

        //查询每页

        for (int currentPage = 1; currentPage <= pageMax; currentPage++) {
            String result = getDataFromRemote(token, currentPage);
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
                System.out.println();
                System.out.println(ts);
                System.out.println();
                JSONArray jsonChars = new JSONArray();
                if (!tses.contains(ts)) {
                    jsonChars.addAll(jsonListEach.getJSONArray("chars"));
                    List<Map<String, Object>> times = JSON.parseObject(jsonChars.toString(), new TypeReference<>() {
                    });
                    for (Map<String, Object> eachTime : times) {
                        total++;
                        String name = (String) eachTime.get("name");
                        Integer rarity = (Integer) eachTime.get("rarity");
                        Boolean isNew = (Boolean) eachTime.get("isNew");
                        ArknightsStatisticsDataEach asde = new ArknightsStatisticsDataEach(ts,name,rarity,isNew);
                        asdes.add(asde);
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
        sixRate = sixCount / total.doubleValue();
        fiveRate = fiveCount / total.doubleValue();
        fourRate = fourCount / total.doubleValue();
        threeRate = threeCount / total.doubleValue();

        return new ArknightsStatisticsData(
                total,
                sixCount, fiveCount, fourCount, threeCount,
                sixRate, fiveRate, fourRate, threeRate,
                asdes
        );
    }
}
