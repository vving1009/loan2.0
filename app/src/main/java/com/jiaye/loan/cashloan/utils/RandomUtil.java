package com.jiaye.loan.cashloan.utils;

/**
 * RandomUtil
 *
 * @author 贾博瑄
 */

public class RandomUtil {

    public static String number4() {
        StringBuilder strRand = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            strRand.append(String.valueOf((int) (Math.random() * 10)));
        }
        return strRand.toString();
    }
}
