package cn.rmfield.website.entity.arknights;

import cn.rmfield.website.entity.RfUser;
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
    //明日方舟账号信息
    @Column(columnDefinition = "VARCHAR(225) not null default 'default'")
    private String arknights_uid;   //用户id
    @Column(columnDefinition = "VARCHAR(225) not null default 'default'")
    private String arknights_nickName;  //用户昵称
    //抽卡记录统计
    @Column(columnDefinition = "INT not null default 0")
    private Integer totalCount; //获得角色总数
    @Column(columnDefinition = "INT not null default 0")
    private Integer sixCount;   //获得六星角色数
    @Column(columnDefinition = "DOUBLE not null default 0")
    private Double sixRate; //六星角色占比
    @Column(columnDefinition = "INT not null default 0")
    private Integer fiveCount;  //获得五星角色数
    @Column(columnDefinition = "DOUBLE not null default 0")
    private Double fiveRate;    //五星角色占比
    @Column(columnDefinition = "INT not null default 0")
    private Integer fourCount;  //获得四星角色数
    @Column(columnDefinition = "DOUBLE not null default 0")
    private Double fourRate;    //四星角色占比
    @Column(columnDefinition = "INT not null default 0")
    private Integer threeCount; //获得三星角色数
    @Column(columnDefinition = "DOUBLE not null default 0")
    private Double threeRate;   //三星角色占比
    //源石记录统计
    @Column(columnDefinition = "INT not null default 0")
    private Integer diamondGrossIncome; //源石总收入
    @Column(columnDefinition = "INT not null default 0")
    private Integer diamondGrossExpenses;   //源石总支出
    //氪金记录统计
    @Column(columnDefinition = "INT not null default 0")
    private Integer totalCost;  //总氪金量

    @OneToOne(
            optional = true,
            fetch = FetchType.EAGER,
            targetEntity = RfUser.class,
            mappedBy = "arknights_statistics",
            cascade = CascadeType.ALL
    )
    private RfUser rfUser;

    //抽卡记录
    @OneToMany(
            mappedBy = "arknightsStatistics",
            cascade = CascadeType.ALL,
            targetEntity = ArknightsGachaHistory.class,
            fetch = FetchType.LAZY
    )
    private List<ArknightsGachaHistory> arknightsGachaHistories;

    //氪金记录
    @OneToMany(
            mappedBy = "arknightsStatistics",
            cascade = CascadeType.ALL,
            targetEntity = ArknightsOrderHistory.class,
            fetch = FetchType.LAZY
    )
    private List<ArknightsOrderHistory> arknightsOrderHistories;

    //源石记录
    @OneToMany(
            mappedBy = "arknightsStatistics",
            cascade = CascadeType.ALL,
            targetEntity = ArknightsDiamondHistory.class,
            fetch = FetchType.LAZY
    )
    private List<ArknightsDiamondHistory> arknightsDiamondHistories;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getArknights_uid() {
        return arknights_uid;
    }
    public void setArknights_uid(String arknights_uid) {
        this.arknights_uid = arknights_uid;
    }
    public String getArknights_nickName() {
        return arknights_nickName;
    }
    public void setArknights_nickName(String arknights_nickName) {
        this.arknights_nickName = arknights_nickName;
    }
    public Integer getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(Integer total) {
        this.totalCount = total;
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
    public List<ArknightsGachaHistory> getArknightsGachaHistories() {
        return arknightsGachaHistories;
    }
    public void setArknightsGachaHistories(List<ArknightsGachaHistory> arknightsGachaHistories) {
        this.arknightsGachaHistories = arknightsGachaHistories;
    }
    public List<ArknightsOrderHistory> getArknightsOrderHistories() {
        return arknightsOrderHistories;
    }
    public void setArknightsOrderHistories(List<ArknightsOrderHistory> arknightsOrderHistories) {
        this.arknightsOrderHistories = arknightsOrderHistories;
    }
    public List<ArknightsDiamondHistory> getArknightsDiamondHistories() {
        return arknightsDiamondHistories;
    }
    public void setArknightsDiamondHistories(List<ArknightsDiamondHistory> arknightsDiamondHistories) {
        this.arknightsDiamondHistories = arknightsDiamondHistories;
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
    public Integer getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }
}
