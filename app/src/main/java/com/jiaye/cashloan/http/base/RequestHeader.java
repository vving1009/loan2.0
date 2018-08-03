package com.jiaye.cashloan.http.base;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.persistence.DbContract;
import com.satcatche.library.utils.DeviceInfoUtil;
import com.satcatche.library.utils.IPUtil;
import com.satcatche.library.utils.NetworkTypeUtil;

/**
 * RequestHeader
 *
 * @author 贾博瑄
 */

public class RequestHeader {

    /*令牌*/
    @SerializedName("jcb_id")
    private String token;

    /*设备名称*/
    @SerializedName("system_id")
    private String model;

    /*网络类型*/
    @SerializedName("network_type")
    private String networkType;

    /*IP地址*/
    @SerializedName("ip")
    private String ip;

    /*设备唯一标示*/
    @SerializedName("token_id")
    private String deviceId;

    /*身份证(获取后必填)*/
    @SerializedName("user_identifyid")
    private String ocrId;

    /*姓名(获取后必填)*/
    @SerializedName("user_name")
    private String ocrName;

    /*手机号(可选)*/
    @SerializedName("phone_num")
    private String phone;

    private RequestHeader() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getOcrId() {
        return ocrId;
    }

    public void setOcrId(String ocrId) {
        this.ocrId = ocrId;
    }

    public String getOcrName() {
        return ocrName;
    }

    public void setOcrName(String ocrName) {
        this.ocrName = ocrName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "RequestHeader{" +
                "token='" + token + '\'' +
                ", model='" + model + '\'' +
                ", networkType='" + networkType + '\'' +
                ", ip='" + ip + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }

    public static RequestHeader create() {
        RequestHeader header = new RequestHeader();
        // 登录成功后查询数据库获取令牌
        String token = "";
        String ocrId = "";
        String ocrName = "";
        String phone = "";
        SQLiteDatabase database = LoanApplication.getInstance().getSQLiteDatabase();
        Cursor cursor = database.rawQuery("SELECT token, ocr_id, ocr_name, phone FROM user;", null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                token = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_TOKEN));
                ocrId = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_ID));
                ocrName = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_NAME));
                phone = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE));
            }
            cursor.close();
        }
        header.setToken(token);
        header.setOcrId(ocrId);
        header.setOcrName(ocrName);
        header.setPhone(phone);
        // 获取手机型号
        header.setModel(Build.MODEL);
        // 获取手机当前网络类型
        header.setNetworkType(NetworkTypeUtil.getNetworkType(LoanApplication.getInstance()));
        // 获取手机ip地址
        header.setIp(IPUtil.getIP(LoanApplication.getInstance()));
        // 获取手机唯一标示
        header.setDeviceId(DeviceInfoUtil.getDeviceId());
        return header;
    }
}
