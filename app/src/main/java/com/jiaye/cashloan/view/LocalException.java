package com.jiaye.cashloan.view;

/**
 * LocalException
 *
 * @author 贾博瑄
 */

public class LocalException extends RuntimeException {

    private int mErrorId;

    public LocalException(int errorId) {
        mErrorId = errorId;
    }

    public int getErrorId() {
        return mErrorId;
    }

    public void setErrorId(int errorId) {
        mErrorId = errorId;
    }
}
