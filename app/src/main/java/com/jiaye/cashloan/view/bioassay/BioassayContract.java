package com.jiaye.cashloan.view.bioassay;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * BioassayContract
 *
 * @author 贾博瑄
 */

public interface BioassayContract {

    String FOLDER_FILE = "bioassay";

    interface View extends BaseViewContract {

        void result();
    }

    interface Presenter extends BasePresenter {

        void submit(byte[] verification);
    }
}
