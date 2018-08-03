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
 * SatcatcheRequestHeader
 *
 * @author 贾博瑄
 */
public class SatcatcheRequestHeader {

    /*接口编号*/
    @SerializedName("req_no")
    private String serialnumber;

    /*令牌(登录后必填)*/
    @SerializedName("userinfo_id")
    private String token;

    /*手机号(登录后必填)*/
    @SerializedName("phone")
    private String phone;

    /*设备名称*/
    @SerializedName("device_name")
    private String model;

    /*网络类型*/
    @SerializedName("network_type")
    private String networkType;

    /*IP地址*/
    @SerializedName("ip")
    private String ip;

    /*设备唯一标示*/
    @SerializedName("device_id")
    private String deviceId;

    private SatcatcheRequestHeader() {
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    @Override
    public String toString() {
        return "RequestHeader{" +
                "token='" + token + '\'' +
                ", phone='" + phone + '\'' +
                ", model='" + model + '\'' +
                ", networkType='" + networkType + '\'' +
                ", ip='" + ip + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }

    public static SatcatcheRequestHeader create(String serialnumber) {
        SatcatcheRequestHeader header = new SatcatcheRequestHeader();
        // 登录成功后查询数据库获取令牌
        String token = "";
        String phone = "";
        SQLiteDatabase database = LoanApplication.getInstance().getSQLiteDatabase();
        Cursor cursor = database.rawQuery("SELECT token, phone FROM user;", null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                token = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_TOKEN));
                phone = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE));
            }
            cursor.close();
        }
        header.setSerialnumber(serialnumber);
        header.setToken(token);
        // 获取手机号(登录成功后从数据库获取)
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