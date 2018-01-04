package com.jiaye.cashloan.http.data.my;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildResponse;

/**
 * CreditPasswordStatus
 *
 * @author 贾博瑄
 */

public class CreditPasswordStatus extends ChildResponse {

    /*是否开户 0 未开户 1 已开户*/
    @SerializedName("open_account")
    private String open = "0";

    /*密码状态 0 设置初始密码 1 重置密码*/
    @SerializedName("pinFlag")
    private String status = "0";

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
