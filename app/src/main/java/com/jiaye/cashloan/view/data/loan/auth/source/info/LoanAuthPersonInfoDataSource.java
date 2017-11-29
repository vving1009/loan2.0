package com.jiaye.cashloan.view.data.loan.auth.source.info;

import com.jiaye.cashloan.http.data.loan.Person;
import com.jiaye.cashloan.http.data.loan.SavePerson;
import com.jiaye.cashloan.http.data.loan.SavePersonRequest;

import io.reactivex.Flowable;

/**
 * LoanAuthPersonInfoDataSource
 *
 * @author 贾博瑄
 */

public interface LoanAuthPersonInfoDataSource {

    Flowable<Person> requestPerson();

    Flowable<SavePerson> requestSavePerson(SavePersonRequest request);
}
