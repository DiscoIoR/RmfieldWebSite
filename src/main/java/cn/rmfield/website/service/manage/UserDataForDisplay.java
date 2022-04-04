package cn.rmfield.website.service.manage;

import cn.rmfield.website.entity.Authority;
import cn.rmfield.website.entity.RfUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDataForDisplay {
    private Integer userId;
    private String username;
    private String realname;
    private List<Map<String,Object>> authorityDataList;

    public UserDataForDisplay(){}

    public UserDataForDisplay(RfUser rfUser){
        this.userId = rfUser.getId();
        this.username = rfUser.getUsername();
        this.realname = rfUser.getRealname();
        this.authorityDataList = new ArrayList<>();
        for(Authority authority:rfUser.getAuthorityList()){
            Map<String,Object> authorityData = new HashMap<>();
            authorityData.put("roleId",authority.getId());
            authorityData.put("rolename",authority.getRolename());
            authorityDataList.add(authorityData);
        }
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
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
    public List<Map<String, Object>> getAuthorityDataList() {
        return authorityDataList;
    }
    public void setAuthorityDataList(List<Map<String, Object>> authorityDataList) {
        this.authorityDataList = authorityDataList;
    }
}
