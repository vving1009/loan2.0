package com.jiaye.cashloan.view.phone;

import android.os.Bundle;
import android.text.TextUtils;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.MoxieFragment;
import com.jiaye.cashloan.view.phone.source.PhoneRepository;

/**
 * PhoneFragment
 *
 * @author 贾博瑄
 */

public class PhoneFragment extends MoxieFragment implements PhoneContract.View {

    private static final String TAG = "PhoneFragment";

    private PhoneContract.Presenter mPresenter;

    public static PhoneFragment newInstance() {
        Bundle args = new Bundle();
        PhoneFragment fragment = new PhoneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BasePresenter getPresenter() {
        mPresenter = new PhonePresenter(this, new PhoneRepository());
        return mPresenter;
    }

    @Override
    protected String getMoxieType() {
        return "carrier";
    }

    @Override
    protected String getMoxieParams() {
        String phoneNum = LoanApplication.getInstance().getDbHelper().queryUser().getPhone();
        phoneNum = !TextUtils.isEmpty(phoneNum) ? "\"phone\":\"" + phoneNum + "\"" : "";
        String userName = LoanApplication.getInstance().getDbHelper().queryUser().getName();
        userName = !TextUtils.isEmpty(userName) ? ",\"name\":\"" + userName + "\"" : "";
        String idCard = LoanApplication.getInstance().getDbHelper().queryUser().getId();
        idCard = !TextUtils.isEmpty(idCard) ? ",\"idcard\":\"" + idCard + "\"" : "";
        return "&loginParams={" + phoneNum + userName + idCard + "}";
    }

    @Override
    protected void notifyService() {
        mPresenter.requestUpdatePhone();
    }
}
