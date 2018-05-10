package com.jiaye.cashloan.view.data.loan.auth.source.info;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.loan.Contact;
import com.jiaye.cashloan.http.data.loan.ContactData;
import com.jiaye.cashloan.http.data.loan.ContactRequest;
import com.jiaye.cashloan.http.data.loan.SaveContact;
import com.jiaye.cashloan.http.data.loan.SaveContactRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * LoanAuthContactInfoRepository
 *
 * @author 贾博瑄
 */

public class LoanAuthContactInfoRepository implements LoanAuthContactInfoDataSource {

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
                        ("contact"))
                .filter(new Predicate<Contact>() {
                    @Override
                    public boolean test(Contact contact) throws Exception {
                        boolean pass = true;
                        if (contact.getData() == null) {
                            pass = false;
                        }
                        return pass;
                    }
                });
    }

    @Override
    public Flowable<SaveContact> requestSaveContact(SaveContactRequest request) {
        return Flowable.just(request)
                .map(new Function<SaveContactRequest, SaveContactRequest>() {
                    @Override
                    public SaveContactRequest apply(SaveContactRequest request) throws Exception {
                        request.setLoanId(getLoanId());
                        return request;
                    }
                })
                .compose(new SatcatcheResponseTransformer<SaveContactRequest, SaveContact>
                        ("saveContact"));
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
