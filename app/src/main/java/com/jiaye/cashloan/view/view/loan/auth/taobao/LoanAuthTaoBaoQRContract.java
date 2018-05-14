package com.jiaye.cashloan.view.view.loan.auth.taobao;

import android.graphics.Bitmap;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * LoanAuthTaoBaoQRContract
 *
 * @author 贾博瑄
 */

public interface LoanAuthTaoBaoQRContract {

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
