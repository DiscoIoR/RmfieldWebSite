package cn.rmfield.website.service.arknights_data;

import java.util.*;

class  ArknightsStatisticsDataEach{
    private Integer ts;
    private String name;
    private Integer rarity;
    private Boolean isNew;

    public ArknightsStatisticsDataEach(Integer ts, String name, Integer rarity, Boolean isNew) {
        this.ts = ts;
        this.name = name;
        this.rarity = rarity;
        this.isNew = isNew;
    }

    public Integer getTs() {
        return ts;
    }
    public void setTs(Integer ts) {
        this.ts = ts;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getRarity() {
        return rarity;
    }
    public void setRarity(Integer rarity) {
        this.rarity = rarity;
    }
    public Boolean getIsNew() {
        return isNew;
    }
    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }
}

public class ArknightsStatisticsData {
    private Integer total;
    private Integer sixCount;
    private Integer fiveCount;
    private Integer fourCount;
    private Integer threeCount;
    private Double sixRate;
    private Double fiveRate;
    private Double fourRate;
    private Double threeRate;
    private Set<Integer> tses;
    private List<ArknightsStatisticsDataEach> arknightsStatisticsDataEaches;

    public ArknightsStatisticsData(
            Integer total,
            Integer sixCount, Integer fiveCount, Integer fourCount, Integer threeCount,
            Double sixRate, Double fiveRate, Double fourRate, Double threeRate,
            Set<Integer> tses) {
        this.total = total;
        this.sixCount = sixCount;
        this.fiveCount = fiveCount;
        this.fourCount = fourCount;
        this.threeCount = threeCount;
        this.sixRate = sixRate;
        this.fiveRate = fiveRate;
        this.fourRate = fourRate;
        this.threeRate = threeRate;
        this.tses = tses;
        this.arknightsStatisticsDataEaches = new ArrayList<>();
    }

    public ArknightsStatisticsData(
            Integer total,
            Integer sixCount, Integer fiveCount, Integer fourCount, Integer threeCount,
            Double sixRate, Double fiveRate, Double fourRate, Double threeRate,
            List<ArknightsStatisticsDataEach> arknightsStatisticsDataEaches) {
        this.total = total;
        this.sixCount = sixCount;
        this.fiveCount = fiveCount;
        this.fourCount = fourCount;
        this.threeCount = threeCount;
        this.sixRate = sixRate;
        this.fiveRate = fiveRate;
        this.fourRate = fourRate;
        this.threeRate = threeRate;
        this.tses = new HashSet<>();
        this.arknightsStatisticsDataEaches = arknightsStatisticsDataEaches;
    }



    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    public Integer getSixCount() {
        return sixCount;
    }
    public void setSixCount(Integer sixCount) {
        this.sixCount = sixCount;
    }
    public Integer getFiveCount() {
        return fiveCount;
    }
    public void setFiveCount(Integer fiveCount) {
        this.fiveCount = fiveCount;
    }
    public Integer getFourCount() {
        return fourCount;
    }
    public void setFourCount(Integer fourCount) {
        this.fourCount = fourCount;
    }
    public Integer getThreeCount() {
        return threeCount;
    }
    public void setThreeCount(Integer threeCount) {
        this.threeCount = threeCount;
    }
    public Double getSixRate() {
        return sixRate;
    }
    public void setSixRate(Double sixRate) {
        this.sixRate = sixRate;
    }
    public Double getFiveRate() {
        return fiveRate;
    }
    public void setFiveRate(Double fiveRate) {
        this.fiveRate = fiveRate;
    }
    public Double getFourRate() {
        return fourRate;
    }
    public void setFourRate(Double fourRate) {
        this.fourRate = fourRate;
    }
    public Double getThreeRate() {
        return threeRate;
    }
    public void setThreeRate(Double threeRate) {
        this.threeRate = threeRate;
    }
    public Set<Integer> getTses() {
        return tses;
    }
    public void setTses(Set<Integer> tses) {
        this.tses = tses;
    }
    public List<ArknightsStatisticsDataEach> getArknightsStatisticsDataEaches() {
        return arknightsStatisticsDataEaches;
    }
    public void setArknightsStatisticsDataEaches(List<ArknightsStatisticsDataEach> arknightsStatisticsDataEaches) {
        this.arknightsStatisticsDataEaches = arknightsStatisticsDataEaches;
    }
}
