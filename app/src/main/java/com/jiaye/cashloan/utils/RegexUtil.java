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
}
