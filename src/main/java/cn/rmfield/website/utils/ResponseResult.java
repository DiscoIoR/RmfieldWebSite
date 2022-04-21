package cn.rmfield.website.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

/*
* state:0   is Login
* state:1   not Login
* state:5   auth error
* state:10   failed,unknow error
*/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult {
    /**
     * state:0   is Login
     * state:1   not Login
     * state:5   auth error
     * state:10   failed,unknow error
     */
    private Integer status;
    /**
     * 提示信息，如果有错误时，前端可以获取该字段进行提示
     */
    private String msg;
    /**
     * 数据
     */
    private Object data;

    public ResponseResult(Integer status,String msg){
        this.status = status;
        this.msg = msg;
    }

    public ResponseResult(Integer status,Object data){
        this.status = status;
        this.data = data;
    }
    public ResponseResult(Integer status,String msg,Object data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
}
