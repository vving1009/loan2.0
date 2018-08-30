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
import com.jiaye.cashloan.http.data.my.CreditInfo;
import com.jiaye.cashloan.view.BaseFunctionFragment;
import com.jiaye.cashloan.view.account.AccountWebActivity;
import com.jiaye.cashloan.view.bankcard.source.BankCardRepository;
import com.satcatche.library.widget.SatcatcheDialog;

/**
 * BankCardFragment
 *
 * @author 贾博瑄
 */

public class BankCardFragment extends BaseFunctionFragment implements BankCardContract.View {

    private static final int REQUEST_CODE_BANK = 101;

    private BankCardContract.Presenter mPresenter;

    private SatcatcheDialog.Builder mDialogBuilder;

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
    public void complete() {
        mDialogBuilder.setMessage(getString(R.string.my_bank_un_bind_complete))
                .setButtonNumber(0)
                .setCancelable(true)
                .setOnDismissListener(dialog -> getActivity().finish())
                .build().show();
    }

    @Override
    public void unBindFailedBalance() {
        mDialogBuilder.setMessage("余额不为0或存在未完成债权关系，暂不可解绑银行卡")
                .setPositiveButton("确定", null)
                .build().show();
    }

    @Override
    public void unBindFailedWrongCode() {
        mDialogBuilder.setMessage("解绑未成功\n错误代码：显示对应错误原因")
                .setButtonNumber(0)
                .setCancelable(true)
                .build().show();
    }

    private void showBindBankView() {
        Intent intent = new Intent(getActivity(), AccountWebActivity.class);
        intent.putExtra("type", "accountOpen");
        startActivity(intent);
    }

    @Override
    protected int getTitleId() {
        return R.string.bank_card_title;
    }

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        View view = inflater.inflate(R.layout.bank_card_fragment, frameLayout, true);
        RelativeLayout bindLayout = view.findViewById(R.id.layout_bind);
        RelativeLayout unBindLayout = view.findViewById(R.id.layout_un_bind);
        Button unBindBtn = view.findViewById(R.id.btn_un_bind);
        TextView textTips = view.findViewById(R.id.text_tips);
        boolean bind = getActivity().getIntent().getExtras().getBoolean("bind");
        if (bind) {
            bindLayout.setVisibility(View.VISIBLE);
            unBindLayout.setVisibility(View.GONE);
            unBindBtn.setVisibility(View.GONE);
            bindLayout.setOnClickListener(v -> showBindBankView());
        } else {
            bindLayout.setVisibility(View.GONE);
            unBindLayout.setVisibility(View.VISIBLE);
            TextView textBank = view.findViewById(R.id.text_bank);
            TextView textName = view.findViewById(R.id.text_name);
            TextView textNumber = view.findViewById(R.id.text_number);
            CreditInfo creditInfo = getActivity().getIntent().getExtras().getParcelable("creditInfo");
            //noinspection ConstantConditions
            textBank.setText(creditInfo.getAccountName());
            String name = creditInfo.getName();
            int length = name.length();
            StringBuilder unKnown = new StringBuilder();
            for (int i = 0; i < length - 1; i++) {
                unKnown.append("*");
            }
            name = unKnown + name.substring(name.length() - 1, name.length());
            textName.setText(String.format(getString(R.string.my_credit_bank_name), name));
            String number = creditInfo.getBankNo();
            int numberLength = number.length();
            if (numberLength > 7) {
                StringBuilder unKnownNumber = new StringBuilder();
                for (int i = 0; i < numberLength - 7; i++) {
                    unKnownNumber.append("*");
                }
                number = number.substring(0, 3) + unKnownNumber + number.substring(number.length() - 4, number.length());
                textNumber.setText(String.format(getString(R.string.my_credit_bank_number), number));
            }
            unBindBtn.setOnClickListener(v -> mPresenter.unBind());
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textTips.getLayoutParams();
            int density = (int) getResources().getDisplayMetrics().density;
            params.setMargins(31 * density, 33 * density, 0, 0);
            textTips.setLayoutParams(params);
        }
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
        mDialogBuilder = new SatcatcheDialog.Builder(getContext()).setTitle("提示");
        mPresenter = new BankCardPresenter(this, new BankCardRepository());
        mPresenter.subscribe();
        return view;
    }
}
