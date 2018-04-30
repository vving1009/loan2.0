package com.jiaye.cashloan.view.data.loan.auth.source.info;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.loan.LoanInfoAuth;
import com.jiaye.cashloan.http.data.loan.LoanInfoAuthRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;

import io.reactivex.Flowable;

/**
 * LoanAuthInfoRepository
 *
 * @author 贾博瑄
 */

public class LoanAuthInfoRepository implements LoanAuthInfoDataSource {

    @Override
    public Flowable<LoanInfoAuth> requestLoanInfoAuth() {
        String loanId = "";
        SQLiteDatabase database = LoanApplication.getInstance().getSQLiteDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM user;", null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                loanId = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_LOAN_ID));
            }
            cursor.close();
        }
        LoanInfoAuthRequest request = new LoanInfoAuthRequest();
        request.setLoanId(loanId);
        return Flowable.just(request)
                .compose(new SatcatcheResponseTransformer<LoanInfoAuthRequest, LoanInfoAuth>
                        ("loanInfoAuth"));
    }
}
