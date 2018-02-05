package com.jiaye.cashloan.view.view.loan;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * LoanContractContract
 *
 * @author 贾博瑄
 */

public class LoanContractContract {

    interface View extends BaseViewContract {

        void postUrl(String url, byte[] postData);

        void result();
    }

    interface Presenter extends BasePresenter {

        void setContractId(String contractId);

        void showContract();
    }
}
