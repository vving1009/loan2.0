package com.jiaye.cashloan.view.info.source;

import android.net.Uri;

import com.jiaye.cashloan.http.data.loan.SaveContact;
import com.jiaye.cashloan.http.data.loan.SaveContactRequest;
import com.jiaye.cashloan.http.data.loan.UploadContact;

import java.nio.channels.FileLock;

import io.reactivex.Flowable;

/**
 * ContactDataSource
 *
 * @author 贾博瑄
 */

public interface ContactDataSource {

    Flowable<String> selectPhone(Uri uri);

    Flowable<UploadContact> uploadContact();

    Flowable<SaveContact> requestSaveContact(SaveContactRequest request);
}
