package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * Sesame
 *
 * @author 贾博瑄
 */

public class Sesame extends SatcatcheChildResponse {

    @SerializedName("zm_score")
    private String score;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
