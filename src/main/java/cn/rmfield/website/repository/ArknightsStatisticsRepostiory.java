package cn.rmfield.website.repository;

import cn.rmfield.website.entity.ArknightsStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArknightsStatisticsRepostiory extends JpaRepository<ArknightsStatistics,Integer> {
    ArknightsStatistics findByRfUser_id(Integer id);
}
