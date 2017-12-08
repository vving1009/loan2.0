package com.jiaye.cashloan.view.view.my.certificate.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.data.my.certificate.info.person.source.InfoPersonRepository;

/**
 * InfoPersonFragment
 *
 * @author 贾博瑄
 */

public class InfoPersonFragment extends BaseFragment implements InfoPersonContract.View {

    private InfoPersonContract.Presenter mPresenter;

    private TextView mTextEducation;

    private TextView mTextMarriage;

    private TextView mTextRegisterCity;

    private TextView mTextCity;

    private TextView mTextAddress;

    private TextView mTextEmail;

    public static InfoPersonFragment newInstance() {
        Bundle args = new Bundle();
        InfoPersonFragment fragment = new InfoPersonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_person_fragment,container,false);
        mTextEducation = view.findViewById(R.id.text_education);
        mTextMarriage = view.findViewById(R.id.text_marriage);
        mTextRegisterCity = view.findViewById(R.id.text_register_city);
        mTextCity = view.findViewById(R.id.text_city);
        mTextAddress = view.findViewById(R.id.text_address);
        mTextEmail = view.findViewById(R.id.text_email);
        mPresenter = new InfoPersonPresenter(this, new InfoPersonRepository());
        mPresenter.subscribe();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void setEducation(String education) {
        mTextEducation.setText(education);
    }

    @Override
    public void setMarriage(String marriage) {
        mTextMarriage.setText(marriage);
    }

    @Override
    public void setRegisterCity(String registerCity) {
        mTextRegisterCity.setText(registerCity);
    }

    @Override
    public void setCity(String city) {
        mTextCity.setText(city);
    }

    @Override
    public void setAddress(String address) {
        mTextAddress.setText(address);
    }

    @Override
    public void setEmail(String email) {
        mTextEmail.setText(email);
    }
}
