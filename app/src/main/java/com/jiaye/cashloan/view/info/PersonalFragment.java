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
import com.jiaye.cashloan.http.data.dictionary.Education;
import com.jiaye.cashloan.http.data.dictionary.Marriage;
import com.jiaye.cashloan.view.BaseDialog;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.info.source.PersonalRepository;

import java.util.ArrayList;

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

    private OptionsPickerView mOptionsEducation;

    private OptionsPickerView mOptionsMarriage;

    private OptionsPickerView mOptionsRegisterCity;

    private OptionsPickerView mOptionsCity;

    private TextView mTextEducation;

    private TextView mTextMarriage;

    private TextView mTextRegisterCity;

    private TextView mTextCity;

    private EditText mEditAddress;

    private EditText mEditEmail;

    private BaseDialog mSaveDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.loan_auth_person_info_activity, container, false);
        mTextEducation = root.findViewById(R.id.text_education);
        mTextMarriage = root.findViewById(R.id.text_marriage);
        mTextRegisterCity = root.findViewById(R.id.text_register_city);
        mTextCity = root.findViewById(R.id.text_city);
        mEditAddress = root.findViewById(R.id.edit_address);
        mEditEmail = root.findViewById(R.id.edit_email);
        mSaveDialog = new BaseDialog(getActivity());
        root.findViewById(R.id.layout_education).setOnClickListener(v -> showEducationPicker());
        root.findViewById(R.id.layout_marriage).setOnClickListener(v -> showMarriagePicker());
        root.findViewById(R.id.layout_register_city).setOnClickListener(v -> showRegisterCityPicker());
        root.findViewById(R.id.layout_city).setOnClickListener(v -> showCityPicker());
        root.findViewById(R.id.btn_save).setOnClickListener(v -> mSaveDialog.show());
        root.findViewById(R.id.img_back).setOnClickListener(v -> getActivity().onBackPressed());
        mSaveDialog = new BaseDialog(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.save_dialog_layout, null);
        view.findViewById(R.id.text_save).setOnClickListener(v -> {
            mPresenter.submit();
            mSaveDialog.dismiss();
        });
        view.findViewById(R.id.text_cancel).setOnClickListener(v -> mSaveDialog.dismiss());
        mSaveDialog.setContentView(view);
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
        mEditAddress.setText(address);
    }

    @Override
    public void setEmail(String email) {
        mEditEmail.setText(email);
    }

    @Override
    public String getEducation() {
        return mTextEducation.getText().toString();
    }

    @Override
    public String getMarriage() {
        return mTextMarriage.getText().toString();
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
    public void initEducation(final ArrayList<Education> educations) {
        mOptionsEducation =
                new OptionsPickerView.Builder(getActivity(), (options1, options2, options3, v) -> {
                    for (int i = 0; i < educations.size(); i++) {
                        educations.get(i).setSelect(false);
                    }
                    educations.get(options1).setSelect(true);
                    mTextEducation.setText(educations.get(options1).getPickerViewText());
                }).setLayoutRes(R.layout.loan_auth_person_item, v -> {
                    TextView textView = v.findViewById(R.id.text_title);
                    textView.setText(R.string.loan_auth_person_education);
                    v.findViewById(R.id.btn_cancel).setOnClickListener(v1 -> mOptionsEducation.dismiss());
                    v.findViewById(R.id.btn_submit).setOnClickListener(v12 -> {
                        mOptionsEducation.returnData();
                        mOptionsEducation.dismiss();
                    });
                }).setDividerColor(getResources().getColor(R.color.color_blue)).isDialog(true).build();
        mOptionsEducation.setPicker(educations);
    }

    @Override
    public void initMarriage(final ArrayList<Marriage> marriages) {
        mOptionsMarriage =
                new OptionsPickerView.Builder(getActivity(), (options1, options2, options3, v) -> {
                    for (int i = 0; i < marriages.size(); i++) {
                        marriages.get(i).setSelect(false);
                    }
                    marriages.get(options1).setSelect(true);
                    mTextMarriage.setText(marriages.get(options1).getPickerViewText());
                }).setLayoutRes(R.layout.loan_auth_person_item, v -> {
                    TextView textView = v.findViewById(R.id.text_title);
                    textView.setText(R.string.loan_auth_person_marriage);
                    v.findViewById(R.id.btn_cancel).setOnClickListener(v1 -> mOptionsMarriage.dismiss());
                    v.findViewById(R.id.btn_submit).setOnClickListener(v12 -> {
                        mOptionsMarriage.returnData();
                        mOptionsMarriage.dismiss();
                    });
                }).setDividerColor(getResources().getColor(R.color.color_blue)).isDialog(true).build();
        mOptionsMarriage.setPicker(marriages);
    }

    @Override
    public void result() {
        getActivity().finish();
    }

    private void showEducationPicker() {
        mOptionsEducation.show();
    }

    private void showMarriagePicker() {
        mOptionsMarriage.show();
    }

    private void showRegisterCityPicker() {
        mOptionsRegisterCity.show();
    }

    private void showCityPicker() {
        mOptionsCity.show();
    }
}
