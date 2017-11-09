package com.jiaye.cashloan.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Base64Util
 *
 * @author 贾博瑄
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

    /**
     * 文件转Base64
     *
     * @param file file
     * @return String
     */
    public static String fileToBase64(File file) {
        String base64 = null;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            int length = in.read(bytes);
            base64 = Base64.encodeToString(bytes, 0, length, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return base64;
    }

    /**
     * Base64 转 Bitmap
     *
     * @param base64Data base64Data
     * @return Bitmap
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
