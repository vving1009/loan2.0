package com.jiaye.cashloan.view.view.loan;

import com.jiaye.cashloan.http.data.loan.SupportBankList;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

import java.util.List;

/**
 * LoanSupportBankContract
 *
 * @author 贾博瑄
 */

public interface LoanSupportBankContract {

    interface View extends BaseViewContract {

        void setList(List<SupportBankList.Data> list);
    }

    interface Presenter extends BasePresenter {
    }
}
