package com.jiaye.loan.cashloan.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * DateUtil
 *
 * @author 贾博瑄
 */

public class DateUtil {

    public static String formatDateTime(long millis) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        return formatter.format(millis);
    }
}
