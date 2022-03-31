package cn.rmfield.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="arknights_statistics")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class ArknightsStatistics implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Integer total;
    private Integer sixCount;
    private Double sixRate;
    private Integer fiveCount;
    private Double fiveRate;
    private Integer fourCount;
    private Double fourRate;
    private Integer threeCount;
    private Double threeRate;
    @OneToOne(
            optional = false,
            fetch = FetchType.LAZY,
            targetEntity = RfUser.class,
            mappedBy = "arknightsStatistics",
            cascade = CascadeType.ALL
    )
    private RfUser rfUser;
    //抽卡历史
    @OneToMany(
            mappedBy = "arknightsStatistics",
            cascade = CascadeType.ALL,
            targetEntity = ArknightsStatisticsHistory.class,
            fetch = FetchType.LAZY
    )
    private List<ArknightsStatisticsHistory> arknightsStatisticsHistory;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    public Double getSixRate() {
        return sixRate;
    }
    public void setSixRate(Double sixRate) {
        this.sixRate = sixRate;
    }
    public Integer getFiveCount() {
        return fiveCount;
    }
    public void setFiveCount(Integer fiveCount) {
        this.fiveCount = fiveCount;
    }
    public Double getFiveRate() {
        return fiveRate;
    }
    public void setFiveRate(Double fiveRate) {
        this.fiveRate = fiveRate;
    }
    public Integer getFourCount() {
        return fourCount;
    }
    public void setFourCount(Integer fourCount) {
        this.fourCount = fourCount;
    }
    public Double getFourRate() {
        return fourRate;
    }
    public void setFourRate(Double fourRate) {
        this.fourRate = fourRate;
    }
    public Integer getThreeCount() {
        return threeCount;
    }
    public void setThreeCount(Integer threeCount) {
        this.threeCount = threeCount;
    }
    public Double getThreeRate() {
        return threeRate;
    }
    public void setThreeRate(Double threeRate) {
        this.threeRate = threeRate;
    }
    public RfUser getRfUser() {
        return rfUser;
    }
    public void setRfUser(RfUser rfUser) {
        this.rfUser = rfUser;
    }
    public List<ArknightsStatisticsHistory> getArknightsStatisticsHistory() {
        return arknightsStatisticsHistory;
    }
    public void setArknightsStatisticsHistory(List<ArknightsStatisticsHistory> arknightsStatisticsHistory) {
        this.arknightsStatisticsHistory = arknightsStatisticsHistory;
    }
}
