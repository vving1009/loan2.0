package com.jiaye.cashloan.http.tongdun;

import com.google.gson.annotations.SerializedName;

/**
 * TongDunAntifraudRealName
 *
 * @author 贾博瑄
 */

public class TongDunAntifraudRealName {

    @SerializedName("RealNameCheck")
    private RealNameCheck realNameCheck;

    public RealNameCheck getRealNameCheck() {
        return realNameCheck;
    }

    public void setRealNameCheck(RealNameCheck realNameCheck) {
        this.realNameCheck = realNameCheck;
    }

    public class RealNameCheck {

        /*0 一致 1 不一致 2 无记录*/
        @SerializedName("realname_consistence")
        private int code;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}
