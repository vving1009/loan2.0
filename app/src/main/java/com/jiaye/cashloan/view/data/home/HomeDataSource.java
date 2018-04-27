package com.jiaye.cashloan.view.data.home;

import com.jiaye.cashloan.http.data.loan.Upload;

import io.reactivex.Flowable;

/**
 * HomeDataSource
 *
 * @author 贾博瑄
 */

public interface HomeDataSource {

    /**
     * 上传身份证信息
     */
    Flowable<Upload> requestUpload();
}
