package com.jiaye.cashloan.view.view.my.credit.cash;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BasePresenterImpl;

/**
 * CreditCashPresenter
 *
 * @author 贾博瑄
 */

public class CreditCashPresenter extends BasePresenterImpl implements CreditCashContract.Presenter {

    private CreditCashContract.View mView;

    public CreditCashPresenter(CreditCashContract.View view) {
        mView = view;
    }

    @Override
    public void cash(String cash, String cashLimit, String bank) {
        if (TextUtils.isEmpty(cash) || cash.substring(0, 1).equals("0") || (cash.contains(".") && cash.substring(cash.indexOf(".")).length() > 3)) {
            mView.showToastById(R.string.error_my_credit_cash);
            return;
        }
        try {
            float num = Float.valueOf(cash);
            float numLimit = Float.valueOf(cashLimit);
            if (num <= 1) {
                mView.showToastById(R.string.error_my_credit_cash_min_limit);
                return;
            } else if (num > numLimit) {
                mView.showToastById(R.string.error_my_credit_cash_max_limit);
                return;
            } else if (num > 50000 && TextUtils.isEmpty(bank)) {
                mView.showToastById(R.string.error_my_credit_cash_max_limit_bank);
                return;
            }
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
            return;
        }
        mView.showCashView(cash, bank);
    }
}
