package com.jiaye.cashloan.view.data.loan.auth.ocr;

import com.jiaye.cashloan.http.data.loan.LoanIDCardAuth;
import com.jiaye.cashloan.http.tongdun.TongDunOCRBack;
import com.jiaye.cashloan.http.tongdun.TongDunOCRFront;

import io.reactivex.Flowable;

/**
 * LoanAuthOCRDataSource
 *
 * @author 贾博瑄
 */

public interface LoanAuthOCRDataSource {

    /**
     * 上传身份证正面照片并保存解析后的信息
     */
    Flowable<TongDunOCRFront> ocrFront(String code, String key, String base64);

    /**
     * 上传身份证背面照片并保存解析后的信息
     */
    Flowable<TongDunOCRBack> ocrBack(String code, String key, String base64);

    /**
     * 身份证认证
     */
    Flowable<LoanIDCardAuth> loanIDCardAuth();

    /**
     * 身份证认证
     */
    Flowable<LoanIDCardAuth> loanIDCardAuth(String front, String back);
}
