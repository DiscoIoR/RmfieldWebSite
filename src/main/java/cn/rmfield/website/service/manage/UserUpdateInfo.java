package cn.rmfield.website.service.manage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserUpdateInfo {
    private Integer id;
    private String username;
    private String realname;
    private String password;
    @JsonProperty("ROLE_ADMIN")
    private Boolean ROLE_ADMIN;
    @JsonProperty("ROLE_DBA")
    private Boolean ROLE_DBA;
    @JsonProperty("ROLE_USER")
    private Boolean ROLE_USER;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getRealname() {
        return realname;
    }
    public void setRealname(String realname) {
        this.realname = realname;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Boolean getROLE_ADMIN() {
        return ROLE_ADMIN;
    }
    public void setROLE_ADMIN(Boolean ROLE_ADMIN) {
        this.ROLE_ADMIN = ROLE_ADMIN;
    }
    public Boolean getROLE_DBA() {
        return ROLE_DBA;
    }
    public void setROLE_DBA(Boolean ROLE_DBA) {
        this.ROLE_DBA = ROLE_DBA;
    }
    public Boolean getROLE_USER() {
        return ROLE_USER;
    }
    public void setROLE_USER(Boolean ROLE_USER) {
        this.ROLE_USER = ROLE_USER;
    }
}
