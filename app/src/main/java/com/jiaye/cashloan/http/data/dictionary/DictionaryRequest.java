package com.jiaye.cashloan.http.data.dictionary;

import com.google.gson.annotations.SerializedName;

/**
 * DictionaryRequest
 *
 * @author 贾博瑄
 */

public class DictionaryRequest {

    public DictionaryRequest(String type) {
        this.type = type;
    }

    /**
     * dict_cd  地区
     * dict_edu 学历
     * dict_mar 婚姻状况
     * dict_rel 关系类型
     */
    @SerializedName("dict_type")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
