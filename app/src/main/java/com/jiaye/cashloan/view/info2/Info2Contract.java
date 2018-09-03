package com.jiaye.cashloan.view.info2;

import com.jiaye.cashloan.http.data.saveauth.SaveAuthRequest;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * Info2Contract
 *
 * @author 贾博瑄
 */

public interface Info2Contract {

    interface View extends BaseViewContract {

        void finish();

        void sendBroadcast();
    }

    interface Presenter extends BasePresenter {

        void submit(SaveAuthRequest request);
    }
}
