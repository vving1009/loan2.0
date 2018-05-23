package com.jiaye.cashloan.utils;

/**
 * RegexUtil
 *
 * @author 贾博瑄
 */

public class RegexUtil {

    public static String phone() {
        return "^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$";
    }

    public static String email() {
        return "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    }

    /**
     * 密码为6-16位字母加数据
     */
    public static String password() {
        return "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
    }

    /**
     * 身份证为18位数字(最后一位可能为X)
     */
    public static String idCard() {
        return "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|x|X)$";
    }

    /**
     * 身份证有效期20180101-20190101
     */
    public static String idCardDate() {
        return "^\\d{8}-\\d{8}$";
    }
}
