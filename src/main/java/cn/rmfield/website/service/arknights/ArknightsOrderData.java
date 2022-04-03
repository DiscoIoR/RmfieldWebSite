package cn.rmfield.website.service.arknights;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class ArknightsOrderDataEach{
    private String orderId;
    private String platform;
    private Integer amount;
    private String productName;
    private Integer payTime;

    public ArknightsOrderDataEach(String orderId, String platform, Integer amount, String productName, Integer payTime) {
        this.orderId = orderId;
        this.platform = platform;
        this.amount = amount;
        this.productName = productName;
        this.payTime = payTime;
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
}

public class ArknightsOrderData {
    private Integer totalCost;
    private Set<String> orderIdSet;
    private List<ArknightsOrderDataEach> orderDataEachList;

    public ArknightsOrderData(Integer totalCost, Set<String> orderIdSet) {
        this.totalCost = totalCost;
        this.orderIdSet = orderIdSet;
        this.orderDataEachList = new ArrayList<>();
    }

    public ArknightsOrderData(Integer totalCost, List<ArknightsOrderDataEach> orderDataEachList) {
        this.totalCost = totalCost;
        this.orderIdSet = new HashSet<>();
        this.orderDataEachList = orderDataEachList;
    }

    public Integer getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }
    public Set<String> getOrderIdSet() {
        return orderIdSet;
    }
    public void setOrderIdSet(Set<String> orderIdSet) {
        this.orderIdSet = orderIdSet;
    }
    public List<ArknightsOrderDataEach> getOrderDataEachList() {
        return orderDataEachList;
    }
    public void setOrderDataEachList(List<ArknightsOrderDataEach> orderDataEachList) {
        this.orderDataEachList = orderDataEachList;
    }
}
