package cn.rmfield.website.service;

import org.springframework.ui.Model;

public interface ArknightsStatisticsService {
    String updateData(Model model,String token);
    String getData(Model model);
}
