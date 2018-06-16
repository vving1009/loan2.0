package com.jiaye.cashloan.view.sign;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * SignContract
 *
 * @author 贾博瑄
 */

public interface SignContract {

    interface View extends BaseViewContract {

        void postUrl(String url, byte[] postData);

        void hideBtn();
    }

    interface Presenter extends BasePresenter {

        void sign();
    }
}
