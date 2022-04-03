package cn.rmfield.website.service.arknights;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.Map;

public class ArknightsUserinfoHandler {
    public static Map<String,Object> getUserinfo(String token){
        String url = "https://as.hypergryph.com/u8/user/info/v1/basic";
        String result = ArknightsDataRequest.postDataFromRemote(url,token);
        int status = (Integer) JSON.parseObject(result).get("status");
        if(status!=0){
            return null;
        }
        JSONObject data = JSON.parseObject(result).getJSONObject("data");
        return JSON.parseObject(data.toString(),new TypeReference<>(){});
    }
}
