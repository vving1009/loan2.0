package com.jiaye.cashloan.view.view.home.wish;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

import java.util.List;

/**
 * Created by guozihua on 2018/1/2.
 */

public class WishContract  {
    interface View extends BaseViewContract {
        /**
         * 跳转认证界面
         */


        /**
         * 品牌
         */
       String getBrand();

        /**
         * 产品型号
         */
        String getProductType();

        /**
         * 参考价格
         */
        String getReferencePrice();

        /**
         * 借款金额
         */
        String getLoanNumber();
    }

    interface Presenter extends BasePresenter {
        /**
         * 提交申请
         */
        void commit() ;

    }
}
