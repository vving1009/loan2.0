package com.jiaye.cashloan.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * DateUtil
 *
 * @author 贾博瑄
 */

public class DateUtil {

    public static String formatDateTimeMillis(long millis) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmssSS", Locale.CHINA);
        return formatter.format(millis);
    }

    public static String formatDateTime(long millis) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        return formatter.format(millis);
    }

    public static String formatDate(long millis) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        return formatter.format(millis);
    }

    public static String formatTime(long millis) {
        SimpleDateFormat formatter = new SimpleDateFormat("HHmmss", Locale.CHINA);
        return formatter.format(millis);
    }
}
