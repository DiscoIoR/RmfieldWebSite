package cn.rmfield.website.service.arknights_data;

import cn.rmfield.website.entity.ArknightsStatistics;
import cn.rmfield.website.entity.ArknightsStatisticsHistory;
import cn.rmfield.website.entity.RfUser;
import cn.rmfield.website.repository.ArknightsStatisticsHistoryRepository;
import cn.rmfield.website.repository.ArknightsStatisticsRepostiory;
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
    private ArknightsStatisticsHistoryRepository ashRepo;

    @Override
    public Boolean updateData(String token) {
        //从数据库提取之前的抽卡数据
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        RfUser rfUser = userRepository.findByUsername(name);
        ArknightsStatistics as = asRepo.findByRfUser_id(rfUser.getId());
        List<ArknightsStatisticsHistory>  ashes = ashRepo.findByArknightsStatistics_id(as.getId());
        Set<Integer> tses = new HashSet<>();
        for(ArknightsStatisticsHistory ash:ashes){
            tses.add(ash.getTs());
        }
        ArknightsStatisticsData asd = new ArknightsStatisticsData(
                as.getTotal(),
                as.getSixCount(),as.getFiveCount(),as.getFourCount(),as.getThreeCount(),
                as.getSixRate(),as.getFiveRate(),as.getFourRate(),as.getThreeRate(),
                tses
        );

        //获取新的抽卡数据并保存
        ArknightsStatisticsDataHandler asdh = new ArknightsStatisticsDataHandler(asd);
        ArknightsStatisticsData asdNew = asdh.dataInquireAndFiltrate(token);
        List<ArknightsStatisticsDataEach> asdesNew = null;
        try {
            asdesNew = asdNew.getArknightsStatisticsDataEaches();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        for(ArknightsStatisticsDataEach asdeNew:asdesNew){
            ArknightsStatisticsHistory ashNew = new ArknightsStatisticsHistory();
            ashNew.setTs(asdeNew.getTs());
            System.out.println();
            System.out.println(asdeNew.getTs());
            System.out.println();
            ashNew.setName(asdeNew.getName());
            ashNew.setRarity(asdeNew.getRarity());
            ashNew.setIsNew(asdeNew.getIsNew());
            ashNew.setArknightsStatistics(as);
            ashRepo.save(ashNew);
        }
        as.setSixCount(asdNew.getSixCount());
        as.setFiveCount(asdNew.getFiveCount());
        as.setFourCount(asdNew.getFourCount());
        as.setThreeCount(asdNew.getThreeCount());
        as.setSixRate(asdNew.getSixRate());
        as.setFiveRate(asdNew.getFiveRate());
        as.setFourRate(asdNew.getFourRate());
        as.setThreeRate(asdNew.getThreeRate());
        as.setTotal(asdNew.getTotal());
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
        data.put("Total",as.getTotal());
        return data;
    }
}
