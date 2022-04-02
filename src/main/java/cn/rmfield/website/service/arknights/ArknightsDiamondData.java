package cn.rmfield.website.service.arknights;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class ArknightsDiamondDataEach {
    private Integer ts;
    private String operation;
    private String platform;
    private Integer changeNum;
    private Integer currentNum;

    public ArknightsDiamondDataEach(Integer ts, String operation, String platform, Integer changeNum, Integer currentNum) {
        this.ts = ts;
        this.operation = operation;
        this.platform = platform;
        this.changeNum = changeNum;
        this.currentNum = currentNum;
    }

    public Integer getTs() {
        return ts;
    }
    public void setTs(Integer ts) {
        this.ts = ts;
    }
    public String getOperation() {
        return operation;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }
    public String getPlatform() {
        return platform;
    }
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    public Integer getChangeNum() {
        return changeNum;
    }
    public void setChangeNum(Integer changeNum) {
        this.changeNum = changeNum;
    }
    public Integer getCurrentNum() {
        return currentNum;
    }
    public void setCurrentNum(Integer currentNum) {
        this.currentNum = currentNum;
    }
}

public class ArknightsDiamondData {
    private Integer diamondGrossIncome;
    private Integer diamondGrossExpenses;
    private Set<Integer> tses;
    private List<ArknightsDiamondDataEach> diamondDataEachList;

    public ArknightsDiamondData(Integer diamondGrossIncome,
                                Integer diamondGrossExpenses,
                                Set<Integer> tses) {
        this.diamondGrossIncome = diamondGrossIncome;
        this.diamondGrossExpenses = diamondGrossExpenses;
        this.tses = tses;
        this.diamondDataEachList = new ArrayList<>();
    }

    public ArknightsDiamondData(Integer diamondGrossIncome,
                                Integer diamondGrossExpenses,
                                List<ArknightsDiamondDataEach> diamondDataEachList) {
        this.diamondGrossIncome = diamondGrossIncome;
        this.diamondGrossExpenses = diamondGrossExpenses;
        this.tses = new HashSet<>();
        this.diamondDataEachList = diamondDataEachList;
    }

    public Integer getDiamondGrossIncome() {
        return diamondGrossIncome;
    }
    public void setDiamondGrossIncome(Integer diamondGrossIncome) {
        this.diamondGrossIncome = diamondGrossIncome;
    }
    public Integer getDiamondGrossExpenses() {
        return diamondGrossExpenses;
    }
    public void setDiamondGrossExpenses(Integer diamondGrossExpenses) {
        this.diamondGrossExpenses = diamondGrossExpenses;
    }
    public Set<Integer> getTses() {
        return tses;
    }
    public void setTses(Set<Integer> tses) {
        this.tses = tses;
    }
    public List<ArknightsDiamondDataEach> getDiamondDataEachList() {
        return diamondDataEachList;
    }
    public void setDiamondDataEachList(List<ArknightsDiamondDataEach> diamondDataEachList) {
        this.diamondDataEachList = diamondDataEachList;
    }
}
