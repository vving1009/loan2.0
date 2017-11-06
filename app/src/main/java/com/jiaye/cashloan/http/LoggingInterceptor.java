package com.jiaye.cashloan.http;

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

    @Override
    public void log(String message) {
        try {
            new JsonParser().parse(message);
            if (!TextUtils.isEmpty(message)) {
                Logger.json(message);
            }
        } catch (JsonSyntaxException e) {
            Logger.d(message);
        }
    }

}
