package cn.rmfield.website.service.arknights;

import java.util.List;
import java.util.Map;

public interface ArknightsStatisticsService {
    Boolean updateData(String token);
    Map<String,Object> getData();
    List<Map<String,Object>> gacha();
    List<Map<String,Object>> diamond();
    List<Map<String,Object>> order();
}
