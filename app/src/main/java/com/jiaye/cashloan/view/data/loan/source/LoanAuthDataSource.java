package com.jiaye.cashloan.view.data.loan.source;

import com.jiaye.cashloan.http.data.loan.LoanAuth;
import com.jiaye.cashloan.http.data.loan.QueryUploadPhoto;
import com.jiaye.cashloan.http.data.loan.UploadContact;
import com.jiaye.cashloan.http.data.loan.UploadPhoto;

import java.io.File;

import io.reactivex.Flowable;

/**
 * LoanAuthDataSource
 *
 * @author 贾博瑄
 */

public interface LoanAuthDataSource {

    /**
     * 上传联系人
     */
    Flowable<UploadContact> uploadContact();

    /**
     * 请求借款认证信息并保存
     */
    Flowable<LoanAuth> requestLoanAuth();

    /**
     * 请求确认借款
     */
    Flowable<String> requestLoanConfirm();

    /**
     * 请求上传人像照片的数量
     */
    Flowable<QueryUploadPhoto> queryUploadPhoto();

    /**
     * 请求上传人像照片
     */
    Flowable<UploadPhoto> uploadPhoto(File file);
}
