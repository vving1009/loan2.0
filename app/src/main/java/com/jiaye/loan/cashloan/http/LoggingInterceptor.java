package com.jiaye.loan.cashloan.http;

import android.text.TextUtils;

import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.Logger;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * LoggingInterceptor
 *
 * @author 贾博瑄
 */

public class LoggingInterceptor implements HttpLoggingInterceptor.Logger {

    private static final String TAG = LoggingInterceptor.class.getName();

    @Override
    public void log(String message) {
        try {
            new JsonParser().parse(message);
            if (!TextUtils.isEmpty(message)) {
                Logger.t(TAG).json(message);
            }
        } catch (JsonSyntaxException e) {
            Logger.t(TAG).d(message);
        }
    }

}
