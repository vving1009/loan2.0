package com.jiaye.cashloan.view.view.loan;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.data.loan.LoanAuthModel;

import java.util.List;

/**
 * LoanAuthContract
 *
 * @author 贾博瑄
 */

public class LoanAuthContract {

    interface View extends BaseViewContract {

        /**
         * 设置借款认证列表
         */
        void setList(List<LoanAuthModel> list);

        /**
         * 设置下一步可用
         */
        void setNextEnable();

        /**
         * 显示借款身份证认证页面
         */
        void showLoanAuthOCRView();

        /**
         * 显示电子签章认证页面
         */
        void showLoanAuthVisaView();

        /**
         * 显示历史电子签章认证页面
         */
        void showLoanAuthVisaHistoryView();

        /**
         * 显示借款活体认证页面
         */
        void showLoanAuthFaceView();

        /**
         * 显示借款个人资料认证页面
         */
        void showLoanAuthInfoView();

        /**
         * 显示借款手机认证页面
         */
        void showLoanAuthPhoneView();

        /**
         * 显示借款淘宝认证页面
         */
        void showLoanAuthTaoBaoView();

        /**
         * 显示进件上传页面
         */
        void showLoanFileView();

        /**
         * 退出当前页面
         */
        void exitView();
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

        /**
         * 上传联系人
         */
        void uploadContact();

        /**
         * 确认
         */
        void confirm();

        /**
         * 上传人像照片
         */
        void uploadPhoto();
    }
}
