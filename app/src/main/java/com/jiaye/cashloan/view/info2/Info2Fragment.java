package com.jiaye.cashloan.view.info2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.saveauth.SaveAuthRequest;
import com.jiaye.cashloan.view.BaseFunctionFragment;
import com.jiaye.cashloan.view.certification.CertificationFragment;
import com.jiaye.cashloan.view.info2.source.Info2Repository;

import java.util.Arrays;
import java.util.List;

/**
 * Info2Fragment
 *
 * @author 贾博瑄
 */

public class Info2Fragment extends BaseFunctionFragment implements Info2Contract.View {

    private Info2Contract.Presenter mPresenter;

    private TextView mTextMarried;

    private TextView mTextEducation;

    private EditText mEditIndustry;

    private EditText mEditYears;

    private EditText mEditJob;

    private EditText mEditSalary;

    private TextView mTextHouse;

    private EditText mEditCreditCardNum;

    private EditText mEditCreditCardLimit;

    private EditText mEditDescription;

    private OptionsPickerView mPickerMarried;

    private OptionsPickerView mPickerEducation;

    private OptionsPickerView mPickerHouse;

    private List<String> marriedList;

    private List<String> educationList;

    private List<String> houseList;

    private SaveAuthRequest request;


    public static Info2Fragment newInstance() {
        Bundle args = new Bundle();
        Info2Fragment fragment = new Info2Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        View root = inflater.inflate(R.layout.info2_fragment, frameLayout, true);
        mTextMarried = root.findViewById(R.id.married);
        mTextEducation = root.findViewById(R.id.education);
        mEditIndustry = root.findViewById(R.id.industry);
        mEditYears = root.findViewById(R.id.years);
        mEditJob = root.findViewById(R.id.job);
        mEditSalary = root.findViewById(R.id.salary);
        mTextHouse = root.findViewById(R.id.house);
        mEditCreditCardNum = root.findViewById(R.id.credit_card_num);
        mEditCreditCardLimit = root.findViewById(R.id.credit_card_limit);
        mEditDescription = root.findViewById(R.id.description);
        mTextMarried.setOnClickListener(v -> mPickerMarried.show());
        mTextEducation.setOnClickListener(v -> mPickerEducation.show());
        mTextHouse.setOnClickListener(v -> mPickerHouse.show());
        request = new SaveAuthRequest();
        root.findViewById(R.id.btn_submit).setOnClickListener(v -> {
            request.setMarriageMsg(mTextMarried.getText().toString());
            request.setEduMsg(mTextEducation.getText().toString());
            request.setWorkIndustry(mEditIndustry.getText().toString());
            request.setWorkNumber(mEditYears.getText().toString());
            request.setWorkPost(mEditJob.getText().toString());
            request.setMonthlyIncome(mEditSalary.getText().toString());
            request.setHouseProperty(mTextHouse.getText().toString());
            request.setCreditCardNum(mEditCreditCardNum.getText().toString());
            request.setCreditCardQuota(mEditCreditCardLimit.getText().toString());
            request.setLoanDescribe(mEditDescription.getText().toString());
            mPresenter.submit(request);
        });
        initList();
        initPicker();

        mPresenter = new Info2Presenter(this, new Info2Repository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    private void initList() {
        marriedList = Arrays.asList(getResources().getStringArray(R.array.step3_info_married_array));
        educationList = Arrays.asList(getResources().getStringArray(R.array.step3_info_education_array));
        houseList = Arrays.asList(getResources().getStringArray(R.array.step3_info_house_array));
    }

    private void initPicker() {
        mPickerMarried =
                new OptionsPickerView.Builder(getActivity(), (options1, options2, options3, v) -> {
                    mTextMarried.setText(marriedList.get(options1));
                }).setLayoutRes(R.layout.info_layout, v -> {
                    TextView textView = v.findViewById(R.id.text_title);
                    textView.setText("婚姻");
                    v.findViewById(R.id.btn_cancel).setOnClickListener(v12 -> mPickerMarried.dismiss());
                    v.findViewById(R.id.btn_submit).setOnClickListener(v1 -> {
                        mPickerMarried.returnData();
                        mPickerMarried.dismiss();
                    });
                }).setDividerColor(getResources().getColor(R.color.color_blue)).isDialog(true).build();
        mPickerMarried.setPicker(marriedList);

        mPickerEducation =
                new OptionsPickerView.Builder(getActivity(), (options1, options2, options3, v) -> {
                    mTextEducation.setText(educationList.get(options1));
                }).setLayoutRes(R.layout.info_layout, v -> {
                    TextView textView = v.findViewById(R.id.text_title);
                    textView.setText("最高学历");
                    v.findViewById(R.id.btn_cancel).setOnClickListener(v12 -> mPickerEducation.dismiss());
                    v.findViewById(R.id.btn_submit).setOnClickListener(v1 -> {
                        mPickerEducation.returnData();
                        mPickerEducation.dismiss();
                    });
                }).setDividerColor(getResources().getColor(R.color.color_blue)).isDialog(true).build();
        mPickerEducation.setPicker(educationList);

        mPickerHouse =
                new OptionsPickerView.Builder(getActivity(), (options1, options2, options3, v) -> {
                    mTextHouse.setText(houseList.get(options1));
                }).setLayoutRes(R.layout.info_layout, v -> {
                    TextView textView = v.findViewById(R.id.text_title);
                    textView.setText("房产信息");
                    v.findViewById(R.id.btn_cancel).setOnClickListener(v12 -> mPickerHouse.dismiss());
                    v.findViewById(R.id.btn_submit).setOnClickListener(v1 -> {
                        mPickerHouse.returnData();
                        mPickerHouse.dismiss();
                    });
                }).setDividerColor(getResources().getColor(R.color.color_blue)).isDialog(true).build();
        mPickerHouse.setPicker(houseList);
    }

    @Override
    protected int getTitleId() {
        return R.string.step3_info_confirm_title;
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void sendBroadcast() {
        CertificationFragment.refresh(getActivity());
    }
}
