package com.jiaye.loan.cashloan.view;

/**
 * BaseViewContract
 *
 * @author 贾博瑄
 */

public interface BaseViewContract {

    void showToast(String string);

    void showToastById(int resId);

    void showProgressDialog();
}
