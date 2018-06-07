package com.jiaye.cashloan.persistence;

/**
 * User
 *
 * @author 贾博瑄
 */
public class User {

    /**
     * 手机号
     */
    private String phone;

    /**
     * 令牌
     */
    private String token;

    /**
     * 借款编号
     */
    private String loanId;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }
}
