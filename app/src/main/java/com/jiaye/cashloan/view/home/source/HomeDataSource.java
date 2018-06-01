package com.jiaye.cashloan.view.home.source;

import com.jiaye.cashloan.http.data.home.BannerList;
import com.jiaye.cashloan.http.data.home.ProductList;
import com.jiaye.cashloan.http.data.loan.UploadRiskAppList;

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
     * 请求产品列表
     */
    Flowable<ProductList.Product[]> requestProductList();

    /**
     * 我要借款
     */
    Flowable<UploadRiskAppList> requestLoan(String productId);
}
