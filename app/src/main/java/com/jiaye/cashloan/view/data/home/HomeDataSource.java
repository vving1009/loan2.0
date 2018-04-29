package com.jiaye.cashloan.view.data.home;

import com.jiaye.cashloan.http.data.home.BannerList;
import com.jiaye.cashloan.http.data.loan.Upload;

import io.reactivex.Flowable;

/**
 * HomeDataSource
 *
 * @author 贾博瑄
 */

public interface HomeDataSource {

    /**
     * 请求广告列表
     */
    Flowable<BannerList.Banner[]> requestBannerList();

    /**
     * 上传身份证信息
     */
    Flowable<Upload> requestUpload();
}
