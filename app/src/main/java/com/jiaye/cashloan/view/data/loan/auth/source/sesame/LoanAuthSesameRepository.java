package com.jiaye.cashloan.view.data.loan.auth.source.sesame;

import android.database.Cursor;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.loan.Sesame;
import com.jiaye.cashloan.http.data.loan.SesameRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * LoanAuthSesameRepository
 *
 * @author 贾博瑄
 */

public class LoanAuthSesameRepository implements LoanAuthSesameDataSource {

    @Override
    public Flowable<Sesame> sesame() {
        return Flowable.just("")
                .map(new Function<String, SesameRequest>() {
                    @Override
                    public SesameRequest apply(String s) throws Exception {
                        SesameRequest request = new SesameRequest();
                        String sql = "SELECT * FROM user;";
                        Cursor cursor = LoanApplication.getInstance().getSQLiteDatabase().rawQuery(sql, null);
                        if (cursor != null) {
                            if (cursor.moveToNext()) {
                                String name = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_NAME));
                                String ocrID = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_ID));
                                request.setName(name);
                                request.setId(ocrID);
                            }
                            cursor.close();
                        }
                        return request;
                    }
                })
                .compose(new ResponseTransformer<SesameRequest, Sesame>("sesame"));
    }
}
