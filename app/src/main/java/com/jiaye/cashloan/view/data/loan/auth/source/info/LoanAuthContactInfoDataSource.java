package com.jiaye.cashloan.view.data.loan.auth.source.info;

import com.jiaye.cashloan.http.data.loan.Contact;
import com.jiaye.cashloan.http.data.loan.SaveContact;
import com.jiaye.cashloan.http.data.loan.SaveContactRequest;

import io.reactivex.Flowable;

/**
 * LoanAuthContactInfoDataSource
 *
 * @author 贾博瑄
 */

public interface LoanAuthContactInfoDataSource {

    Flowable<Contact> requestContact();

    Flowable<SaveContact> requestSaveContact(SaveContactRequest request);
}
