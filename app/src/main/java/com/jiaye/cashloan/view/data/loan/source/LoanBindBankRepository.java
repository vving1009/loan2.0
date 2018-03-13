package com.jiaye.cashloan.view.data.loan.source;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.loan.LoanBindBank;
import com.jiaye.cashloan.http.data.loan.LoanBindBankRequest;
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
        request.setCode(mCode);
        return Flowable.just(request)
                .compose(new ResponseTransformer<LoanBindBankRequest, LoanBindBank>("loanBindBank"));
    }

    @Override
    public Flowable<LoanBindBank> requestBindBankAgain(LoanBindBankRequest request) {
        request.setCode(mCode);
        return Flowable.just(request)
                .compose(new ResponseTransformer<LoanBindBankRequest, LoanBindBank>("bindBankAgain"));
    }
}
