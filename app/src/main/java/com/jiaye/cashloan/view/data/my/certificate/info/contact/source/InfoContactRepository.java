package com.jiaye.cashloan.view.data.my.certificate.info.contact.source;

import com.jiaye.cashloan.http.data.loan.Contact;
import com.jiaye.cashloan.http.data.loan.ContactRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;

import io.reactivex.Flowable;

/**
 * InfoContactRepository
 *
 * @author 贾博瑄
 */

public class InfoContactRepository implements InfoContactDataSource {

    @Override
    public Flowable<Contact> requestContact() {
        return Flowable.just(new ContactRequest())
                .compose(new ResponseTransformer<ContactRequest, Contact>("contact"));
    }
}
