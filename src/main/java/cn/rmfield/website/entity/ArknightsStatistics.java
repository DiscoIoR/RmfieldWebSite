package cn.rmfield.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="arknights_statistics")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class ArknightsStatistics implements Serializable {
    private static final Long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "INT not null default 0")
    private Integer total;
    @Column(columnDefinition = "INT not null default 0")
    private Integer sixCount;
    @Column(columnDefinition = "DOUBLE not null default 0")
    private Double sixRate;
    @Column(columnDefinition = "INT not null default 0")
    private Integer fiveCount;
    @Column(columnDefinition = "DOUBLE not null default 0")
    private Double fiveRate;
    @Column(columnDefinition = "INT not null default 0")
    private Integer fourCount;
    @Column(columnDefinition = "DOUBLE not null default 0")
    private Double fourRate;
    @Column(columnDefinition = "INT not null default 0")
    private Integer threeCount;
    @Column(columnDefinition = "DOUBLE not null default 0")
    private Double threeRate;

    @OneToOne(
            optional = true,
            fetch = FetchType.EAGER,
            targetEntity = RfUser.class,
            mappedBy = "arknights_statistics",
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
