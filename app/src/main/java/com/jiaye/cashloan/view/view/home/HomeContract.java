package com.jiaye.cashloan.view.view.home;

import com.jiaye.cashloan.http.data.home.BannerList;
import com.jiaye.cashloan.http.data.home.ProductList;
import com.jiaye.cashloan.view.AuthView;
import com.jiaye.cashloan.view.BasePresenter;

/**
 * HomeContract
 *
 * @author 贾博瑄
 */
public interface HomeContract {

    interface View extends AuthView {

        /**
         * 设置广告
         */
        void setBanners(BannerList.Banner[] banners);

        /**
         * 设置产品
         */
        void setProduct(ProductList.Product[] products);

        /**
         * 显示借款认证页面
         */
        void showLoanAuthView(String productId);
    }

    interface Presenter extends BasePresenter {

        /**
         * 借款
         */
        void loan(String loanId);
    }
}
