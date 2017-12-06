package com.jiaye.cashloan.view.data.loan.source;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.loan.LoanBindBank;
import com.jiaye.cashloan.http.data.loan.LoanBindBankRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * LoanBindBankRepository
 *
 * @author 贾博瑄
 */

public class LoanBindBankRepository implements LoanBindBankDataSource {

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
    public Flowable<LoanBindBank> requestBindBank(LoanBindBankRequest request) {
        return Flowable.just(request)
                .compose(new ResponseTransformer<LoanBindBankRequest, LoanBindBank>("loanBindBank"));
    }
}
