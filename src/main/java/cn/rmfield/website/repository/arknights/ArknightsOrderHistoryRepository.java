package cn.rmfield.website.repository.arknights;

import cn.rmfield.website.entity.arknights.ArknightsOrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArknightsOrderHistoryRepository extends JpaRepository<ArknightsOrderHistory,Integer> {
    List<ArknightsOrderHistory> findByArknightsStatistics_id(Integer id);
}
