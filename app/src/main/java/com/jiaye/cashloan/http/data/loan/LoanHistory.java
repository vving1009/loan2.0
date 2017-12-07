package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.ChildResponse;

import java.util.List;

/**
 * LoanHistory
 *
 * @author 贾博瑄
 */

public class LoanHistory extends ChildResponse {

    @SerializedName("lendapply_list")
    private List<LoanDetails> list;

    public List<LoanDetails> getList() {
        return list;
    }

    public void setList(List<LoanDetails> list) {
        this.list = list;
    }
}
