package com.jiaye.cashloan.view.account;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.my.CreditBalance;
import com.jiaye.cashloan.http.data.my.CreditInfo;
import com.jiaye.cashloan.http.data.my.CreditPasswordRequest;
import com.jiaye.cashloan.http.data.my.CreditPasswordResetRequest;
import com.jiaye.cashloan.view.BaseFunctionFragment;
import com.jiaye.cashloan.view.FunctionActivity;
import com.jiaye.cashloan.view.account.source.CreditRepository;
import com.jiaye.cashloan.widget.SatcatcheDialog;

/**
 * AccountFragment
 *
 * @author 贾博瑄
 */

public class AccountFragment extends BaseFunctionFragment implements AccountContract.View {

    private AccountContract.Presenter mPresenter;

    private TextView mTextPassword;

    private TextView mTextBalance;

    private TextView mTextAccountId;

    private SatcatcheDialog mOpenDialog;

    public static AccountFragment newInstance() {
        Bundle args = new Bundle();
        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(args);
        return fragment;
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
        Intent intent = new Intent(getActivity(), FunctionActivity.class);
        intent.putExtra("source", "01");
        intent.putExtra("function", "BindBank");
        startActivity(intent);
    }

    @Override
    public void showPasswordView(CreditPasswordRequest request) {
        Intent intent = new Intent(getActivity(), AccountWebActivity.class);
        intent.putExtra("type", "password");
        intent.putExtra("request", request);
        startActivity(intent);
    }

    @Override
    public void showPasswordResetView(CreditPasswordResetRequest request) {
        Intent intent = new Intent(getActivity(), AccountWebActivity.class);
        intent.putExtra("type", "passwordReset");
        intent.putExtra("request", request);
        startActivity(intent);
    }

    @Override
    public void showCashView(CreditBalance balance) {
        Intent intent = new Intent(getActivity(), FunctionActivity.class);
        intent.putExtra("balance", balance);
        intent.putExtra("function", "Cash");
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
        Intent intent = new Intent(getContext(), FunctionActivity.class);
        intent.putExtra("bind", bind);
        intent.putExtra("creditInfo", creditInfo);
        intent.putExtra("function", "BankCard");
        startActivity(intent);
    }

    private void copy() {
        if (!TextUtils.isEmpty(mTextAccountId.getText().toString())) {
            ClipboardManager cmb = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(mTextAccountId.getText().toString().trim());
            showToastById(R.string.my_credit_copy_success);
        }
    }

    @Override
    protected int getTitleId() {
        return R.string.account_title;
    }

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        View view = inflater.inflate(R.layout.account_fragment, frameLayout, true);
        mTextPassword = view.findViewById(R.id.text_password);
        mTextBalance = view.findViewById(R.id.text_balance);
        mTextAccountId = view.findViewById(R.id.text_account_id);
        view.findViewById(R.id.layout_account).setOnClickListener(v -> mPresenter.account());
        view.findViewById(R.id.layout_password).setOnClickListener(v -> mPresenter.password());
        view.findViewById(R.id.layout_cash).setOnClickListener(v -> mPresenter.cash());
        view.findViewById(R.id.layout_bank).setOnClickListener(v -> mPresenter.bank());
        view.findViewById(R.id.text_copy).setOnClickListener(v -> copy());
        mOpenDialog = new SatcatcheDialog.Builder(getContext())
                .setTitle("提示")
                .setMessage("尚未开通江西银行存管账户")
                .setPositiveButton("立即开通", ((dialog, i) -> {
                    mPresenter.account();
                    dialog.dismiss();
                }))
                .setNegativeButton("取消", ((dialog, i) -> dialog.dismiss()))
                .build();
        mPresenter = new AccountPresenter(this, new CreditRepository());
        mPresenter.subscribe();
        return view;
    }
}
