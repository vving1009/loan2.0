package com.jiaye.cashloan.view.data.loan.auth.source.sesame;

import android.database.Cursor;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.base.RequestContent;
import com.jiaye.cashloan.http.base.RequestHeader;
import com.jiaye.cashloan.http.data.loan.SesameRequest;
import com.jiaye.cashloan.persistence.DbContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * LoanAuthSesameRepository
 *
 * @author 贾博瑄
 */

public class LoanAuthSesameRepository implements LoanAuthSesameDataSource {

    @Override
    public Flowable<Request<SesameRequest>> sesame() {
        return Flowable.just("SELECT * FROM user;")
                .map(new Function<String, Request<SesameRequest>>() {
                    @Override
                    public Request<SesameRequest> apply(String sql) throws Exception {
                        Request<SesameRequest> request = new Request<>();
                        RequestContent<SesameRequest> content = new RequestContent<>();
                        List<SesameRequest> list = new ArrayList<>();
                        SesameRequest sesame = new SesameRequest();
                        Cursor cursor = LoanApplication.getInstance().getSQLiteDatabase().rawQuery(sql, null);
                        if (cursor != null) {
                            if (cursor.moveToNext()) {
                                String name = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_NAME));
                                String ocrID = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_ID));
                                sesame.setName(name);
                                sesame.setId(ocrID);
                            }
                            cursor.close();
                        }
                        list.add(sesame);
                        content.setBody(list);
                        RequestHeader header = RequestHeader.create();
                        header.setPhone("");
                        content.setHeader(header);
                        request.setContent(content);
                        return request;
                    }
                });
    }
}
