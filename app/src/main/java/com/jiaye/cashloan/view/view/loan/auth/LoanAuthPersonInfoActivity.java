package com.jiaye.cashloan.view.view.loan.auth;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.data.auth.Area;
import com.jiaye.cashloan.view.data.auth.Education;
import com.jiaye.cashloan.view.data.auth.Marriage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * LoanAuthPersonInfoActivity
 *
 * @author 贾博瑄
 */

public class LoanAuthPersonInfoActivity extends AppCompatActivity {

    private OptionsPickerView mOptionsEducation;

    private OptionsPickerView mOptionsMarriage;

    private OptionsPickerView mOptionsRegisterCity;

    private OptionsPickerView mOptionsCity;

    private TextView mTextEducation;

    private TextView mTextMarriage;

    private TextView mTextRegisterCity;

    private TextView mTextCity;

    private ArrayList<ArrayList<String>> mAreas2;

    private ArrayList<ArrayList<ArrayList<String>>> mAreas3;

    private ArrayList<Education> mEducations;

    private ArrayList<Marriage> mMarriages;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_auth_person_info_activity);
        mTextEducation = findViewById(R.id.text_education);
        mTextMarriage = findViewById(R.id.text_marriage);
        mTextRegisterCity = findViewById(R.id.text_register_city);
        mTextCity = findViewById(R.id.text_city);
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
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        init();
    }

    private void init() {
        // TODO: 2017/11/13 因为服务器返回的结构体有问题,暂时使用本地资源.
        AssetManager assetManager = getAssets();
        File dir = getExternalFilesDir("dictionary");
        if (dir != null && !dir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            dir.mkdir();
        }
        String fileNames[] = new String[0];
        try {
            fileNames = assetManager.list("dictionary");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String fileName : fileNames) {
            InputStream input = getClass().getClassLoader().getResourceAsStream("assets/dictionary/" + fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            Gson gson = new Gson();
            switch (fileName) {
                case "area.json":
                    transformArea(br, gson);
                    break;
                case "education.json":
                    transformEducation(br, gson);
                    break;
                case "marriage.json":
                    transformMarriage(br, gson);
                    break;
            }
        }
    }

    private void transformArea(BufferedReader br, Gson gson) {
        ArrayList<Area> areas = gson.fromJson(br, new TypeToken<List<Area>>() {
        }.getType());
        mAreas2 = new ArrayList<>();
        mAreas3 = new ArrayList<>();
        for (int i = 0; i < areas.size(); i++) {//遍历省份
            ArrayList<String> cities = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> provinces = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int c = 0; c < areas.get(i).getCity().size(); c++) {//遍历该省份的所有城市
                String CityName = areas.get(i).getCity().get(c).getName();
                cities.add(CityName);//添加城市
                ArrayList<String> strings = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (areas.get(i).getCity().get(c).getAreas() == null || areas.get(i).getCity().get(c).getAreas().size() == 0) {
                    strings.add("");
                } else {
                    strings.addAll(areas.get(i).getCity().get(c).getAreas());
                }
                provinces.add(strings);//添加该省所有地区数据
            }
            mAreas2.add(cities);
            mAreas3.add(provinces);
        }

        mOptionsRegisterCity =
                new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        mTextRegisterCity.setText(mAreas2.get(options1).get(options2));
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
        mOptionsRegisterCity.setPicker(areas, mAreas2);

        mOptionsCity =
                new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        mTextCity.setText(mAreas3.get(options1).get(options2).get(options3));
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
        mOptionsCity.setPicker(areas, mAreas2, mAreas3);
    }

    private void transformEducation(BufferedReader br, Gson gson) {
        mEducations = gson.fromJson(br, new TypeToken<List<Education>>() {
        }.getType());
        mOptionsEducation =
                new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        mTextEducation.setText(mEducations.get(options1).getPickerViewText());
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
        mOptionsEducation.setPicker(mEducations);
    }

    private void transformMarriage(BufferedReader br, Gson gson) {
        mMarriages = gson.fromJson(br, new TypeToken<List<Marriage>>() {
        }.getType());
        mOptionsMarriage =
                new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        mTextMarriage.setText(mMarriages.get(options1).getPickerViewText());
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
        mOptionsMarriage.setPicker(mMarriages);
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
