package com.jiaye.cashloan.http.data.dictionary;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildRequest;

/**
 * DictionaryListRequest
 *
 * @author 贾博瑄
 */

public class DictionaryListRequest extends ChildRequest {

    @SerializedName("dict_data")
    private Dictionary[] dictionaries;

    public Dictionary[] getDictionaries() {
        return dictionaries;
    }

    public void setDictionaries(Dictionary[] dictionaries) {
        this.dictionaries = dictionaries;
    }

    public static class Dictionary {

        @SerializedName("dict_type")
        private int type;

        @SerializedName("dict_md5")
        private String md5;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }
    }

    @Override
    protected String getBusiness() {
        return "CL010";
    }
}
