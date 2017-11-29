package com.jiaye.cashloan.view.data.loan.auth.source.face;

import com.jiaye.cashloan.http.data.loan.LoanFaceAuth;

import io.reactivex.Flowable;

/**
 * LoanAuthFaceDataSource
 *
 * @author 贾博瑄
 */

public interface LoanAuthFaceDataSource {

    /**
     * 上传图片
     */
    Flowable<LoanFaceAuth> upload(byte[] data);
}
