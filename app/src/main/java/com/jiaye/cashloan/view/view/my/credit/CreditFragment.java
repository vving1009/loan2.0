package com.jiaye.cashloan.view.view.my.credit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.my.CreditCashRequest;
import com.jiaye.cashloan.http.data.my.CreditPasswordRequest;
import com.jiaye.cashloan.http.data.my.CreditPasswordResetRequest;
import com.jiaye.cashloan.view.BaseDialog;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.data.my.credit.source.CreditRepository;

/**
 * CreditFragment
 *
 * @author 贾博瑄
 */

public class CreditFragment extends BaseFragment implements CreditContract.View {

    private CreditContract.Presenter mPresenter;

    private TextView mTextPassword;

    private BaseDialog mBalanceDialog;

    private TextView mTextAvail;

    private TextView mTextFreeze;

    private TextView mTextCurr;

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
        view.findViewById(R.id.layout_balance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.balance();
            }
        });
        mBalanceDialog = new BaseDialog(getActivity());
        View balanceView = LayoutInflater.from(getActivity()).inflate(R.layout.balance_dialog_layout, null);
        balanceView.findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBalanceDialog.dismiss();
            }
        });
        mTextAvail = balanceView.findViewById(R.id.text_avail);
        mTextFreeze = balanceView.findViewById(R.id.text_freeze);
        mTextCurr = balanceView.findViewById(R.id.text_curr);
        mBalanceDialog.setContentView(balanceView);
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
    public void showCashView(CreditCashRequest request) {
        Intent intent = new Intent(getActivity(), CreditActivity.class);
        intent.putExtra("type", "cash");
        intent.putExtra("request", request);
        startActivity(intent);
    }

    @Override
    public void showBalance(String availBal, String freezeBal, String currBal) {
        mTextAvail.setText(String.format(getString(R.string.my_credit_balance_avail), availBal));
        mTextFreeze.setText(String.format(getString(R.string.my_credit_balance_freeze), freezeBal));
        mTextCurr.setText(String.format(getString(R.string.my_credit_balance_curr), currBal));
        mBalanceDialog.show();
    }
}
