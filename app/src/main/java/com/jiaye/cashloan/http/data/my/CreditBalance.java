package com.jiaye.cashloan.http.data.my;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildResponse;

/**
 * CreditBalance
 *
 * @author 贾博瑄
 */

public class CreditBalance extends ChildResponse {

    /*可用余额*/
    @SerializedName("avail_bal")
    private String availBal;

    /*冻结余额*/
    @SerializedName("freeze_bal")
    private String freezeBal;

    /*账面余额*/
    @SerializedName("curr_bal")
    private String currBal;

    public String getAvailBal() {
        return availBal;
    }

    public void setAvailBal(String availBal) {
        this.availBal = availBal;
    }

    public String getFreezeBal() {
        return freezeBal;
    }

    public void setFreezeBal(String freezeBal) {
        this.freezeBal = freezeBal;
    }

    public String getCurrBal() {
        return currBal;
    }

    public void setCurrBal(String currBal) {
        this.currBal = currBal;
    }
}
