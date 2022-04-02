package cn.rmfield.website.entity.arknights;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="arknights_diamond_history")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class ArknightsDiamondHistory implements Serializable {
    private static final Long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Integer ts;    //变动时间   timestamp
    private String operation;   //变动项目名称
    private String platform;   //源石变动的平台 Android,iOS
    private Integer changeNum; //源石变动数量
    private Integer currentNum;    //当前剩余源石量
    @ManyToOne(
            cascade = CascadeType.ALL,
            optional = false
    )
    @JoinColumn(
            name = "arknights_statistics_id"
    )
    @JsonIgnore
    private ArknightsStatistics arknightsStatistics;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    public ArknightsStatistics getArknightsStatistics() {
        return arknightsStatistics;
    }
    public void setArknightsStatistics(ArknightsStatistics arknightsStatistics) {
        this.arknightsStatistics = arknightsStatistics;
    }
}