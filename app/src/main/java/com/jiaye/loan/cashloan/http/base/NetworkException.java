package com.jiaye.loan.cashloan.http.base;

/**
 * NetworkException
 *
 * @author 贾博瑄
 */

public class NetworkException extends RuntimeException {

    private String mErrorCode;

    private String mErrorMessage;

    public NetworkException(String errorCode, String errorMessage) {
        super("错误码:" + errorCode + "\n错误信息:" + errorMessage);
        mErrorCode = errorCode;
        mErrorMessage = errorMessage;
    }

    public String getErrorCode() {
        return mErrorCode;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }
}
