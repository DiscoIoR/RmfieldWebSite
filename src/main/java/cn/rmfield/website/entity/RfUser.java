package cn.rmfield.website.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="user")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class RfUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String realname;
    //权限
    @ManyToMany(
            cascade = {CascadeType.REFRESH},
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private List<Authority> authorityList;
    //方舟抽卡数据
    @OneToOne(
            optional = false,
            fetch = FetchType.EAGER,
            targetEntity = ArknightsStatistics.class,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "arknights_statistics_id",
            referencedColumnName = "id",
            unique = true
    )
    @JsonIgnore
    private ArknightsStatistics arknights_statistics;

    @Transient
    private String repassword;
    @Transient
    private String invitationcode;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<Authority> getAuthorityList() {
        return authorityList;
    }
    public void setAuthorityList(List<Authority> authorityList) {
        this.authorityList = authorityList;
    }
    public String getRepassword() {
        return repassword;
    }
    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }
    public String getRealname() {
        return realname;
    }
    public void setRealname(String realname) {
        this.realname = realname;
    }
    public String getInvitationcode() {
        return invitationcode;
    }
    public void setInvitationcode(String invitationcode) {
        this.invitationcode = invitationcode;
    }
    public ArknightsStatistics getArknightsStatistics() {
        return arknights_statistics;
    }
    public void setArknightsStatistics(ArknightsStatistics arknights_statistics) {
        this.arknights_statistics = arknights_statistics;
    }
}
