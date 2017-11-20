package com.jiaye.cashloan.view.view.my.certificate;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * CertificateContract
 *
 * @author 贾博瑄
 */

public interface CertificateContract {

    interface View extends BaseViewContract {

        /**
         * 显示银行卡信息页面
         */
        void showBankView();

        /**
         * 显示身份证信息页面
         */
        void showIdCardView();

        /**
         * 显示个人信息页面
         */
        void showInfoView();

        /**
         * 显示运营商信息页面
         */
        void showOperatorView();

        /**
         * 显示淘宝信息页面
         */
        void showTaoBaoView();
    }

    interface Presenter extends BasePresenter {

    }
}
