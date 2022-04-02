package cn.rmfield.website.repository.arknights;

import cn.rmfield.website.entity.arknights.ArknightsDiamondHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArknightsDiamondHistoryRepository extends JpaRepository<ArknightsDiamondHistory,Integer> {
    List<ArknightsDiamondHistory> findByArknightsStatistics_id(Integer id);
}
