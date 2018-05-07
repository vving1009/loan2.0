package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildRequest;

/**
 * LoanUploadPictureRequest
 *
 * @author 贾博瑄
 */

public class LoanUploadPictureRequest extends SatcatcheChildRequest {

    @SerializedName("phone")
    private String phone;

    /**
     * 1 身份认证 2 活体检测
     */
    @SerializedName("pic_source")
    private String source;

    /**
     * 1 身份证正面 2 身份证反面 3 活体检测
     */
    @SerializedName("pic_type")
    private String type;

    @SerializedName("pic_base64")
    private String base64;

    @SerializedName("pic_name")
    private String name;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected String getBusiness() {
        return "CL029";
    }
}
