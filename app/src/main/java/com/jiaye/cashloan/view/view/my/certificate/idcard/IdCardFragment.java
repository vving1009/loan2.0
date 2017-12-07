package com.jiaye.cashloan.view.view.my.certificate.idcard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.data.my.certificate.idcard.source.IdCardRepository;

/**
 * IdCardFragment
 *
 * @author 贾博瑄
 */

public class IdCardFragment extends BaseFragment implements IdCardContract.View {

    private IdCardContract.Presenter mPresenter;

    private TextView mTextName;

    private TextView mTextNumber;

    private TextView mTextDate;

    public static IdCardFragment newInstance() {
        Bundle args = new Bundle();
        IdCardFragment fragment = new IdCardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.id_card_fragment, container, false);
        mTextName = view.findViewById(R.id.text_name);
        mTextNumber = view.findViewById(R.id.text_id_card);
        mTextDate = view.findViewById(R.id.text_date);
        mPresenter = new IdCardPresenter(this, new IdCardRepository());
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
    public void setNumber(String text) {
        mTextNumber.setText(text);
    }

    @Override
    public void setDate(String text) {
        mTextDate.setText(text);
    }
}
