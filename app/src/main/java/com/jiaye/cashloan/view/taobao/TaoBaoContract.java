package com.jiaye.cashloan.view.taobao;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * TaoBaoContract
 *
 * @author 贾博瑄
 */

public interface TaoBaoContract {

    interface View extends BaseViewContract {

        void exit();
    }

    interface Presenter extends BasePresenter {

        void requestTaoBao();
    }
}
