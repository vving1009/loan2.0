package com.jiaye.cashloan.view.view.loan;

import com.jiaye.cashloan.http.data.loan.ContractList;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * LoanContractListContract
 *
 * @author 贾博瑄
 */

public interface LoanContractListContract {

    interface View extends BaseViewContract {

        void setContracts(ContractList.Contract[] contracts);
    }

    interface Presenter extends BasePresenter {

        void requestContractList(String loanId);
    }
}
