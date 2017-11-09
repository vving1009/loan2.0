package com.jiaye.cashloan.http.gongxinbao;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.utils.DateUtil;
import com.jiaye.cashloan.utils.MD5Util;
import com.jiaye.cashloan.utils.RandomUtil;

/**
 * GongXinBaoTokenRequest
 *
 * @author 贾博瑄
 */

public class GongXinBaoTokenRequest {

    /*当前授权操作的唯一标示*/
    @SerializedName("sequenceNo")
    private String sequenceNo = "PO" + DateUtil.formatDateTime(System.currentTimeMillis()) + RandomUtil.number4();

    /*手机号码*/
    @SerializedName("phone")
    private String phone;

    //  security        社保
    //  operator_pro    运营商
    //  xuexin	        学信
    //  jd	            京东
    //  credit	        人行征信
    //  resume	        简历
    //  sesame	        芝麻信用分（已下线）
    //  ecommerce	    电商（支付宝、淘宝）
    //  wechat	        微信、微粒贷
    //  kyc	            身份验证
    @SerializedName("authItem")
    private String authItem;

    /*姓名*/
    @SerializedName("name")
    private String name;

    /*身份证*/
    @SerializedName("idcard")
    private String idCard;

    /*商户应用ID*/
    @SerializedName("appId")
    private String appId = BuildConfig.GONGXINBAO_APPID;

    /*时间戳*/
    @SerializedName("timestamp")
    private String timestamp = String.valueOf(System.currentTimeMillis());

    /*签名 md5(appId+appSecurity+authItem+timestamp+sequenceNo) */
    @SerializedName("sign")
    private String sign;

    /*平台(可选)*/
    @SerializedName("platform")
    private String platform = "android";

    /*sdk版本(可选)*/
    @SerializedName("sdkVersion")
    private String sdkVersion = "1.0";

    /**
     * @param authItem security         社保
     *                 operator_pro     运营商
     *                 xuexin	        学信
     *                 jd	            京东
     *                 credit	        人行征信
     *                 resume	        简历
     *                 sesame	        芝麻信用分（已下线）
     *                 ecommerce	    电商（支付宝、淘宝）
     *                 wechat	        微信、微粒贷
     *                 kyc	            身份验证
     */
    public GongXinBaoTokenRequest(String authItem) {
        this.authItem = authItem;
        sign = MD5Util.get32MD5Lower(appId + BuildConfig.GONGXINBAO_APPSECURITY + authItem + timestamp + sequenceNo);
    }

    public String getSequenceNo() {
        return sequenceNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAuthItem() {
        return authItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAppId() {
        return appId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getSign() {
        return sign;
    }

    public String getPlatform() {
        return platform;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }
}
