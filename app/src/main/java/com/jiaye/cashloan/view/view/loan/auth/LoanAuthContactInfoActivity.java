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
import com.jiaye.cashloan.view.data.auth.Education;
import com.jiaye.cashloan.view.data.auth.Relation;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * LoanAuthContactInfoActivity
 *
 * @author 贾博瑄
 */

public class LoanAuthContactInfoActivity extends AppCompatActivity {

    private OptionsPickerView mOptionsFamily;

    private OptionsPickerView mOptionsFriend;

    private TextView mTextFamily;

    private TextView mTextFriend;

    private ArrayList<Relation> mRelations;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_auth_contact_info_activity);
        mTextFamily = findViewById(R.id.text_family);
        mTextFriend = findViewById(R.id.text_friend);
        findViewById(R.id.layout_family).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFamilyPicker();
            }
        });
        findViewById(R.id.layout_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFriendPicker();
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
                case "relation.json":
                    transformRelation(br, gson);
                    break;
            }
        }
    }

    private void transformRelation(BufferedReader br, Gson gson) {
        mRelations = gson.fromJson(br, new TypeToken<List<Relation>>() {
        }.getType());
        mOptionsFamily =
                new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        mTextFamily.setText(mRelations.get(options1).getPickerViewText());
                    }
                }).setLayoutRes(R.layout.loan_auth_person_item, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView textView = v.findViewById(R.id.text_title);
                        textView.setText(R.string.loan_auth_contact_family);
                        v.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOptionsFamily.dismiss();
                            }
                        });
                        v.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOptionsFamily.returnData();
                                mOptionsFamily.dismiss();
                            }
                        });
                    }
                }).setDividerColor(getResources().getColor(R.color.color_blue)).isDialog(true).build();
        mOptionsFamily.setPicker(mRelations);

        mOptionsFriend =
                new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        mTextFriend.setText(mRelations.get(options1).getPickerViewText());
                    }
                }).setLayoutRes(R.layout.loan_auth_person_item, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView textView = v.findViewById(R.id.text_title);
                        textView.setText(R.string.loan_auth_contact_friend);
                        v.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOptionsFriend.dismiss();
                            }
                        });
                        v.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOptionsFriend.returnData();
                                mOptionsFriend.dismiss();
                            }
                        });
                    }
                }).setDividerColor(getResources().getColor(R.color.color_blue)).isDialog(true).build();
        mOptionsFriend.setPicker(mRelations);
    }

    private void showFamilyPicker() {
        mOptionsFamily.show();
    }

    private void showFriendPicker() {
        mOptionsFriend.show();
    }
}
