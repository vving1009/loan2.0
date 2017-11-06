package com.jiaye.cashloan.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by boxuanjia.
 */
public class Base64Util {

    private Base64Util() {
    }

    /**
     * 解码
     *
     * @param base64Str base64Str
     * @return byte[]
     */
    public static byte[] decode(String base64Str) {
        return Base64.decode(base64Str, Base64.DEFAULT);
    }

    /**
     * 编码
     *
     * @param bytes bytes
     * @return String
     */
    public static String encode(byte[] bytes) {
        try {
            return new String(Base64.encode(bytes, Base64.DEFAULT), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

}
