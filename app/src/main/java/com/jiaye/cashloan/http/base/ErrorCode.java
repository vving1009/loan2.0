package com.jiaye.cashloan.http.base;

import com.jiaye.cashloan.R;

/**
 * NetworkError
 *
 * @author 贾博瑄
 */

public enum ErrorCode {

    SUCCESS("0000", R.string.error_0000),

    FAILURE_0001("0001", R.string.error_0001),

    FAILURE_0002("0002", R.string.error_0002),

    FAILURE_0003("0003", R.string.error_0003),

    FAILURE_0004("0004", R.string.error_0004),

    FAILURE_0005("0005", R.string.error_0005),

    FAILURE_0006("0006", R.string.error_0006),

    FAILURE_0007("0007", R.string.error_0007),

    FAILURE_0008("0008", R.string.error_0008),

    FAILURE_0009("0009", R.string.error_0009),

    FAILURE_0010("0010", R.string.error_0010),

    FAILURE_0011("0011", R.string.error_0011),

    FAILURE_0012("0012", R.string.error_0012),

    FAILURE_2001("2001", R.string.error_2001),

    FAILURE_2002("2002", R.string.error_2002),

    EMPTY("9990", R.string.error_9990),

    FAILURE_9997("9997", R.string.error_9997),

    FAILURE_9998("9998", R.string.error_9998),

    FAILURE_9999("9999", R.string.error_9999);


    private String code;

    private int stringResId;

    ErrorCode(String code, int stringResId) {
        this.code = code;
        this.stringResId = stringResId;
    }

    public String getCode() {
        return code;
    }

    public int getStringResId() {
        return stringResId;
    }
}
