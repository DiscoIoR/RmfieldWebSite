package cn.rmfield.website.service.arknights;

import java.util.*;

class ArknightsGachaDataEach {
    private Integer ts;
    private String pool;
    private String name;
    private Integer rarity;
    private Boolean isNew;

    public ArknightsGachaDataEach(Integer ts, String pool, String name, Integer rarity, Boolean isNew) {
        this.ts = ts;
        this.pool = pool;
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
    public String getPool() {
        return pool;
    }
    public void setPool(String pool) {
        this.pool = pool;
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

public class ArknightsGachaData {
    private Integer totalCount;
    private Integer sixCount;
    private Integer fiveCount;
    private Integer fourCount;
    private Integer threeCount;
    private Double sixRate;
    private Double fiveRate;
    private Double fourRate;
    private Double threeRate;
    private Set<Integer> tses;
    private List<ArknightsGachaDataEach> arknightsGachaDataEaches;

    public ArknightsGachaData(
            Integer totalCount,
            Integer sixCount, Integer fiveCount, Integer fourCount, Integer threeCount,
            Double sixRate, Double fiveRate, Double fourRate, Double threeRate,
            Set<Integer> tses) {
        this.totalCount = totalCount;
        this.sixCount = sixCount;
        this.fiveCount = fiveCount;
        this.fourCount = fourCount;
        this.threeCount = threeCount;
        this.sixRate = sixRate;
        this.fiveRate = fiveRate;
        this.fourRate = fourRate;
        this.threeRate = threeRate;
        this.tses = tses;
        this.arknightsGachaDataEaches = new ArrayList<>();
    }

    public ArknightsGachaData(
            Integer totalCount,
            Integer sixCount, Integer fiveCount, Integer fourCount, Integer threeCount,
            Double sixRate, Double fiveRate, Double fourRate, Double threeRate,
            List<ArknightsGachaDataEach> arknightsGachaDataEaches) {
        this.totalCount = totalCount;
        this.sixCount = sixCount;
        this.fiveCount = fiveCount;
        this.fourCount = fourCount;
        this.threeCount = threeCount;
        this.sixRate = sixRate;
        this.fiveRate = fiveRate;
        this.fourRate = fourRate;
        this.threeRate = threeRate;
        this.tses = new HashSet<>();
        this.arknightsGachaDataEaches = arknightsGachaDataEaches;
    }

    public Integer getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
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
    public List<ArknightsGachaDataEach> getArknightsStatisticsDataEaches() {
        return arknightsGachaDataEaches;
    }
    public void setArknightsStatisticsDataEaches(List<ArknightsGachaDataEach> arknightsGachaDataEaches) {
        this.arknightsGachaDataEaches = arknightsGachaDataEaches;
    }
}
