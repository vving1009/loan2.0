package com.jiaye.cashloan.view.data.my.certificate.info.person.source;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.loan.Person;
import com.jiaye.cashloan.http.data.loan.PersonRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * InfoPersonRepository
 *
 * @author 贾博瑄
 */

public class InfoPersonRepository implements InfoPersonDataSource {

    @Override
    public Flowable<Person> requestPerson() {
        return Flowable.just("SELECT phone FROM user;")
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String sql) throws Exception {
                        return getString(sql);
                    }
                })
                .map(new Function<String, PersonRequest>() {
                    @Override
                    public PersonRequest apply(String phone) throws Exception {
                        PersonRequest request = new PersonRequest();
                        request.setPhone(phone);
                        return request;
                    }
                })
                .compose(new SatcatcheResponseTransformer<PersonRequest, Person>("person"));
    }

    private String getString(String sql) {
        String phone = "";
        SQLiteDatabase database = LoanApplication.getInstance().getSQLiteDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                phone = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE));
            }
            cursor.close();
        }
        return phone;
    }
}
