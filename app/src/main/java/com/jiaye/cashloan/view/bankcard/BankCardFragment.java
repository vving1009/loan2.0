package com.jiaye.cashloan.view.bankcard;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFunctionFragment;
import com.jiaye.cashloan.view.account.AccountWebActivity;
import com.jiaye.cashloan.view.bankcard.source.BankCardRepository;

/**
 * BankCardFragment
 *
 * @author 贾博瑄
 */

public class BankCardFragment extends BaseFunctionFragment implements BankCardContract.View {

    private static final int REQUEST_CODE_BANK = 101;

    private BankCardContract.Presenter mPresenter;

    private RelativeLayout bindLayout;

    private RelativeLayout unBindLayout;

    private Button unBindBtn;

    private TextView textBank;

    private TextView textName;

    private TextView textNumber;

    private TextView textTips;

    public static BankCardFragment newInstance() {
        Bundle args = new Bundle();
        BankCardFragment fragment = new BankCardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_BANK && resultCode == Activity.RESULT_OK) {
            getActivity().finish();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.init();
    }

    private void goToBindCard() {
        Intent intent = new Intent(getActivity(), AccountWebActivity.class);
        intent.putExtra("type", "bindCard");
        startActivity(intent);
    }

    private void goToUnbindCard() {
        Intent intent = new Intent(getActivity(), AccountWebActivity.class);
        intent.putExtra("type", "unbind");
        startActivity(intent);
    }

    @Override
    protected int getTitleId() {
        return R.string.bank_card_title;
    }

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        View view = inflater.inflate(R.layout.bank_card_fragment, frameLayout, true);
        bindLayout = view.findViewById(R.id.layout_bind);
        unBindLayout = view.findViewById(R.id.layout_un_bind);
        unBindBtn = view.findViewById(R.id.btn_un_bind);
        textBank = view.findViewById(R.id.text_bank);
        textName = view.findViewById(R.id.text_name);
        textNumber = view.findViewById(R.id.text_number);
        textTips = view.findViewById(R.id.text_tips);

        bindLayout.setOnClickListener(v -> goToBindCard());
        unBindBtn.setOnClickListener(v -> goToUnbindCard());

        SpannableString string = new SpannableString(textTips.getText());
        string.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_blue)),
                textTips.getText().length() - 12, textTips.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        string.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:400–8780–777"));
                startActivity(intent);
            }
        }, textTips.getText().length() - 12, textTips.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textTips.setText(string);
        textTips.setMovementMethod(LinkMovementMethod.getInstance());

        mPresenter = new BankCardPresenter(this, new BankCardRepository());
        mPresenter.subscribe();
        return view;
    }

    @Override
    public void showBindCardView() {
        bindLayout.setVisibility(View.VISIBLE);
        unBindLayout.setVisibility(View.GONE);
        unBindBtn.setVisibility(View.GONE);
        textTips.setVisibility(View.VISIBLE);
    }

    @Override
    public void showUnbindCardView(String account, String name, String bankNo) {
        bindLayout.setVisibility(View.GONE);
        unBindLayout.setVisibility(View.VISIBLE);
        unBindBtn.setVisibility(View.VISIBLE);
        textTips.setVisibility(View.VISIBLE);

        //noinspection ConstantConditions
        textBank.setText(account);
        int length = name.length();
        StringBuilder unKnown = new StringBuilder();
        for (int i = 0; i < length - 1; i++) {
            unKnown.append("*");
        }
        name = unKnown + name.substring(name.length() - 1, name.length());
        textName.setText(String.format(getString(R.string.my_credit_bank_name), name));
        int numberLength = bankNo.length();
        if (numberLength > 7) {
            StringBuilder unKnownNumber = new StringBuilder();
            for (int i = 0; i < numberLength - 7; i++) {
                unKnownNumber.append("*");
            }
            bankNo = bankNo.substring(0, 3) + unKnownNumber + bankNo.substring(bankNo.length() - 4, bankNo.length());
            textNumber.setText(String.format(getString(R.string.my_credit_bank_number), bankNo));
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textTips.getLayoutParams();
        int density = (int) getResources().getDisplayMetrics().density;
        params.setMargins(31 * density, 33 * density, 0, 0);
        textTips.setLayoutParams(params);
    }
}
