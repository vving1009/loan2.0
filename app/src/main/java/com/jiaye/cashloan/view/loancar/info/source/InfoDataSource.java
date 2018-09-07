package com.jiaye.cashloan.view.loancar.info.source;

import android.net.Uri;

import com.jiaye.cashloan.http.data.loan.SaveContact;
import com.jiaye.cashloan.http.data.loan.SaveContactRequest;
import com.jiaye.cashloan.http.data.loan.SavePerson;
import com.jiaye.cashloan.http.data.loan.SavePersonRequest;
import com.jiaye.cashloan.http.data.loan.UploadContact;

import io.reactivex.Flowable;

/**
 * InfoDataSource
 *
 * @author 贾博瑄
 */
public interface InfoDataSource {

    Flowable<String> selectPhone(Uri uri);

    Flowable<UploadContact> uploadContact();

    Flowable<SaveContact> requestSaveContact(SaveContactRequest request);

    Flowable<SavePerson> requestSavePerson(SavePersonRequest request);
}
