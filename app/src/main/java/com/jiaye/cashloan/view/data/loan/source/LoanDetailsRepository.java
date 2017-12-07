package com.jiaye.cashloan.view.data.loan.source;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.loan.LoanDetails;
import com.jiaye.cashloan.http.data.loan.LoanDetailsRequest;
import com.jiaye.cashloan.http.data.loan.LoanHistory;
import com.jiaye.cashloan.http.data.loan.LoanHistoryRequest;
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
    public Flowable<LoanDetails> requestLoanDetails(String id) {
        return Flowable.just(id)
                .map(new Function<String, LoanDetailsRequest>() {
                    @Override
                    public LoanDetailsRequest apply(String id) throws Exception {
                        LoanDetailsRequest request = new LoanDetailsRequest();
                        request.setLoanId(id);
                        return request;
                    }
                })
                .compose(new ResponseTransformer<LoanDetailsRequest, LoanDetails>("loanDetails"));
    }

    @Override
    public Flowable<LoanHistory> requestLoanHistory() {
        return Flowable.just("SELECT phone FROM user;")
                .map(new Function<String, LoanHistoryRequest>() {
                    @Override
                    public LoanHistoryRequest apply(String sql) throws Exception {
                        String phone = "";
                        SQLiteDatabase database = LoanApplication.getInstance().getSQLiteDatabase();
                        Cursor cursor = database.rawQuery(sql, null);
                        if (cursor != null) {
                            if (cursor.moveToNext()) {
                                phone = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE));
                            }
                            cursor.close();
                        }
                        LoanHistoryRequest request = new LoanHistoryRequest();
                        request.setPhone(phone);
                        return request;
                    }
                })
                .compose(new ResponseTransformer<LoanHistoryRequest, LoanHistory>("loanHistory"));
    }
}
