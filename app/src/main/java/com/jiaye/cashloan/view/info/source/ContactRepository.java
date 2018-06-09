package com.jiaye.cashloan.view.info.source;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.loan.SaveContact;
import com.jiaye.cashloan.http.data.loan.SaveContactRequest;
import com.jiaye.cashloan.http.data.loan.UploadContact;
import com.jiaye.cashloan.http.data.loan.UploadContactRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.view.LocalException;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * ContactRepository
 *
 * @author 贾博瑄
 */

public class ContactRepository implements ContactDataSource {

    @Override
    public Flowable<String> selectPhone(Uri uri) {
        return Flowable.just(uri)
                .map(contactData -> {
                    String phoneNumber = "";
                    Cursor cursor = LoanApplication.getInstance().getContentResolver()
                            .query(contactData, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        int phoneColumn = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
                        int phoneNum = cursor.getInt(phoneColumn);
                        if (phoneNum > 0) {
                            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                            String contactId = cursor.getString(idColumn);
                            Cursor phone = LoanApplication.getInstance().getContentResolver()
                                    .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                            null,
                                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                                            null,
                                            null);
                            if (phone != null && phone.moveToNext()) {
                                int index = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                                phoneNumber = phone.getString(index);
                                phone.close();
                            }
                        }
                        cursor.close();
                    } else {
                        throw new LocalException(R.string.error_auth_unknown_contact);
                    }
                    return phoneNumber;
                });
    }

    @Override
    public Flowable<UploadContact> uploadContact() {
        UploadContactRequest request = new UploadContactRequest();
        List<UploadContactRequest.Contact> mList = new ArrayList<>();
        ContentResolver cr = LoanApplication.getInstance().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cur == null) {
            return Flowable.error(new LocalException(R.string.error_auth_unknown_contact));
        } else {
            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
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
            cur.close();
            return Flowable.just(request).compose(new SatcatcheResponseTransformer<UploadContactRequest, UploadContact>("uploadContact"));
        }
    }

    @Override
    public Flowable<SaveContact> requestSaveContact(SaveContactRequest request) {
        return Flowable.just(request)
                .map(request1 -> {
                    String loanId = LoanApplication.getInstance().getDbHelper().queryUser().getLoanId();
                    request1.setLoanId(loanId);
                    return request1;
                })
                .compose(new SatcatcheResponseTransformer<SaveContactRequest, SaveContact>
                        ("saveContact"));
    }
}
