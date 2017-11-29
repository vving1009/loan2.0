package com.jiaye.cashloan.view.data.loan.auth.source.info;

import com.jiaye.cashloan.http.data.loan.Contact;
import com.jiaye.cashloan.http.data.loan.ContactRequest;
import com.jiaye.cashloan.http.data.loan.SaveContact;
import com.jiaye.cashloan.http.data.loan.SaveContactRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;

import io.reactivex.Flowable;

/**
 * LoanAuthContactInfoRepository
 *
 * @author 贾博瑄
 */

public class LoanAuthContactInfoRepository implements LoanAuthContactInfoDataSource {

    @Override
    public Flowable<Contact> requestContact() {
        return Flowable.just(new ContactRequest())
                .compose(new ResponseTransformer<ContactRequest, Contact>("contact"));
    }

    @Override
    public Flowable<SaveContact> requestSaveContact(SaveContactRequest request) {
        return Flowable.just(request)
                .compose(new ResponseTransformer<SaveContactRequest, SaveContact>("saveContact"));
    }
}
