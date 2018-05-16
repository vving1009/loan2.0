package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * QueryUploadPhoto
 *
 * @author 贾博瑄
 */
public class QueryUploadPhoto extends SatcatcheChildResponse {

    @SerializedName("pic_count")
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
