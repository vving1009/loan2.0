package com.jiaye.cashloan.view.bindbank;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFunctionFragment;
import com.jiaye.cashloan.view.FunctionActivity;
import com.jiaye.cashloan.view.bindbank.source.BindBankRepository;
import com.jiaye.cashloan.widget.LoanEditText;
import com.jiaye.cashloan.widget.SatcatcheDialog;

import static android.app.Activity.RESULT_OK;

public class BindBankFragment extends BaseFunctionFragment implements BindBankContract.View {

    private BindBankContract.Presenter mPresenter;

    private TextView mTextName;

    private EditText mEditPhone;

    private EditText mEditBank;

    private EditText mEditNumber;

    private LoanEditText mEditSMS;

    public static BindBankFragment newInstance() {
        Bundle args = new Bundle();
        BindBankFragment fragment = new BindBankFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getTitleId() {
        return R.string.bind_bank_title;
    }

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        View rootView = inflater.inflate(R.layout.bind_bank_fragment, frameLayout, true);
        mTextName = rootView.findViewById(R.id.text_person);
        mEditPhone = rootView.findViewById(R.id.edit_phone);
        mEditBank = rootView.findViewById(R.id.edit_bank);
        mEditNumber = rootView.findViewById(R.id.edit_number);
        mEditSMS = rootView.findViewById(R.id.edit_sms_verification_code);
        rootView.findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.submit();
            }
        });
        mEditSMS.setOnClickVerificationCode(new LoanEditText.OnClickVerificationCode() {
            @Override
            public void onClickVerificationCode() {
                mPresenter.requestSMS();
            }
        });
        rootView.findViewById(R.id.text_support).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSupportBankView();
            }
        });
        mPresenter = new BindBankPresenter(this, new BindBankRepository());
        mPresenter.subscribe();
        //noinspection ConstantConditions
        mPresenter.setSource(getActivity().getIntent().getExtras().getString("source", ""));
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void setName(String text) {
        mTextName.setText(text);
    }

    @Override
    public String getPhone() {
        return mEditPhone.getText().toString();
    }

    @Override
    public String getBank() {
        return mEditBank.getText().toString();
    }

    @Override
    public String getNumber() {
        return mEditNumber.getText().toString();
    }

    @Override
    public String getSMS() {
        return mEditSMS.getText().toString();
    }

    @Override
    public void startCountDown() {
        mEditSMS.startCountDown();
    }

    @Override
    public void complete() {
        new SatcatcheDialog.Builder(getContext())
                .setTitle("提示")
                .setMessage(getString(R.string.my_bank_un_bind_complete))
                .setPositiveButton("确定", (dialog, i) -> {
                    dialog.dismiss();
                    getActivity().setResult(RESULT_OK);
                    getActivity().finish();
                })
                .build().show();
    }

    @Override
    public void result() {
        new SatcatcheDialog.Builder(getContext())
                .setTitle("提示")
                .setMessage(getString(R.string.my_bank_un_bind_complete))
                .setPositiveButton("确定", (dialog, i) -> {
                    dialog.dismiss();
                    getActivity().finish();
                })
                .build().show();
    }

    private void showSupportBankView() {
        FunctionActivity.function(getActivity(), "Support");
    }
}
