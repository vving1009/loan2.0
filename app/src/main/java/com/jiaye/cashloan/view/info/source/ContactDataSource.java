package com.jiaye.cashloan.view.info.source;

import com.jiaye.cashloan.http.data.loan.Contact;
import com.jiaye.cashloan.http.data.loan.SaveContact;
import com.jiaye.cashloan.http.data.loan.SaveContactRequest;

import io.reactivex.Flowable;

/**
 * ContactDataSource
 *
 * @author 贾博瑄
 */

public interface ContactDataSource {

    Flowable<Contact> requestContact();

    Flowable<SaveContact> requestSaveContact(SaveContactRequest request);
}
