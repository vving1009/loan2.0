package com.jiaye.cashloan.http;

import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.data.loan.Upload;
import com.jiaye.cashloan.http.data.loan.UploadContact;
import com.jiaye.cashloan.http.data.loan.UploadContactRequest;
import com.jiaye.cashloan.http.data.loan.UploadLinkmanRequest;
import com.jiaye.cashloan.http.data.loan.UploadLocation;
import com.jiaye.cashloan.http.data.loan.UploadLocationRequest;
import com.jiaye.cashloan.http.data.loan.UploadPersonalRequest;
import com.jiaye.cashloan.http.data.loan.UploadTaoBaoRequest;

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

    /*淘宝支付宝*/
    @POST("taobao")
    Flowable<Upload> uploadTaoBao(@Body Request<UploadTaoBaoRequest> request);

    /*联系人信息*/
    @POST("linkman")
    Flowable<Upload> uploadLinkman(@Body Request<UploadLinkmanRequest> request);

    /*个人信息*/
    @POST("personalInfo/save")
    Flowable<Upload> uploadPersonal(@Body Request<UploadPersonalRequest> request);
}
