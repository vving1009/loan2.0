package com.jiaye.loan.cashloan.utils;

import android.os.Build;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * DeviceInfoUtil
 *
 * @author 贾博瑄
 */

public class DeviceInfoUtil {

    public static String getDeviceId() {
        String deviceId = getUniquePsuedoID();
        try {
            deviceId = SHA(deviceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (deviceId.length() > 16) {
            deviceId = deviceId.substring(0, 16).toUpperCase();
        } else {
            deviceId = deviceId.toUpperCase();
        }
        return deviceId;
    }

    private static String SHA(String s) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        sha.update(s.getBytes());
        char hexDigits[] = {'f', 'e', '2', '3', 'd', '5', '6', '7', '8', '9',
                'a', 'b', 'c', '4', '1', '0'};

        try {
            sha = MessageDigest.getInstance("SHA-1");
            sha.update(s.getBytes());
            byte[] md = sha.digest();

            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
            return "";

        }
    }

    private static String getUniquePsuedoID() {
        // If all else fails, if the user does have lower than API 9 (lower
        // than Gingerbread), has reset their device or 'Secure.ANDROID_ID'
        // returns 'null', then simply the ID returned will be solely based
        // off their Android device information. This is where the collisions
        // can happen.
        // Thanks http://www.pocketmagic.net/?p=1662!
        // Try not to use DISPLAY, HOST or ID - these items could change.
        // If there are collisions, there will be overlapping data
        String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);

        // Thanks to @Roman SL!
        // http://stackoverflow.com/a/4789483/950427
        // Only devices with API >= 9 have android.os.Build.SERIAL
        // http://developer.android.com/reference/android/os/Build.html#SERIAL
        // If a user upgrades software or roots their device, there will be a duplicate entry
        String serial;
        try {
            serial = Build.class.getField("SERIAL").get(null).toString();
            // Go ahead and return the serial for api => 9
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            // String needs to be initialized
            serial = "serial"; // some value
        }
        // Thanks @Joe!
        // http://stackoverflow.com/a/2853253/950427
        // Finally, combine the values we have found by using the UUID class to create a unique identifier
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }
}
