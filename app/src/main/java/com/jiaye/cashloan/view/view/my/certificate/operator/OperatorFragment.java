package com.jiaye.cashloan.view.view.my.certificate.operator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.data.my.certificate.operator.source.OperatorRepository;

/**
 * OperatorFragment
 *
 * @author 贾博瑄
 */

public class OperatorFragment extends BaseFragment implements OperatorContract.View {

    private OperatorContract.Presenter mPresenter;

    private TextView mTextPhone;

    private TextView mTextOperators;

    public static OperatorFragment newInstance() {
        Bundle args = new Bundle();
        OperatorFragment fragment = new OperatorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.operator_fragment, container, false);
        mTextPhone = view.findViewById(R.id.text_phone);
        mTextOperators = view.findViewById(R.id.text_operator);
        mPresenter = new OperatorPresenter(this, new OperatorRepository());
        mPresenter.subscribe();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPhone(String text) {
        mTextPhone.setText(text);
    }

    @Override
    public void setOperators(String text) {
        mTextOperators.setText(text);
    }
}
