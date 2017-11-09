package com.jiaye.cashloan.http.gongxinbao;

import com.google.gson.annotations.SerializedName;

/**
 * GongXinBaoSubmitRequest
 *
 * @author 贾博瑄
 */

public class GongXinBaoSubmitRequest {

    /*用户名*/
    @SerializedName("username")
    private String username;

    /*密码*/
    @SerializedName("password")
    private String password;

    /*图片验证码*/
    @SerializedName("code")
    private String code;

    /*随机短信验证码*/
    @SerializedName("randomPassword")
    private String randomPassword;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRandomPassword() {
        return randomPassword;
    }

    public void setRandomPassword(String randomPassword) {
        this.randomPassword = randomPassword;
    }
}
