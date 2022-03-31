package cn.rmfield.website.service;

import cn.rmfield.website.repository.ArknightsStatisticsRepostiory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class ArknightsStatisticsServiceImpl implements ArknightsStatisticsService{
    @Autowired
    ArknightsStatisticsRepostiory akRepository;

    @Override
    public String updateData(Model model, String token) {
        return null;
    }

    @Override
    public String getData(Model model) {
        return null;
    }
}
