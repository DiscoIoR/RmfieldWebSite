package cn.rmfield.website.service.arknights_data;

import org.springframework.ui.Model;

import java.util.Map;

public interface ArknightsStatisticsService {
    void updateData(String token);
    Map<String,Object> getData();
}
