package com.jiaye.cashloan.view.view.my.credit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.my.CreditBalance;
import com.jiaye.cashloan.http.data.my.CreditPasswordRequest;
import com.jiaye.cashloan.http.data.my.CreditPasswordResetRequest;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.data.my.credit.source.CreditRepository;
import com.jiaye.cashloan.view.view.my.MyActivity;

/**
 * CreditFragment
 *
 * @author 贾博瑄
 */

public class CreditFragment extends BaseFragment implements CreditContract.View {

    private CreditContract.Presenter mPresenter;

    private TextView mTextPassword;

    private TextView mTextBalance;

    public static CreditFragment newInstance() {
        Bundle args = new Bundle();
        CreditFragment fragment = new CreditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.credit_fragment, container, false);
        mTextPassword = view.findViewById(R.id.text_password);
        mTextBalance = view.findViewById(R.id.text_balance);
        view.findViewById(R.id.layout_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.password();
            }
        });
        view.findViewById(R.id.layout_cash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.cash();
            }
        });
        mPresenter = new CreditPresenter(this, new CreditRepository());
        mPresenter.subscribe();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void notOpen() {
        showToastById(R.string.my_credit_not_open);
        getActivity().finish();
    }

    @Override
    public void setPasswordText(String text) {
        mTextPassword.setText(text);
    }

    @Override
    public void showPasswordView(CreditPasswordRequest request) {
        Intent intent = new Intent(getActivity(), CreditActivity.class);
        intent.putExtra("type", "password");
        intent.putExtra("request", request);
        startActivity(intent);
    }

    @Override
    public void showPasswordResetView(CreditPasswordResetRequest request) {
        Intent intent = new Intent(getActivity(), CreditActivity.class);
        intent.putExtra("type", "passwordReset");
        intent.putExtra("request", request);
        startActivity(intent);
    }

    @Override
    public void showCashView(CreditBalance balance) {
        Intent intent = new Intent(getActivity(), MyActivity.class);
        intent.putExtra("view", "credit_cash");
        intent.putExtra("balance", balance);
        startActivity(intent);
    }

    @Override
    public void showBalance(String balance) {
        mTextBalance.setText(balance);
    }
}
