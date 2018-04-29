package com.jiaye.cashloan.view.data.loan.source;

import com.jiaye.cashloan.http.data.loan.FileState;
import com.jiaye.cashloan.http.data.loan.LoanAuth;
import com.jiaye.cashloan.http.data.loan.UploadContact;
import com.jiaye.cashloan.http.data.loan.UploadLocation;

import io.reactivex.Flowable;

/**
 * LoanAuthDataSource
 *
 * @author 贾博瑄
 */

public interface LoanAuthDataSource {

    /**
     * 设置产品编号
     */
    void setProductId(String productId);

    /**
     * 上传联系人
     */
    Flowable<UploadContact> uploadContact();

    /**
     * 上传地理位置
     */
    Flowable<UploadLocation> uploadLocation();

    /**
     * 请求进件上传的状态
     */
    Flowable<FileState> requestFileState();

    /**
     * 请求借款认证信息并保存
     */
    Flowable<LoanAuth> requestLoanAuth();

    /**
     * 请求确认借款
     */
    Flowable<String> requestLoanConfirm();
}
