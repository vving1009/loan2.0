package com.jiaye.cashloan.http;

import com.jiaye.cashloan.http.data.launch.CheckUpdate;
import com.jiaye.cashloan.http.data.launch.CheckUpdateRequest;
import com.jiaye.cashloan.http.data.loan.UploadContactRequest;
import com.jiaye.cashloan.http.data.loan.UploadContact;
import com.jiaye.cashloan.http.data.loan.UploadLocation;
import com.jiaye.cashloan.http.data.loan.UploadLocationRequest;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * UploadService
 *
 * @author 贾博瑄
 */

public interface UploadService {

    @POST("phoneContacts")
    Flowable<UploadContact> uploadContact(@Body UploadContactRequest request);

    @POST("phoneLocation")
    Flowable<UploadLocation> uploadLocation(@Body UploadLocationRequest request);

    @POST("getversion")
    Flowable<CheckUpdate> checkUpdate(@Body CheckUpdateRequest request);
}
