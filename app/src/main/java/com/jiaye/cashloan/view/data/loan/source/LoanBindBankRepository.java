package com.jiaye.cashloan.view.data.loan.source;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.loan.LoanBindBank;
import com.jiaye.cashloan.http.data.loan.LoanBindBankRequest;
import com.jiaye.cashloan.http.data.loan.LoanOpen;
import com.jiaye.cashloan.http.data.loan.LoanOpenRequest;
import com.jiaye.cashloan.http.data.loan.LoanOpenSMS;
import com.jiaye.cashloan.http.data.loan.LoanOpenSMSRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * LoanBindBankRepository
 *
 * @author 贾博瑄
 */

public class LoanBindBankRepository implements LoanBindBankDataSource {

    private String mCode;

    @Override
    public Flowable<String> queryName() {
        return Flowable.just("SELECT ocr_name FROM user;")
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String sql) throws Exception {
                        String name = "";
                        SQLiteDatabase database = LoanApplication.getInstance().getSQLiteDatabase();
                        Cursor cursor = database.rawQuery(sql, null);
                        if (cursor != null) {
                            if (cursor.moveToNext()) {
                                name = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_NAME));
                            }
                            cursor.close();
                        }
                        return name;
                    }
                });
    }

    @Override
    public Flowable<LoanOpenSMS> requestBindBankSMS(LoanOpenSMSRequest request) {
        return Flowable.just(request).flatMap(new Function<LoanOpenSMSRequest, Publisher<LoanOpenSMS>>() {
            @Override
            public Publisher<LoanOpenSMS> apply(LoanOpenSMSRequest request) throws Exception {
                return Flowable.just(request).compose(new ResponseTransformer<LoanOpenSMSRequest, LoanOpenSMS>("loanOpenSMS"));
            }
        }).map(new Function<LoanOpenSMS, LoanOpenSMS>() {
            @Override
            public LoanOpenSMS apply(LoanOpenSMS loanBindBankSMS) throws Exception {
                mCode = loanBindBankSMS.getCode();
                return loanBindBankSMS;
            }
        });
    }

    @Override
    public Flowable<LoanBindBank> requestBindBank(LoanBindBankRequest request) {
        return Flowable.just(request)
                .compose(new ResponseTransformer<LoanBindBankRequest, LoanBindBank>("loanBindBank"));
    }

    @Override
    public Flowable<LoanOpen> requestOpen(final LoanOpenRequest request) {
        return Flowable.just("SELECT ocr_id, ocr_name FROM user;")
                .map(new Function<String, LoanOpenRequest>() {
                    @Override
                    public LoanOpenRequest apply(String sql) throws Exception {
                        String id = "";
                        String name = "";
                        SQLiteDatabase database = LoanApplication.getInstance().getSQLiteDatabase();
                        Cursor cursor = database.rawQuery(sql, null);
                        if (cursor != null) {
                            if (cursor.moveToNext()) {
                                id = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_ID));
                                name = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_NAME));
                            }
                            cursor.close();
                        }
                        request.setId(id);
                        request.setName(name);
                        request.setCode(mCode);
                        return request;
                    }
                }).flatMap(new Function<LoanOpenRequest, Publisher<LoanOpen>>() {
                    @Override
                    public Publisher<LoanOpen> apply(LoanOpenRequest request) throws Exception {
                        return Flowable.just(request).compose(new ResponseTransformer<LoanOpenRequest, LoanOpen>("loanOpen"));
                    }
                });
    }
}
