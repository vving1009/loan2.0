package com.jiaye.cashloan.view.view.my.certificate.bank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.data.my.certificate.bank.source.BankRepository;

/**
 * BankCardFragment
 *
 * @author 贾博瑄
 */

public class BankFragment extends BaseFragment implements BankContract.View {

    private BankContract.Presenter mPresenter;

    public static BankFragment newInstance() {
        Bundle args = new Bundle();
        BankFragment fragment = new BankFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private TextView mTextName;

    private TextView mTextPhone;

    private TextView mTextBank;

    private TextView mTextNumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bank_fragment, container, false);
        mTextName = view.findViewById(R.id.text_person);
        mTextPhone = view.findViewById(R.id.text_phone);
        mTextBank = view.findViewById(R.id.text_bank);
        mTextNumber = view.findViewById(R.id.text_number);
        mPresenter = new BankPresenter(this, new BankRepository());
        mPresenter.subscribe();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void setName(String text) {
        mTextName.setText(text);
    }

    @Override
    public void setPhone(String text) {
        mTextPhone.setText(text);
    }

    @Override
    public void setBank(String text) {
        mTextBank.setText(text);
    }

    @Override
    public void setNumber(String text) {
        mTextNumber.setText(text);
    }
}
