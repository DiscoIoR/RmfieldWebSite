package cn.rmfield.website.service.arknights;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.*;

public class ArknightsDataRequest {
    //发送get请求，查询寻访和源石记录
    public static String getDataFromRemote(String url,String token, Integer page) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
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
                HttpEntity httpEntity = response.getEntity();
                String result = null;
                try {
                    result = EntityUtils.toString(httpEntity, "UTF-8");
                    EntityUtils.consume(httpEntity);
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

    //发送post请求，查询账户信息和充值记录
    public static String postDataFromRemote(String url,String token){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        post.setHeader("content-type", "application/json;charset=UTF-8");
        post.setHeader("Accept", "*/*");
        post.setHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.74 Safari/537.36 Edg/99.0.1150.55");
        Map<String,Object> params_ori = new HashMap<>();
        params_ori.put("appId", 1);
        params_ori.put("channelMasterId", 1);
        Map<String,Object> channelToken = new HashMap<>();
        channelToken.put("token",token);
        params_ori.put("channelToken",channelToken);
        JSONObject params = new JSONObject(params_ori);
        String str = params.toJSONString();
        StringEntity stringEntity = new StringEntity(str, "utf-8");
        post.setEntity(stringEntity);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = response.getEntity();
                String result = null;
                try {
                    result = EntityUtils.toString(httpEntity, "UTF-8");
                    EntityUtils.consume(httpEntity);
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
}
