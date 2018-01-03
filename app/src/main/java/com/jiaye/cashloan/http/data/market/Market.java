package com.jiaye.cashloan.http.data.market;

/**
 * Created by guozihua on 2018/1/3.
 */

public class Market {
    private String code ;
    private String msg ;
    private String body ;

    public void setCode(String code){
        this.code = code ;
    }
    public String getCode(){
        return  code ;
    }

    public void setMsg(String msg){
        this.msg = msg ;
    }
    public String getMsg(){
        return msg ;
    }
    public void setBody(String body){
        this.body = body ;
    }
    public String getBody(){
        return body ;
    }
}
