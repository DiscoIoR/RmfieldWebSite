package cn.rmfield.website.repository;

import cn.rmfield.website.entity.ArknightsStatisticsHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArknightsStatisticsHistoryRepository extends JpaRepository<ArknightsStatisticsHistory,Integer> {
    List<ArknightsStatisticsHistory> findByArknightsStatistics_id(Integer id);
}
