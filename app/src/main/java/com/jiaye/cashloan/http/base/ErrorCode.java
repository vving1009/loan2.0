package com.jiaye.cashloan.http.base;

import com.jiaye.cashloan.R;

/**
 * NetworkError
 * <p>
 * stringResId 对应的文字仅用于说明,方便开发人员查看,不展示给用户.
 *
 * @author 贾博瑄
 */

public enum ErrorCode {

    /*成功*/
    SUCCESS("0000", R.string.error_0000),

    /*不返回内容*/
    EMPTY("9990", R.string.error_9990),

    /*登录过期*/
    TOKEN_OVERDUE("9994", R.string.error_9994);

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
