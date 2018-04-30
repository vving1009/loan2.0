package com.jiaye.cashloan.http.data.dictionary;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * DictionaryList
 *
 * @author 贾博瑄
 */
public class DictionaryList extends SatcatcheChildResponse {

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

        @SerializedName("dict_url")
        private String url;

        @SerializedName("dict_update")
        private boolean isNeedUpdate;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isNeedUpdate() {
            return isNeedUpdate;
        }

        public void setNeedUpdate(boolean needUpdate) {
            isNeedUpdate = needUpdate;
        }
    }
}
