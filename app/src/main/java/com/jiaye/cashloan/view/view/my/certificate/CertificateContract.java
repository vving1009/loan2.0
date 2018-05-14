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
         * 设置手机号
         */
        void setPhone(String text);

        /**
         * 设置身份证状态
         */
        void setOCRStatus(String text);

        /**
         * 设置信息状态
         */
        void setInfoStatus(String text);

        /**
         * 设置手机运营商状态
         */
        void setPhoneStatus(String text);

        /**
         * 设置淘宝状态
         */
        void setTaoBaoStatus(String text);

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
    }

    interface Presenter extends BasePresenter {

        void ocr();

        void info();

        void phone();
    }
}
