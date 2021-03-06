package com.jiaye.cashloan.view.launch;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;
import com.satcatche.library.network.checkupgrade.bean.UpgradeResponse;

import java.io.File;

/**
 * LaunchContract
 *
 * @author 贾博瑄
 */

public interface LaunchContract {

    interface View extends BaseViewContract {

        /**
         * 显示主页面
         */
        void showMainView();

        /**
         * 显示升级页面
         */
        void showUpdateView(UpgradeResponse.Body data);

        /**
         * 设置进度
         */
        void setProgress(int progress);

        /**
         * 安装应用
         */
        void install(File file);
    }

    interface Presenter extends BasePresenter {

        /**
         * 下载
         */
        void download();

        /**
         * 自动执行后续逻辑
         */
        void auto();
    }
}
