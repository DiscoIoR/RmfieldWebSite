package cn.rmfield.website.repository.arknights;

import cn.rmfield.website.entity.arknights.ArknightsGachaHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArknightsGachaHistoryRepository extends JpaRepository<ArknightsGachaHistory,Integer> {
    List<ArknightsGachaHistory> findByArknightsStatistics_id(Integer id);
}
