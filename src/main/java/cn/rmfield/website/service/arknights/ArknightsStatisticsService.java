package cn.rmfield.website.service.arknights;

import java.util.Map;

public interface ArknightsStatisticsService {
    Boolean updateData(String token);
    Map<String,Object> getData();
}
