package com.jiaye.cashloan.view.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.dictionary.Area;
import com.jiaye.cashloan.http.data.loan.SavePerson;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.info.source.PersonalRepository;

import java.util.ArrayList;

import io.reactivex.Flowable;

/**
 * PersonalFragment
 *
 * @author 贾博瑄
 */
public class PersonalFragment extends BaseFragment implements PersonalContract.View {

    public static PersonalFragment newInstance() {
        Bundle args = new Bundle();
        PersonalFragment fragment = new PersonalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private PersonalContract.Presenter mPresenter;

    private OptionsPickerView mOptionsRegisterCity;

    private OptionsPickerView mOptionsCity;

    private TextView mTextRegisterCity;

    private TextView mTextCity;

    private EditText mEditAddress;

    private EditText mEditEmail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.person_fragment, container, false);
        mTextRegisterCity = root.findViewById(R.id.text_register_city);
        mTextCity = root.findViewById(R.id.text_city);
        mEditAddress = root.findViewById(R.id.edit_address);
        mEditEmail = root.findViewById(R.id.edit_email);
        root.findViewById(R.id.layout_register_city).setOnClickListener(v -> showRegisterCityPicker());
        root.findViewById(R.id.layout_city).setOnClickListener(v -> showCityPicker());
        mPresenter = new PersonalPresenter(this, new PersonalRepository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public String getRegisterCity() {
        return mTextRegisterCity.getText().toString();
    }

    @Override
    public String getCity() {
        return mTextCity.getText().toString();
    }

    @Override
    public String getAddress() {
        return mEditAddress.getText().toString();
    }

    @Override
    public String getEmail() {
        return mEditEmail.getText().toString();
    }

    @Override
    public void initArea(final ArrayList<Area> areas, final ArrayList<ArrayList<String>> areas2, final ArrayList<ArrayList<ArrayList<String>>> areas3) {
        mOptionsRegisterCity =
                new OptionsPickerView.Builder(getActivity(), (options1, options2, options3, v) -> mTextRegisterCity.setText(areas.get(options1).getName() + "|" + areas2.get(options1).get(options2) + "|" + areas3.get(options1).get(options2).get(options3))).setLayoutRes(R.layout.loan_auth_person_item, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView textView = v.findViewById(R.id.text_title);
                        textView.setText(R.string.loan_auth_person_register_city);
                        v.findViewById(R.id.btn_cancel).setOnClickListener(v1 -> mOptionsRegisterCity.dismiss());
                        v.findViewById(R.id.btn_submit).setOnClickListener(v12 -> {
                            mOptionsRegisterCity.returnData();
                            mOptionsRegisterCity.dismiss();
                        });
                    }
                }).setDividerColor(getResources().getColor(R.color.color_blue)).isDialog(true).build();
        mOptionsRegisterCity.setPicker(areas, areas2, areas3);

        mOptionsCity =
                new OptionsPickerView.Builder(getActivity(), (options1, options2, options3, v) -> mTextCity.setText(areas.get(options1).getName() + "|" + areas2.get(options1).get(options2) + "|" + areas3.get(options1).get(options2).get(options3))).setLayoutRes(R.layout.loan_auth_person_item, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView textView = v.findViewById(R.id.text_title);
                        textView.setText(R.string.loan_auth_person_city);
                        v.findViewById(R.id.btn_cancel).setOnClickListener(v13 -> mOptionsCity.dismiss());
                        v.findViewById(R.id.btn_submit).setOnClickListener(v14 -> {
                            mOptionsCity.returnData();
                            mOptionsCity.dismiss();
                        });
                    }
                }).setDividerColor(getResources().getColor(R.color.color_blue)).isDialog(true).build();
        mOptionsCity.setPicker(areas, areas2, areas3);
    }

    @Override
    public void result() {
        getActivity().finish();
    }

    public Flowable<Boolean> canSubmit() {
        return mPresenter.canSubmit();
    }

    public Flowable<SavePerson> submit() {
        return mPresenter.submit();
    }

    private void showRegisterCityPicker() {
        mOptionsRegisterCity.show();
    }

    private void showCityPicker() {
        mOptionsCity.show();
    }
}
