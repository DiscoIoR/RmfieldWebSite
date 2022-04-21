package cn.rmfield.website.service.arknights;

import java.util.List;
import java.util.Map;

public interface ArknightsStatisticsPushService {
    Map<String,Object> getGeneral();
    List<Map<String,Object>> gachaDetail();
    List<Map<String,Object>> diamondDetail();
    List<Map<String,Object>> orderDetail();
}
