package cn.rmfield.website.service.arknights;

import cn.rmfield.website.entity.arknights.ArknightsStatistics;
import cn.rmfield.website.entity.arknights.ArknightsGachaHistory;
import cn.rmfield.website.entity.RfUser;
import cn.rmfield.website.repository.arknights.ArknightsGachaHistoryRepository;
import cn.rmfield.website.repository.arknights.ArknightsStatisticsRepostiory;
import cn.rmfield.website.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArknightsStatisticsServiceImpl implements ArknightsStatisticsService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArknightsStatisticsRepostiory asRepo;
    @Autowired
    private ArknightsGachaHistoryRepository gachaHistoryRepo;

    @Override
    public Boolean updateData(String token) {
        //从数据库提取之前的抽卡数据
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        RfUser rfUser = userRepository.findByUsername(name);
        ArknightsStatistics as = asRepo.findByRfUser_id(rfUser.getId());
        List<ArknightsGachaHistory>  gachaHistoryList = gachaHistoryRepo.findByArknightsStatistics_id(as.getId());
        Set<Integer> tses = new HashSet<>();
        for(ArknightsGachaHistory gachaHistory:gachaHistoryList){
            tses.add(gachaHistory.getTs());
        }
        ArknightsStatisticsGachaData gachaData = new ArknightsStatisticsGachaData(
                as.getTotalCount(),
                as.getSixCount(),as.getFiveCount(), as.getFourCount(),as.getThreeCount(),
                as.getSixRate(),as.getFiveRate(), as.getFourRate(),as.getThreeRate(),
                tses
        );

        //获取新的抽卡数据并保存
        ArknightsStatisticsGachaDataHandler gachaDataHandler = new ArknightsStatisticsGachaDataHandler(gachaData);
        ArknightsStatisticsGachaData gachaData_New = gachaDataHandler.gachaHandler(token);
        List<ArknightsStatisticsGachaDataEach> gachaDataEachList_New;
        try {
            gachaDataEachList_New = gachaData_New.getArknightsStatisticsDataEaches();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        for(ArknightsStatisticsGachaDataEach gachaDataEach_New:gachaDataEachList_New){
            ArknightsGachaHistory gachaHistory_New = new ArknightsGachaHistory();
            gachaHistory_New.setTs(gachaDataEach_New.getTs());
            System.out.println();
            System.out.println(gachaDataEach_New.getTs());
            System.out.println();
            gachaHistory_New.setName(gachaDataEach_New.getName());
            gachaHistory_New.setRarity(gachaDataEach_New.getRarity());
            gachaHistory_New.setIsNew(gachaDataEach_New.getIsNew());
            gachaHistory_New.setArknightsStatistics(as);
            gachaHistoryRepo.save(gachaHistory_New);
        }
        as.setSixCount(gachaData_New.getSixCount());
        as.setFiveCount(gachaData_New.getFiveCount());
        as.setFourCount(gachaData_New.getFourCount());
        as.setThreeCount(gachaData_New.getThreeCount());
        as.setSixRate(gachaData_New.getSixRate());
        as.setFiveRate(gachaData_New.getFiveRate());
        as.setFourRate(gachaData_New.getFourRate());
        as.setThreeRate(gachaData_New.getThreeRate());
        as.setTotalCount(gachaData_New.getTotalCount());
        asRepo.save(as);
        return true;
    }

    @Override
    public Map<String,Object> getData() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        RfUser rfUser = userRepository.findByUsername(name);
        ArknightsStatistics as = asRepo.findByRfUser_id(rfUser.getId());
        Map<String,Object> data = new HashMap<>();
        data.put("sixCount",as.getSixCount());
        data.put("fiveCount",as.getFiveCount());
        data.put("fourCount",as.getFourCount());
        data.put("threeCount",as.getThreeCount());
        data.put("sixRate",as.getSixRate()*100);
        data.put("fiveRate",as.getFiveRate()*100);
        data.put("fourRate",as.getFourRate()*100);
        data.put("threeRate",as.getThreeRate()*100);
        data.put("Total",as.getTotalCount());
        return data;
    }
}
