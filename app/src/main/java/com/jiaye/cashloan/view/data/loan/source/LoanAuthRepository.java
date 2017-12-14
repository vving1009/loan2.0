package com.jiaye.cashloan.view.data.loan.source;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.UploadContactClient;
import com.jiaye.cashloan.http.data.loan.LoanAuth;
import com.jiaye.cashloan.http.data.loan.LoanAuthRequest;
import com.jiaye.cashloan.http.data.loan.UploadContactRequest;
import com.jiaye.cashloan.http.data.loan.UploadContactResponse;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;

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
    public Flowable<UploadContactResponse> uploadContact() {
        UploadContactRequest request = new UploadContactRequest();
        /*phone*/
        Cursor cursorUser = LoanApplication.getInstance().getSQLiteDatabase().rawQuery("SELECT phone FROM user", null);
        if (cursorUser != null) {
            if (cursorUser.moveToNext()) {
                String phone = cursorUser.getString(cursorUser.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE));
                request.setPhone(phone);
            }
            cursorUser.close();
        }
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
        request.setContacts(mList);
        if (cur != null) {
            cur.close();
        }
        return UploadContactClient.INSTANCE.getService().uploadContact(request);
    }

    @Override
    public Flowable<LoanAuth> requestLoanAuth() {
        return Flowable.just("")
                .map(new Function<String, LoanAuthRequest>() {
                    @Override
                    public LoanAuthRequest apply(String s) throws Exception {
                        LoanAuthRequest request = new LoanAuthRequest();
                        Cursor cursorUser = LoanApplication.getInstance().getSQLiteDatabase().rawQuery("SELECT phone FROM user", null);
                        if (cursorUser != null) {
                            if (cursorUser.moveToNext()) {
                                String phone = cursorUser.getString(cursorUser.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE));
                                request.setPhone(phone);
                            }
                            cursorUser.close();
                        }
                        Cursor cursorProduct = LoanApplication.getInstance().getSQLiteDatabase().rawQuery("SELECT product_id FROM product;", null);
                        if (cursorProduct != null) {
                            if (cursorProduct.moveToNext()) {
                                String productId = cursorProduct.getString(cursorProduct.getColumnIndex(DbContract.Product.COLUMN_NAME_PRODUCT_ID));
                                request.setProductId(productId);
                            }
                            cursorProduct.close();
                        }
                        return request;
                    }
                })
                .compose(new ResponseTransformer<LoanAuthRequest, LoanAuth>("loanAuth"))
                .map(new Function<LoanAuth, LoanAuth>() {
                    @Override
                    public LoanAuth apply(LoanAuth loanAuth) throws Exception {
                        ContentValues values = new ContentValues();
                        values.put("loan_id", loanAuth.getLoanId());
                        values.put("ocr_name", loanAuth.getOcrName());
                        values.put("ocr_id", loanAuth.getOcrID());
                        LoanApplication.getInstance().getSQLiteDatabase().update("user", values, null, null);
                        return loanAuth;
                    }
                });
    }
}
