package com.jiaye.cashloan.view.data.loan.source;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.loan.LoanAuth;
import com.jiaye.cashloan.http.data.loan.LoanAuthRequest;
import com.jiaye.cashloan.http.data.loan.LoanConfirm;
import com.jiaye.cashloan.http.data.loan.LoanConfirmRequest;
import com.jiaye.cashloan.http.data.loan.UploadContact;
import com.jiaye.cashloan.http.data.loan.UploadContactRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * LoanAuthRepository
 *
 * @author 贾博瑄
 */

public class LoanAuthRepository implements LoanAuthDataSource {

    @Override
    public Flowable<UploadContact> uploadContact() {
        UploadContactRequest request = new UploadContactRequest();
        /*contact*/
        List<UploadContactRequest.Contact> mList = new ArrayList<>();
        ContentResolver cr = LoanApplication.getInstance().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    if (pCur != null) {
                        while (pCur.moveToNext()) {
                            String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            UploadContactRequest.Contact contact = new UploadContactRequest.Contact();
                            contact.setName(name);
                            contact.setPhone(phoneNo);
                            mList.add(contact);
                        }
                        pCur.close();
                    }
                }
            }
        }
        request.setContacts(mList);
        if (cur != null) {
            cur.close();
        }
        return Flowable.just(request).compose(new SatcatcheResponseTransformer<UploadContactRequest, UploadContact>("uploadContact"));
    }

    @Override
    public Flowable<LoanAuth> requestLoanAuth() {
        return Flowable.just("")
                .map(new Function<String, LoanAuthRequest>() {
                    @Override
                    public LoanAuthRequest apply(String s) throws Exception {
                        LoanAuthRequest request = new LoanAuthRequest();
                        Cursor cursorUser = LoanApplication.getInstance().getSQLiteDatabase().rawQuery("SELECT phone, loan_id FROM user", null);
                        if (cursorUser != null) {
                            if (cursorUser.moveToNext()) {
                                String phone = cursorUser.getString(cursorUser.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE));
                                String loanId = cursorUser.getString(cursorUser.getColumnIndex(DbContract.User.COLUMN_NAME_LOAN_ID));
                                request.setPhone(phone);
                                request.setLoanId(loanId);
                            }
                            cursorUser.close();
                        }
                        return request;
                    }
                })
                .compose(new SatcatcheResponseTransformer<LoanAuthRequest, LoanAuth>("loanAuth"))
                .map(new Function<LoanAuth, LoanAuth>() {
                    @Override
                    public LoanAuth apply(LoanAuth loanAuth) throws Exception {
                        ContentValues values = new ContentValues();
                        values.put("ocr_name", loanAuth.getOcrName());
                        values.put("ocr_id", loanAuth.getOcrID());
                        LoanApplication.getInstance().getSQLiteDatabase().update("user", values, null, null);
                        return loanAuth;
                    }
                });
    }

    @Override
    public Flowable<String> requestLoanConfirm() {
        return Flowable.just("SELECT loan_id FROM user;")
                .map(new Function<String, LoanConfirmRequest>() {
                    @Override
                    public LoanConfirmRequest apply(String sql) throws Exception {
                        String loanId = "";
                        LoanConfirmRequest request = new LoanConfirmRequest();
                        SQLiteDatabase database = LoanApplication.getInstance().getSQLiteDatabase();
                        Cursor cursor = database.rawQuery(sql, null);
                        if (cursor != null) {
                            if (cursor.moveToNext()) {
                                loanId = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_LOAN_ID));
                            }
                            cursor.close();
                        }
                        request.setLoanId(loanId);
                        return request;
                    }
                })
                .compose(new SatcatcheResponseTransformer<LoanConfirmRequest, LoanConfirm>("loanConfirm"))
                .flatMap(new Function<LoanConfirm, Publisher<String>>() {
                    @Override
                    public Publisher<String> apply(LoanConfirm loanConfirm) throws Exception {
                        return queryLoanId();
                    }
                });
    }

    private Flowable<String> queryLoanId() {
        return Flowable.just("SELECT loan_id FROM user;")
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String sql) throws Exception {
                        String loanId = "";
                        SQLiteDatabase database = LoanApplication.getInstance().getSQLiteDatabase();
                        Cursor cursor = database.rawQuery(sql, null);
                        if (cursor != null) {
                            if (cursor.moveToNext()) {
                                loanId = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_LOAN_ID));
                            }
                            cursor.close();
                        }
                        return loanId;
                    }
                });
    }
}
