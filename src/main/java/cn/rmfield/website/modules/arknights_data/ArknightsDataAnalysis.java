package cn.rmfield.website.modules.arknights_data;

import cn.rmfield.website.entity.ArknightsStatistics;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ArknightsDataAnalysis {
    private Integer total;
    private Integer sixCount;
    private Double sixRate;
    private Integer fiveCount;
    private Double fiveRate;
    private Integer fourCount;
    private Double fourRate;
    private Integer threeCount;
    private Double threeRate;

    public ArknightsDataAnalysis(ArknightsStatistics arknightsStatistics) {
        this.total = arknightsStatistics.getTotal();
        this.sixCount = arknightsStatistics.getSixCount();
        this.fiveCount = arknightsStatistics.getFiveCount();
        this.fourCount = arknightsStatistics.getFourCount();
        this.threeCount = arknightsStatistics.getThreeCount();
        this.sixRate = arknightsStatistics.getSixRate();
        this.fiveRate = arknightsStatistics.getFiveRate();
        this.fourRate = arknightsStatistics.getFourRate();
        this.threeRate = arknightsStatistics.getThreeRate();
    }

    public CloseableHttpResponse getDataFromRemote(String token, Integer page) {
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
            return httpClient.execute(httpGet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void dataAnalysis(String token) {
        //总页数
        int totalPageNum = 0;
        CloseableHttpResponse responsePageNum = getDataFromRemote(token, 1);
        if (responsePageNum == null) {
            return;
        }
        if (responsePageNum.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity httpEntityPageNum = responsePageNum.getEntity();
            String resultPageNum = null;
            try {
                resultPageNum = EntityUtils.toString(httpEntityPageNum, "UTF-8");
                EntityUtils.consume(httpEntityPageNum);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int code = (Integer) JSON.parseObject(resultPageNum).get("code");
            if (code != 0) {
                return;
            }
            totalPageNum = (Integer) JSON.parseObject(resultPageNum).getJSONObject("data").getJSONObject("pagination").get("total");
        }
        int pageMax = totalPageNum / 10 + 1;

        //查询每页
        JSONArray jsonChars = new JSONArray();
        for (int currentPage = 1; currentPage <= pageMax; currentPage++) {
            CloseableHttpResponse response = getDataFromRemote(token, currentPage);
            if (response == null) {
                return;
            }
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = response.getEntity();
                String result = null;
                try {
                    result = EntityUtils.toString(httpEntity, "UTF-8");
                    EntityUtils.consume(httpEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int code = (Integer) JSON.parseObject(result).get("code");
                if (code != 0) {
                    return;
                }
                //Json数据解析，提取一抽数据
                JSONArray jsonArrayList = JSON.parseObject(result).getJSONObject("data").getJSONArray("list");
                for (Object obj : jsonArrayList) {
                    JSONObject jsonListEach = (JSONObject) obj;
                    jsonChars.addAll(jsonListEach.getJSONArray("chars"));
                }
            }
        }

        //抽卡数据分析
        List<Map<String, Object>> times = JSON.parseObject(jsonChars.toString(), new TypeReference<>() {
        });
        total = times.size();
        for (Map<String, Object> eachTime : times) {
            Integer rarity = (Integer) eachTime.get("rarity");
            switch (rarity) {
                case 6 -> sixCount++;
                case 5 -> fiveCount++;
                case 4 -> fourCount++;
                case 3 -> threeCount++;
            }
        }
        sixRate = sixCount / total.doubleValue();
        fiveRate = fiveCount / total.doubleValue();
        fourRate = fourCount / total.doubleValue();
        threeRate = threeCount / total.doubleValue();
    }


}
