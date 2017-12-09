package com.jiaye.cashloan.view.view.my;

import com.jiaye.cashloan.http.data.my.User;
import com.jiaye.cashloan.view.AuthView;
import com.jiaye.cashloan.view.BasePresenter;

/**
 * MyContract
 *
 * @author 贾博瑄
 */

public class MyContract {

    interface View extends AuthView {

        /**
         * 显示用户信息
         *
         * @param user 用户信息
         */
        void showUserInfo(User user);

        /**
         * 显示我的认证页面
         */
        void showMyCertificateView();

        /**
         * 显示借款审批页面
         */
        void showApproveView(String loanId);

        /**
         * 显示放款还款页面
         */
        void showProgressView(String loanId);

        /**
         * 显示历史借款页面
         */
        void showHistoryView();

        /**
         * 显示设置页面
         */
        void showSettingsView();

        /**
         * 显示分享页面
         */
        void showShareView();
    }

    interface Presenter extends BasePresenter {

        /**
         * 请求用户信息
         */
        void requestUser();

        /**
         * 点击我的认证
         */
        void onClickMyCertificate();

        /**
         * 借款审批
         */
        void approve();

        /**
         * 放款还款
         */
        void progress();

        /**
         * 历史借款
         */
        void history();

        /**
         * 设置
         */
        void settings();

        /**
         * 分享
         */
        void share();

        /**
         * 分享到微信
         */
        void shareWeChat();

        /**
         * 分享到朋友圈
         */
        void shareMoments();
    }
}
