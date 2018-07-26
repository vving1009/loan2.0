package com.satcatche.appupgrade.utils;

/**
 * NetworkError
 * <p>
 * stringResId 对应的文字仅用于说明,方便开发人员查看,不展示给用户.
 *
 * @author 贾博瑄
 */

public enum ErrorCode {

    /*成功*/
    SUCCESS("0000"),

    /*不返回内容*/
    EMPTY("9990"),

    /*登录过期*/
    TOKEN_OVERDUE("9994");

    private String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
