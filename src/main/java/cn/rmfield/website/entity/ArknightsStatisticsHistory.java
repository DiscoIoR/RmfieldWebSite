package cn.rmfield.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="arknights_statistics_history")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class ArknightsStatisticsHistory implements Serializable{
    private static final Long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Integer ts;    //Unix时间戳,timestamp
    private String name;
    private Integer rarity;
    private Boolean isNew;
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
