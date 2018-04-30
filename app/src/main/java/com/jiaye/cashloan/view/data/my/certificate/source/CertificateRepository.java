package com.jiaye.cashloan.view.data.my.certificate.source;

import android.database.Cursor;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.auth.Auth;
import com.jiaye.cashloan.http.data.auth.AuthRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * CertificateRepository
 *
 * @author 贾博瑄
 */

public class CertificateRepository implements CertificateDataSource {

    private Auth mAuth;

    @Override
    public Flowable<Auth> requestAuth() {
        return Flowable.just(new AuthRequest())
                .compose(new SatcatcheResponseTransformer<AuthRequest, Auth>("auth"))
                .map(new Function<Auth, Auth>() {
                    @Override
                    public Auth apply(Auth auth) throws Exception {
                        String phone = "";
                        Cursor cursor = LoanApplication.getInstance().getSQLiteDatabase().rawQuery("SELECT phone FROM user;", null);
                        if (cursor != null) {
                            if (cursor.moveToNext()) {
                                phone = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE));
                            }
                            cursor.close();
                        }
                        auth.setPhone(phone);
                        mAuth = auth;
                        return auth;
                    }
                });
    }

    @Override
    public Auth getAuth() {
        return mAuth;
    }
}
