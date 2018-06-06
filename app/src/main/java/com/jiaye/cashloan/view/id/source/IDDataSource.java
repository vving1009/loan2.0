package com.jiaye.cashloan.view.id.source;

import com.jiaye.cashloan.http.data.loan.LoanIDCardAuth;
import com.jiaye.cashloan.http.tongdun.TongDunAntifraudRealName;
import com.jiaye.cashloan.http.tongdun.TongDunOCRBack;
import com.jiaye.cashloan.http.tongdun.TongDunOCRFront;

import io.reactivex.Flowable;

/**
 * IDDataSource
 *
 * @author 贾博瑄
 */

public interface IDDataSource {

    /**
     * 上传身份证正面照片并保存解析后的信息
     */
    Flowable<TongDunOCRFront> ocrFront(String path);

    /**
     * 上传身份证背面照片并保存解析后的信息
     */
    Flowable<TongDunOCRBack> ocrBack(String path);

    /**
     * 检验信息
     */
    Flowable<TongDunAntifraudRealName> check(String id, String name);

    /**
     * 身份证认证
     */
    Flowable<LoanIDCardAuth> loanIDCardAuth();
}
