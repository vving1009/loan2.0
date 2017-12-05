package com.jiaye.cashloan.view.view.loan;

import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.BaseViewContract;

/**
 * LoanConfirmContract
 *
 * @author 贾博瑄
 */

public interface LoanConfirmContract {

    interface View extends BaseViewContract {

        void setLoan(String text);

        void setService(String text);

        void setConsult(String text);

        void setInterest(String text);

        void setDeadline(String text);

        void setPaymentMethod(String text);

        void setAmount(String text);

        void showLoanProgressView(String loanId);
    }

    interface Presenter extends BasePresenter {

        void confirm();
    }
}
