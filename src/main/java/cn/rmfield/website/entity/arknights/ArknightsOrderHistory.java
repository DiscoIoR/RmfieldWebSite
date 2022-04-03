package cn.rmfield.website.entity.arknights;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="arknights_order_history")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class ArknightsOrderHistory implements Serializable {
    private static final Long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String orderId; //交易编号
    private String platform;   //平台 iOS,Android
    private Integer amount; //充值金额
    private String productName; //项目名称
    private Integer payTime;    //交易时间  timestamp
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
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getPlatform() {
        return platform;
    }
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public Integer getPayTime() {
        return payTime;
    }
    public void setPayTime(Integer payTime) {
        this.payTime = payTime;
    }
    public ArknightsStatistics getArknightsStatistics() {
        return arknightsStatistics;
    }
    public void setArknightsStatistics(ArknightsStatistics arknightsStatistics) {
        this.arknightsStatistics = arknightsStatistics;
    }
}
