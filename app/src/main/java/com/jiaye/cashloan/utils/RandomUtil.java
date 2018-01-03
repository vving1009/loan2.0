package com.jiaye.cashloan.utils;

/**
 * RandomUtil
 *
 * @author 贾博瑄
 */

public class RandomUtil {

    public static String number4() {
        return number(4);
    }

    public static String number6() {
        return number(6);
    }

    public static String number(int number) {
        StringBuilder strRand = new StringBuilder();
        for (int i = 0; i < number; i++) {
            strRand.append(String.valueOf((int) (Math.random() * 10)));
        }
        return strRand.toString();
    }
}
