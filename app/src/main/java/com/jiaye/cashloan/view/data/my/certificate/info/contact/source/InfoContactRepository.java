package com.jiaye.cashloan.view.data.my.certificate.info.contact.source;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.loan.Contact;
import com.jiaye.cashloan.http.data.loan.ContactRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * InfoContactRepository
 *
 * @author 贾博瑄
 */

public class InfoContactRepository implements InfoContactDataSource {

    @Override
    public Flowable<Contact> requestContact() {
        return Flowable.just(new ContactRequest())
                .map(new Function<ContactRequest, ContactRequest>() {
                    @Override
                    public ContactRequest apply(ContactRequest request) throws Exception {
                        request.setLoanId(getLoanId());
                        return request;
                    }
                })
                .compose(new SatcatcheResponseTransformer<ContactRequest, Contact>
                        ("contact"));
    }

    private String getLoanId() {
        String phone = "";
        SQLiteDatabase database = LoanApplication.getInstance().getSQLiteDatabase();
        Cursor cursor = database.rawQuery("SELECT loan_id FROM user;", null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                phone = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_LOAN_ID));
            }
            cursor.close();
        }
        return phone;
    }
}
