package com.jiaye.cashloan.view.data.loan.source;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.loan.LoanConfirm;
import com.jiaye.cashloan.http.data.loan.LoanConfirmInfo;
import com.jiaye.cashloan.http.data.loan.LoanConfirmInfoRequest;
import com.jiaye.cashloan.http.data.loan.LoanConfirmRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * LoanConfirmRepository
 *
 * @author 贾博瑄
 */

public class LoanConfirmRepository implements LoanConfirmDataSource {

    @Override
    public Flowable<LoanConfirmInfo> requestLoanConfirmInfo() {
        LoanConfirmInfoRequest request = new LoanConfirmInfoRequest();
        return Flowable.just(request)
                .compose(new ResponseTransformer<LoanConfirmInfoRequest, LoanConfirmInfo>("loanConfirmInfo"));
    }

    @Override
    public Flowable<String> requestLoanConfirm() {
        return Flowable.just("SELECT loan_id FROM user;")
                .map(new Function<String, LoanConfirmRequest>() {
                    @Override
                    public LoanConfirmRequest apply(String sql) throws Exception {
                        String loanId = "";
                        LoanConfirmRequest request = new LoanConfirmRequest();
                        SQLiteDatabase database = LoanApplication.getInstance().getSQLiteDatabase();
                        Cursor cursor = database.rawQuery(sql, null);
                        if (cursor != null) {
                            if (cursor.moveToNext()) {
                                loanId = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_LOAN_ID));
                            }
                            cursor.close();
                        }
                        request.setLoanId(loanId);
                        return request;
                    }
                })
                .compose(new ResponseTransformer<LoanConfirmRequest, LoanConfirm>("loanConfirm"))
                .flatMap(new Function<LoanConfirm, Publisher<String>>() {
                    @Override
                    public Publisher<String> apply(LoanConfirm loanConfirm) throws Exception {
                        return queryLoanId();
                    }
                });
    }

    @Override
    public Flowable<String> queryLoanId() {
        return Flowable.just("SELECT loan_id FROM user;")
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String sql) throws Exception {
                        String loanId = "";
                        SQLiteDatabase database = LoanApplication.getInstance().getSQLiteDatabase();
                        Cursor cursor = database.rawQuery(sql, null);
                        if (cursor != null) {
                            if (cursor.moveToNext()) {
                                loanId = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_LOAN_ID));
                            }
                            cursor.close();
                        }
                        return loanId;
                    }
                });
    }
}
