package com.satcatche.appupgrade.utils;

/**
 * UpgradeConfig 必须在application中设定所有属性
 */

public class UpgradeConfig {

    private static String checkUpgradeUrl;

    private static int versionCode;

    private static String packageName;

    public static int getVersionCode() {
        return versionCode;
    }

    public static void setVersionCode(int versionCode) {
        UpgradeConfig.versionCode = versionCode;
    }

    public static String getPackageName() {
        return packageName;
    }

    public static void setPackageName(String packageName) {
        UpgradeConfig.packageName = packageName;
    }

    public static String getCheckUpgradeUrl() {
        return checkUpgradeUrl;
    }

    public static void setCheckUpgradeUrl(String checkUpgradeUrl) {
        UpgradeConfig.checkUpgradeUrl = checkUpgradeUrl;
    }
}
