package com.jiaye.cashloan.view.data.my.certificate.info.person.source;

import com.jiaye.cashloan.http.data.loan.Person;

import io.reactivex.Flowable;

/**
 * InfoPersonDataSource
 *
 * @author 贾博瑄
 */

public interface InfoPersonDataSource {

    Flowable<Person> requestPerson();
}
