package com.jiaye.cashloan.http.data.dictionary;

import com.bigkoo.pickerview.model.IPickerViewData;
import com.google.gson.annotations.SerializedName;

/**
 * Education
 *
 * @author 贾博瑄
 */

public class Education implements IPickerViewData {

    @SerializedName("key")
    private String key;

    @SerializedName("value")
    private String value;

    private boolean isSelect;

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

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public String getPickerViewText() {
        return value;
    }
}
