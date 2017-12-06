package com.jiaye.cashloan.view.view.loan.auth.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.http.data.dictionary.Relation;
import com.jiaye.cashloan.view.data.loan.auth.source.info.LoanAuthContactInfoRepository;

import java.util.ArrayList;

/**
 * LoanAuthContactInfoActivity
 *
 * @author 贾博瑄
 */

public class LoanAuthContactInfoActivity extends BaseActivity implements LoanAuthContactInfoContract.View {

    private LoanAuthContactInfoContract.Presenter mPresenter;

    private OptionsPickerView mOptionsFamily;

    private OptionsPickerView mOptionsFriend;

    private TextView mEditFamilyName;

    private TextView mTextFamily;

    private TextView mEditFamilyPhone;

    private TextView mEditFriendName;

    private TextView mTextFriend;

    private TextView mEditFriendPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_auth_contact_info_activity);
        mEditFamilyName = findViewById(R.id.edit_family_name);
        mTextFamily = findViewById(R.id.text_family);
        mEditFamilyPhone = findViewById(R.id.edit_family_phone);
        mEditFriendName = findViewById(R.id.edit_friend_name);
        mTextFriend = findViewById(R.id.text_friend);
        mEditFriendPhone = findViewById(R.id.edit_friend_phone);
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
        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.submit();
            }
        });
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mPresenter = new LoanAuthContactInfoPresenter(this, new LoanAuthContactInfoRepository());
        mPresenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void setFamilyName(String text) {
        mEditFamilyName.setText(text);
    }

    @Override
    public void setFamily(String text) {
        mTextFamily.setText(text);
    }

    @Override
    public void setFamilyPhone(String text) {
        mEditFamilyPhone.setText(text);
    }

    @Override
    public void setFriendName(String text) {
        mEditFriendName.setText(text);
    }

    @Override
    public void setFriend(String text) {
        mTextFriend.setText(text);
    }

    @Override
    public void setFriendPhone(String text) {
        mEditFriendPhone.setText(text);
    }

    @Override
    public String getFamilyName() {
        return mEditFamilyName.getText().toString();
    }

    @Override
    public String getFamily() {
        return mTextFamily.getText().toString();
    }

    @Override
    public String getFamilyPhone() {
        return mEditFamilyPhone.getText().toString();
    }

    @Override
    public String getFriendName() {
        return mEditFriendName.getText().toString();
    }

    @Override
    public String getFriend() {
        return mTextFriend.getText().toString();
    }

    @Override
    public String getFriendPhone() {
        return mEditFriendPhone.getText().toString();
    }

    @Override
    public void initFamily(final ArrayList<Relation> relations) {
        mOptionsFamily =
                new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        for (int i = 0; i < relations.size(); i++) {
                            relations.get(i).setSelect(false);
                        }
                        relations.get(options1).setSelect(true);
                        mTextFamily.setText(relations.get(options1).getPickerViewText());
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
        mOptionsFamily.setPicker(relations);
    }

    @Override
    public void initFriend(final ArrayList<Relation> relations) {
        mOptionsFriend =
                new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        for (int i = 0; i < relations.size(); i++) {
                            relations.get(i).setSelect(false);
                        }
                        relations.get(options1).setSelect(true);
                        mTextFriend.setText(relations.get(options1).getPickerViewText());
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
        mOptionsFriend.setPicker(relations);
    }

    @Override
    public void result() {
        finish();
    }

    private void showFamilyPicker() {
        mOptionsFamily.show();
    }

    private void showFriendPicker() {
        mOptionsFriend.show();
    }
}
