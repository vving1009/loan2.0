package com.jiaye.cashloan.http;

import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.data.auth.UploadSesameRequest;
import com.jiaye.cashloan.http.data.launch.CheckUpdate;
import com.jiaye.cashloan.http.data.launch.CheckUpdateRequest;
import com.jiaye.cashloan.http.data.loan.Upload;
import com.jiaye.cashloan.http.data.loan.UploadAuthRequest;
import com.jiaye.cashloan.http.data.loan.UploadBioAssayRequest;
import com.jiaye.cashloan.http.data.loan.UploadContact;
import com.jiaye.cashloan.http.data.loan.UploadContactRequest;
import com.jiaye.cashloan.http.data.loan.UploadLinkmanRequest;
import com.jiaye.cashloan.http.data.loan.UploadLocation;
import com.jiaye.cashloan.http.data.loan.UploadLocationRequest;
import com.jiaye.cashloan.http.data.loan.UploadOperatorRequest;
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

    @POST("getversion")
    Flowable<CheckUpdate> checkUpdate(@Body CheckUpdateRequest request);

    /*活体检测*/
    @POST("bioAssay")
    Flowable<Upload> uploadBioAssay(@Body Request<UploadBioAssayRequest> request);

    /*身份证认证*/
    @POST("cardAuth")
    Flowable<Upload> uploadAuth(@Body Request<UploadAuthRequest> request);

    /*手机运营商认证*/
    @POST("phoneOperator")
    Flowable<Upload> uploadOperator(@Body Request<UploadOperatorRequest> request);

    /*淘宝支付宝*/
    @POST("taobao")
    Flowable<Upload> uploadTaoBao(@Body Request<UploadTaoBaoRequest> request);

    /*联系人信息*/
    @POST("linkman")
    Flowable<Upload> uploadLinkman(@Body Request<UploadLinkmanRequest> request);

    /*个人信息*/
    @POST("personalInfo/save")
    Flowable<Upload> uploadPersonal(@Body Request<UploadPersonalRequest> request);

    /*芝麻信用*/
    @POST("sesame")
    Flowable<Upload> uploadSesame(@Body Request<UploadSesameRequest> request);
}
