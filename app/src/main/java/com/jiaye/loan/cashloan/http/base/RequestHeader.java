package com.jiaye.loan.cashloan.http.base;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.google.gson.annotations.SerializedName;
import com.jiaye.loan.cashloan.LoanApplication;
import com.jiaye.loan.cashloan.persistence.DbContract;
import com.jiaye.loan.cashloan.persistence.DbHelper;
import com.jiaye.loan.cashloan.utils.DeviceInfoUtil;
import com.jiaye.loan.cashloan.utils.IPUtil;
import com.jiaye.loan.cashloan.utils.NetworkTypeUtil;

/**
 * RequestHeader
 *
 * @author 贾博瑄
 */

public class RequestHeader {

    /*令牌*/
    @SerializedName("jcb_id")
    private String token;

    /*手机号(可选)*/
    @SerializedName("phone_num")
    private String phone;

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

    private RequestHeader() {
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

    public static RequestHeader create() {
        RequestHeader header = new RequestHeader();
        // 登录成功后查询数据库获取令牌
        String token = null;
        DbHelper dbHelper = new DbHelper(LoanApplication.getInstance());
        SQLiteDatabase databaseHelper = dbHelper.getWritableDatabase();
        String sql = "SELECT token FROM user;";
        Cursor cursor = databaseHelper.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                token = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_TOKEN));
            }
            cursor.close();
        }
        databaseHelper.close();
        header.setToken(token);
        // 部分手机可以获取手机号(不再获取,成功率太低.)
        header.setPhone(null);
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
