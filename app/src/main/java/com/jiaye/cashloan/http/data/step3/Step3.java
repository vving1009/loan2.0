package com.jiaye.cashloan.http.data.step3;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * Step3
 *
 * @author 贾博瑄
 */
public class Step3 extends SatcatcheChildResponse {

    @SerializedName("taobao_auth")
    private int taobao;

    @SerializedName("material_state")
    private int file;

    @SerializedName("sign")
    private int sign;

    public int getTaobao() {
        return taobao;
    }

    public void setTaobao(int taobao) {
        this.taobao = taobao;
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }
}
