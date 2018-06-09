package com.jiaye.cashloan.view.view.my.credit;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.my.CreditBalance;
import com.jiaye.cashloan.http.data.my.CreditInfo;
import com.jiaye.cashloan.http.data.my.CreditPasswordRequest;
import com.jiaye.cashloan.http.data.my.CreditPasswordResetRequest;
import com.jiaye.cashloan.widget.BaseDialog;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.data.my.credit.source.CreditRepository;
import com.jiaye.cashloan.view.view.loan.LoanBindBankActivity;
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

    private TextView mTextAccountId;

    private BaseDialog mOpenDialog;

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
        mTextAccountId = view.findViewById(R.id.text_account_id);
        view.findViewById(R.id.layout_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.account();
            }
        });
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
        view.findViewById(R.id.layout_bank).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.bank();
            }
        });
        view.findViewById(R.id.text_copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copy();
            }
        });
        mOpenDialog = new BaseDialog(getActivity());
        View updateView = LayoutInflater.from(getActivity()).inflate(R.layout.credit_open_dialog_layout, null);
        updateView.findViewById(R.id.text_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOpenDialog.dismiss();
            }
        });
        updateView.findViewById(R.id.text_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.account();
                mOpenDialog.dismiss();
            }
        });
        mOpenDialog.setContentView(updateView);

        mPresenter = new CreditPresenter(this, new CreditRepository());
        mPresenter.subscribe();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPasswordText(String text) {
        mTextPassword.setText(text);
    }

    @Override
    public void showBindBankView() {
        Intent intent = new Intent(getActivity(), LoanBindBankActivity.class);
        intent.putExtra("source", "01");
        startActivity(intent);
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

    @Override
    public void showAccountId(String accountId) {
        mTextAccountId.setText(accountId);
    }

    @Override
    public void showOpenDialog() {
        mOpenDialog.show();
    }

    @Override
    public void showBankView(boolean bind, CreditInfo creditInfo) {
        Intent intent = new Intent(getActivity(), MyActivity.class);
        intent.putExtra("view", "credit_bank");
        intent.putExtra("bind", bind);
        intent.putExtra("creditInfo", creditInfo);
        startActivity(intent);
    }

    private void copy() {
        if (!TextUtils.isEmpty(mTextAccountId.getText().toString())) {
            ClipboardManager cmb = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(mTextAccountId.getText().toString().trim());
            showToastById(R.string.my_credit_copy_success);
        }
    }
}
