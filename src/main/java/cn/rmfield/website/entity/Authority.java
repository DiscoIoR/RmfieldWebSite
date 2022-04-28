package cn.rmfield.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "authority")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class Authority implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String rolename;
    @ManyToMany(mappedBy = "authorityList")
    @JsonIgnore
    private List<RfUser> rfUserList;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getRolename() {
        return rolename;
    }
    public void setRolename(String name) {
        this.rolename = name;
    }
    public List<RfUser> getUserList() {
        return rfUserList;
    }
    public void setUserList(List<RfUser> rfUserList) {
        this.rfUserList = rfUserList;
    }
}
