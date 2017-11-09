package com.jiaye.cashloan.view.view.loan;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseView;
import com.jiaye.cashloan.view.data.loan.LoanAuthModel;

import java.util.List;

/**
 * LoanAuthContract
 *
 * @author 贾博瑄
 */

public class LoanAuthContract {

    interface View extends BaseView<Presenter> {

        /**
         * 设置借款认证列表
         */
        void setList(List<LoanAuthModel> list);

        /**
         * 显示借款身份证认证页面
         */
        void startLoanAuthOCRView();

        /**
         * 显示借款活体认证页面
         */
        void startLoanAuthFaceView();

        /**
         * 显示借款个人资料认证页面
         */
        void startLoanAuthPersonView();

        /**
         * 显示借款手机认证页面
         */
        void startLoanAuthPhoneView();

        /**
         * 显示借款淘宝认证页面
         */
        void startLoanAuthTaoBaoView();

        /**
         * 显示借款芝麻信用认证页面
         */
        void startLoanAuthSesameView();
    }

    interface Presenter extends BasePresenter {

        /**
         * 请求认证信息
         */
        void requestLoanAuth();

        /**
         * 选择认证
         */
        void selectLoanAuthModel(LoanAuthModel model);
    }
}
