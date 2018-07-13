package com.jiaye.cashloan.view.insurance
        ;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.BasePresenter;
import com.jiaye.cashloan.view.MoxieFragment;
import com.jiaye.cashloan.view.insurance.source.InsuranceRepository;
import com.jiaye.cashloan.view.phone.PhonePresenter;
import com.jiaye.cashloan.view.phone.source.PhoneRepository;

/**
 * InsuranceFragment
 *
 * @author 贾博瑄
 */

public class InsuranceFragment extends MoxieFragment implements InsuranceContract.View {

    private static final String TAG = "InsuranceFragment";

    private InsuranceContract.Presenter mPresenter;

    public static InsuranceFragment newInstance() {
        Bundle args = new Bundle();
        InsuranceFragment fragment = new InsuranceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BasePresenter getPresenter() {
        mPresenter = new InsurancePresenter(this, new InsuranceRepository());
        return mPresenter;
    }

    @Override
    protected String getMoxieType() {
        return "inslist";
    }

    @Override
    protected String getMoxieParams() {
        return "";
    }
}
