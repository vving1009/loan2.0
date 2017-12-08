package com.jiaye.cashloan.view.data.my.certificate.info.contact.source;

import com.jiaye.cashloan.http.data.loan.Contact;

import io.reactivex.Flowable;

/**
 * InfoContactDataSource
 *
 * @author 贾博瑄
 */

public interface InfoContactDataSource {

    Flowable<Contact> requestContact();
}
