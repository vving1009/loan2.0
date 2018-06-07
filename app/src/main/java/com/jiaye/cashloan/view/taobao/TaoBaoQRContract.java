package com.jiaye.cashloan.view.taobao;

import android.graphics.Bitmap;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * TaoBaoQRContract
 *
 * @author 贾博瑄
 */

public interface TaoBaoQRContract {

    interface View extends BaseViewContract {

        void setImg(Bitmap bitmap);

        void showRpc();

        void result();
    }

    interface Presenter extends BasePresenter {

        void request();

        String getRpc();
    }
}
