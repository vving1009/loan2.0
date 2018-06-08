package com.jiaye.cashloan.view.info.source;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.loan.Person;
import com.jiaye.cashloan.http.data.loan.PersonRequest;
import com.jiaye.cashloan.http.data.loan.SavePerson;
import com.jiaye.cashloan.http.data.loan.SavePersonRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;

import io.reactivex.Flowable;

/**
 * PersonalRepository
 *
 * @author 贾博瑄
 */

public class PersonalRepository implements PersonalDataSource {

    @Override
    public Flowable<Person> requestPerson() {
        return Flowable.just(new PersonRequest())
                .map(request -> {
                    request.setLoanId(getLoanId());
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<PersonRequest, Person>("person"));
    }

    @Override
    public Flowable<SavePerson> requestSavePerson(SavePersonRequest request) {
        return Flowable.just(request)
                .map(request1 -> {
                    request1.setLoanId(getLoanId());
                    return request1;
                })
                .compose(new SatcatcheResponseTransformer<SavePersonRequest, SavePerson>("savePerson"));
    }

    private String getLoanId() {
        String loanId = "";
        SQLiteDatabase database = LoanApplication.getInstance().getSQLiteDatabase();
        Cursor cursor = database.rawQuery("SELECT loan_id FROM user;", null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                loanId = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_LOAN_ID));
            }
            cursor.close();
        }
        return loanId;
    }
}
