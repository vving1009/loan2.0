package com.jiaye.cashloan.http;

import com.jiaye.cashloan.http.data.loan.UploadContactRequest;
import com.jiaye.cashloan.http.data.loan.UploadContactResponse;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * UploadContactService
 *
 * @author 贾博瑄
 */

public interface UploadContactService {

    @POST("phoneContacts")
    Flowable<UploadContactResponse> uploadContact(@Body UploadContactRequest request);
}
