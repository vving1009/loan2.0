package com.jiaye.cashloan.view.view.my.credit.bank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.my.CreditInfo;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.data.my.credit.bank.source.CreditBankRepository;
import com.jiaye.cashloan.view.view.loan.LoanBindBankActivity;

/**
 * CreditBankFragment
 *
 * @author 贾博瑄
 */

public class CreditBankFragment extends BaseFragment implements CreditBankContract.View {

    private CreditBankContract.Presenter mPresenter;

    public static CreditBankFragment newInstance(boolean bind, CreditInfo creditInfo) {
        Bundle args = new Bundle();
        args.putBoolean("bind", bind);
        args.putParcelable("creditInfo", creditInfo);
        CreditBankFragment fragment = new CreditBankFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.credit_bank_fragment, container, false);
        RelativeLayout bindLayout = view.findViewById(R.id.layout_bind);
        RelativeLayout unBindLayout = view.findViewById(R.id.layout_un_bind);
        Button unBindBtn = view.findViewById(R.id.btn_un_bind);
        TextView textTips = view.findViewById(R.id.text_tips);
        boolean bind = getArguments().getBoolean("bind");
        if (bind) {
            bindLayout.setVisibility(View.VISIBLE);
            unBindLayout.setVisibility(View.GONE);
            unBindBtn.setVisibility(View.GONE);
            bindLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showBindBankView();
                }
            });
        } else {
            bindLayout.setVisibility(View.GONE);
            unBindLayout.setVisibility(View.VISIBLE);
            TextView textBank = view.findViewById(R.id.text_bank);
            TextView textName = view.findViewById(R.id.text_name);
            TextView textNumber = view.findViewById(R.id.text_number);
            CreditInfo creditInfo = getArguments().getParcelable("creditInfo");
            //noinspection ConstantConditions
            textBank.setText(creditInfo.getAccountName());
            String name = creditInfo.getName();
            int length = name.length();
            StringBuilder unKnown = new StringBuilder();
            for (int i = 0; i < length - 1; i++) {
                unKnown.append("*");
            }
            name = name.substring(0, 1) + unKnown;
            textName.setText(String.format(getString(R.string.my_credit_bank_name), name));
            String number = creditInfo.getBankNo();
            int numberLength = number.length();
            StringBuilder unKnownNumber = new StringBuilder();
            for (int i = 0; i < numberLength - 6; i++) {
                unKnownNumber.append("*");
            }
            number = number.substring(0, 2) + unKnownNumber + number.substring(number.length() - 4, number.length());
            textNumber.setText(String.format(getString(R.string.my_credit_bank_number), number));
            unBindBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.unBind();
                }
            });
        }
        SpannableString string = new SpannableString(textTips.getText());
        string.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_blue_dark)), textTips.getText().length() - 12, textTips.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textTips.setText(string);
        mPresenter = new CreditBankPresenter(this, new CreditBankRepository());
        mPresenter.subscribe();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void complete() {
        getActivity().finish();
    }

    private void showBindBankView() {
        Intent intent = new Intent(getActivity(), LoanBindBankActivity.class);
        intent.putExtra("source", "");
        startActivity(intent);
    }
}
