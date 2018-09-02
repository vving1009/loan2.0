package com.jiaye.cashloan.view.info2;

import com.jiaye.cashloan.http.data.saveauth.SaveAuthRequest;
import com.jiaye.cashloan.view.BaseViewContract;
import com.jiaye.cashloan.view.BasePresenter;

/**
 * Info2Contract
 *
 * @author 贾博瑄
 */

public interface Info2Contract {

    interface View extends BaseViewContract {

        void finish();
    }

    interface Presenter extends BasePresenter {

        void submit(SaveAuthRequest request);
    }
}
