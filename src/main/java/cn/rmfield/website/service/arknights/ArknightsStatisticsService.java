package cn.rmfield.website.service.arknights;

import java.util.List;
import java.util.Map;

public interface ArknightsStatisticsService {
    Boolean updateData(String token);
    Map<String,Object> getData();
    List<Map<String,Object>> gachaDetail();
    List<Map<String,Object>> diamondDetail();
    List<Map<String,Object>> orderDetail();
}
