package com.jiaye.cashloan.view.data.auth;

import com.bigkoo.pickerview.model.IPickerViewData;
import com.google.gson.annotations.SerializedName;

/**
 * Relation
 *
 * @author 贾博瑄
 */

public class Relation implements IPickerViewData {

    @SerializedName("key")
    private String key;

    @SerializedName("value")
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getPickerViewText() {
        return value;
    }
}
