package com.jiaye.cashloan.view.view.home;

import com.jiaye.cashloan.http.data.home.Product;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

import java.util.List;

/**
 * HomeContract
 *
 * @author 贾博瑄
 */

public class HomeContract {

    interface View extends BaseViewContract {

        /**
         * 设置产品列表
         */
        void setList(List<Product> list);

        /**
         * 显示借款页面
         */
        void showLoanView();
    }

    interface Presenter extends BasePresenter {

        /**
         * 选择产品
         */
        void selectProduct(Product product);
    }
}
