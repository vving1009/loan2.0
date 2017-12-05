package com.jiaye.cashloan.view.data.loan.source;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.loan.LoanDetails;
import com.jiaye.cashloan.http.data.loan.LoanDetailsRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * LoanDetailsRepository
 *
 * @author 贾博瑄
 */

public class LoanDetailsRepository implements LoanDetailsDataSource {

    @Override
    public Flowable<LoanDetails> requestLoanDetails() {
        return Flowable.just("SELECT loan_id FROM user;")
                .map(new Function<String, LoanDetailsRequest>() {
                    @Override
                    public LoanDetailsRequest apply(String sql) throws Exception {
                        String loanId = "";
                        SQLiteDatabase database = LoanApplication.getInstance().getSQLiteDatabase();
                        Cursor cursor = database.rawQuery(sql, null);
                        if (cursor != null) {
                            if (cursor.moveToNext()) {
                                loanId = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_LOAN_ID));
                            }
                            cursor.close();
                        }
                        LoanDetailsRequest request = new LoanDetailsRequest();
                        request.setLoanId(loanId);
                        return request;
                    }
                })
                .compose(new ResponseTransformer<LoanDetailsRequest, LoanDetails>("loanDetails"));
    }
}
