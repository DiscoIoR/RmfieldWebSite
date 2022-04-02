package cn.rmfield.website.entity.arknights;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="arknights_gacha_history")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class ArknightsGachaHistory implements Serializable{
    private static final Long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Integer ts;    //寻访时间   timestamp
    private String pool;    //卡池
    private String name;    //角色名称
    private Integer rarity; //角色等级（星级-1）
    private Boolean isNew;  //是否为新获得
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
    public String getPool() {
        return pool;
    }
    public void setPool(String pool) {
        this.pool = pool;
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
    public ArknightsStatistics getArknightsStatistics() {
        return arknightsStatistics;
    }
    public void setArknightsStatistics(ArknightsStatistics arknightsStatistics) {
        this.arknightsStatistics = arknightsStatistics;
    }
}
