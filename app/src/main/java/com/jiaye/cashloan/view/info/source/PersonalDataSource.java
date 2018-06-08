package com.jiaye.cashloan.view.info.source;

import com.jiaye.cashloan.http.data.loan.Person;
import com.jiaye.cashloan.http.data.loan.SavePerson;
import com.jiaye.cashloan.http.data.loan.SavePersonRequest;

import io.reactivex.Flowable;

/**
 * PersonalDataSource
 *
 * @author 贾博瑄
 */

public interface PersonalDataSource {

    Flowable<Person> requestPerson();

    Flowable<SavePerson> requestSavePerson(SavePersonRequest request);
}
