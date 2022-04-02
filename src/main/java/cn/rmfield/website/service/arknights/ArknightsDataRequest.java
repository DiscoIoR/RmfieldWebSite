package cn.rmfield.website.service.arknights;

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

public class ArknightsDataRequest {
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
}
