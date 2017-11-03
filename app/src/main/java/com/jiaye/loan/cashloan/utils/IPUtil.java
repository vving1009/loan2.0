package com.jiaye.loan.cashloan.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;

import static android.content.Context.WIFI_SERVICE;

/**
 * IPUtil
 *
 * @author 贾博瑄
 */

public class IPUtil {

    /**
     * 根据当前网络类型返回IP
     *
     * @return IP as string
     */
    public static String getIP(Context context) {
        if (NetworkTypeUtil.getNetworkType(context).equals("WIFI")) {
            return getWifiIP(context);
        } else {
            return getMobileIP();
        }
    }

    /**
     * Get the IP of current Wi-Fi connection
     *
     * @return IP as string
     */
    public static String getWifiIP(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
            //noinspection ConstantConditions
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            return String.format(Locale.getDefault(), "%d.%d.%d.%d",
                    (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                    (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * Get IP of mobile
     *
     * @return IP as string
     */
    public static String getMobileIP() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ignored) {
        }
        return null;
    }
}
