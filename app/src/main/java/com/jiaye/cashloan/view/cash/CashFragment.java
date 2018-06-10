package com.jiaye.cashloan.view.cash;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.my.CreditBalance;
import com.jiaye.cashloan.view.BaseFunctionFragment;
import com.jiaye.cashloan.view.account.AccountWebActivity;

/**
 * CashFragment
 *
 * @author 贾博瑄
 */

public class CashFragment extends BaseFunctionFragment implements CashContract.View {

    private CashContract.Presenter mPresenter;

    private EditText mEditCash;

    private EditText mEditBank;

    public static CashFragment newInstance(CreditBalance balance) {
        Bundle args = new Bundle();
        args.putParcelable("balance", balance);
        CashFragment fragment = new CashFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void showCashView(String cash, String bank) {
        Intent intent = new Intent(getActivity(), AccountWebActivity.class);
        intent.putExtra("type", "cash");
        intent.putExtra("cash", cash);
        intent.putExtra("bank", bank);
        startActivity(intent);
    }

    @Override
    protected int getTitleId() {
        return R.string.cash_title;
    }

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        final CreditBalance balance = getArguments().getParcelable("balance");
        View view = inflater.inflate(R.layout.cash_fragment, frameLayout, true);
        TextView textTitle = view.findViewById(R.id.text_title);
        TextView textCash = view.findViewById(R.id.text_cash);
        //预防服务器返回的数据不正确的情况
        try {
            textTitle.setText(String.format("%s\n%s", balance.getBankName(), balance.getBankNo().substring(balance.getBankNo().length() - 4)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            textCash.setText(String.format(getString(R.string.my_credit_cash_available_unit), balance.getAvailBal()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mEditCash = view.findViewById(R.id.edit_cash);
        mEditBank = view.findViewById(R.id.edit_bank);
        try {
            if (!TextUtils.isEmpty(balance.getBankCnapsno())) {
                mEditBank.setText(balance.getBankCnapsno());
                mEditBank.setEnabled(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        view.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.cash(mEditCash.getText().toString(), balance.getAvailBal(), mEditBank.getText().toString());
            }
        });
        mPresenter = new CashPresenter(this);
        mPresenter.subscribe();
        return view;
    }
}
