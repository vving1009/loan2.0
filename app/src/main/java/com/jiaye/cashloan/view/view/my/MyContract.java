package com.jiaye.cashloan.view.view.my;

import com.jiaye.cashloan.view.AuthView;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.data.auth.User;

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
         *
         * @param user 用户信息
         */
        void showMyCertificateView(User user);

        /**
         * 显示借款审批页面
         */
        void showApproveView();

        /**
         * 显示分享页面
         */
        void showShareView();
    }

    interface Presenter extends BasePresenter {

        /**
         * 点击我的认证
         */
        void onClickMyCertificate();

        /**
         * 审批
         */
        void approve();

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
