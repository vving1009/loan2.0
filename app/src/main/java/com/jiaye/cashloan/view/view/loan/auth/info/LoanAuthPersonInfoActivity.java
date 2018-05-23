package com.jiaye.cashloan.view.view.loan.auth.info;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.http.data.dictionary.Area;
import com.jiaye.cashloan.http.data.dictionary.Education;
import com.jiaye.cashloan.http.data.dictionary.Marriage;
import com.jiaye.cashloan.view.BaseDialog;
import com.jiaye.cashloan.view.data.loan.auth.source.info.LoanAuthPersonInfoRepository;

import java.util.ArrayList;


/**
 * LoanAuthPersonInfoActivity
 *
 * @author 贾博瑄
 */

public class LoanAuthPersonInfoActivity extends BaseActivity implements LoanAuthPersonInfoContract.View {

    private LoanAuthPersonInfoContract.Presenter mPresenter;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_auth_person_info_activity);
        mTextEducation = findViewById(R.id.text_education);
        mTextMarriage = findViewById(R.id.text_marriage);
        mTextRegisterCity = findViewById(R.id.text_register_city);
        mTextCity = findViewById(R.id.text_city);
        mEditAddress = findViewById(R.id.edit_address);
        mEditEmail = findViewById(R.id.edit_email);
        mSaveDialog = new BaseDialog(this);
        findViewById(R.id.layout_education).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEducationPicker();
            }
        });
        findViewById(R.id.layout_marriage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMarriagePicker();
            }
        });
        findViewById(R.id.layout_register_city).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterCityPicker();
            }
        });
        findViewById(R.id.layout_city).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCityPicker();
            }
        });
        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSaveDialog.show();
            }
        });
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mSaveDialog = new BaseDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.save_dialog_layout, null);
        view.findViewById(R.id.text_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.submit();
                mSaveDialog.dismiss();
            }
        });
        view.findViewById(R.id.text_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSaveDialog.dismiss();
            }
        });
        mSaveDialog.setContentView(view);
        mPresenter = new LoanAuthPersonInfoPresenter(this, new LoanAuthPersonInfoRepository());
        mPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        mTextRegisterCity.setText(areas.get(options1).getName() + "|" + areas2.get(options1).get(options2) + "|" + areas3.get(options1).get(options2).get(options3));
                    }
                }).setLayoutRes(R.layout.loan_auth_person_item, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView textView = v.findViewById(R.id.text_title);
                        textView.setText(R.string.loan_auth_person_register_city);
                        v.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOptionsRegisterCity.dismiss();
                            }
                        });
                        v.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOptionsRegisterCity.returnData();
                                mOptionsRegisterCity.dismiss();
                            }
                        });
                    }
                }).setDividerColor(getResources().getColor(R.color.color_blue)).isDialog(true).build();
        mOptionsRegisterCity.setPicker(areas, areas2, areas3);

        mOptionsCity =
                new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        mTextCity.setText(areas.get(options1).getName() + "|" + areas2.get(options1).get(options2) + "|" + areas3.get(options1).get(options2).get(options3));
                    }
                }).setLayoutRes(R.layout.loan_auth_person_item, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView textView = v.findViewById(R.id.text_title);
                        textView.setText(R.string.loan_auth_person_city);
                        v.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOptionsCity.dismiss();
                            }
                        });
                        v.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOptionsCity.returnData();
                                mOptionsCity.dismiss();
                            }
                        });
                    }
                }).setDividerColor(getResources().getColor(R.color.color_blue)).isDialog(true).build();
        mOptionsCity.setPicker(areas, areas2, areas3);
    }

    @Override
    public void initEducation(final ArrayList<Education> educations) {
        mOptionsEducation =
                new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        for (int i = 0; i < educations.size(); i++) {
                            educations.get(i).setSelect(false);
                        }
                        educations.get(options1).setSelect(true);
                        mTextEducation.setText(educations.get(options1).getPickerViewText());
                    }
                }).setLayoutRes(R.layout.loan_auth_person_item, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView textView = v.findViewById(R.id.text_title);
                        textView.setText(R.string.loan_auth_person_education);
                        v.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOptionsEducation.dismiss();
                            }
                        });
                        v.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOptionsEducation.returnData();
                                mOptionsEducation.dismiss();
                            }
                        });
                    }
                }).setDividerColor(getResources().getColor(R.color.color_blue)).isDialog(true).build();
        mOptionsEducation.setPicker(educations);
    }

    @Override
    public void initMarriage(final ArrayList<Marriage> marriages) {
        mOptionsMarriage =
                new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        for (int i = 0; i < marriages.size(); i++) {
                            marriages.get(i).setSelect(false);
                        }
                        marriages.get(options1).setSelect(true);
                        mTextMarriage.setText(marriages.get(options1).getPickerViewText());
                    }
                }).setLayoutRes(R.layout.loan_auth_person_item, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView textView = v.findViewById(R.id.text_title);
                        textView.setText(R.string.loan_auth_person_marriage);
                        v.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOptionsMarriage.dismiss();
                            }
                        });
                        v.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOptionsMarriage.returnData();
                                mOptionsMarriage.dismiss();
                            }
                        });
                    }
                }).setDividerColor(getResources().getColor(R.color.color_blue)).isDialog(true).build();
        mOptionsMarriage.setPicker(marriages);
    }

    @Override
    public void result() {
        finish();
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
