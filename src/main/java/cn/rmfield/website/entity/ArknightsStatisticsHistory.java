package cn.rmfield.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="arknights_statistics_history")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class ArknightsStatisticsHistory implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String ts;
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
    public String getTs() {
        return ts;
    }
    public void setTs(String ts) {
        this.ts = ts;
    }
    public ArknightsStatistics getArknightsStatistics() {
        return arknightsStatistics;
    }
    public void setArknightsStatistics(ArknightsStatistics arknightsStatistics) {
        this.arknightsStatistics = arknightsStatistics;
    }
}
