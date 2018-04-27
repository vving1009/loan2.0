package com.jiaye.cashloan.view.view.loan.auth.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.dictionary.Relation;
import com.jiaye.cashloan.view.BaseActivity;
import com.jiaye.cashloan.view.BaseDialog;
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

    private OptionsPickerView mOptionsFamily2;

    private OptionsPickerView mOptionsFriend2;

    private EditText mEditFamilyName;

    private TextView mTextFamily;

    private EditText mEditFamilyPhone;

    private EditText mEditFriendName;

    private TextView mTextFriend;

    private EditText mEditFriendPhone;

    private EditText mEditFamilyName2;

    private TextView mTextFamily2;

    private EditText mEditFamilyPhone2;

    private EditText mEditFriendName2;

    private TextView mTextFriend2;

    private EditText mEditFriendPhone2;

    private BaseDialog mSaveDialog;

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
        mEditFamilyName2 = findViewById(R.id.edit_family_name_2);
        mTextFamily2 = findViewById(R.id.text_family_2);
        mEditFamilyPhone2 = findViewById(R.id.edit_family_phone_2);
        mEditFriendName2 = findViewById(R.id.edit_friend_name_2);
        mTextFriend2 = findViewById(R.id.text_friend_2);
        mEditFriendPhone2 = findViewById(R.id.edit_friend_phone_2);
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
        findViewById(R.id.layout_family_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFamilyPicker2();
            }
        });
        findViewById(R.id.layout_friend_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFriendPicker2();
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
    public void setFamilyName2(String text) {
        mEditFamilyName2.setText(text);
    }

    @Override
    public void setFamily2(String text) {
        mTextFamily2.setText(text);
    }

    @Override
    public void setFamilyPhone2(String text) {
        mEditFamilyPhone2.setText(text);
    }

    @Override
    public void setFriendName2(String text) {
        mEditFriendName2.setText(text);
    }

    @Override
    public void setFriend2(String text) {
        mTextFriend2.setText(text);
    }

    @Override
    public void setFriendPhone2(String text) {
        mEditFriendPhone2.setText(text);
    }

    @Override
    public String getFamilyName2() {
        return mEditFamilyName2.getText().toString();
    }

    @Override
    public String getFamily2() {
        return mTextFamily2.getText().toString();
    }

    @Override
    public String getFamilyPhone2() {
        return mEditFamilyPhone2.getText().toString();
    }

    @Override
    public String getFriendName2() {
        return mEditFriendName2.getText().toString();
    }

    @Override
    public String getFriend2() {
        return mTextFriend2.getText().toString();
    }

    @Override
    public String getFriendPhone2() {
        return mEditFriendPhone2.getText().toString();
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
    public void initFamily2(final ArrayList<Relation> relations) {
        mOptionsFamily2 =
                new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        for (int i = 0; i < relations.size(); i++) {
                            relations.get(i).setSelect(false);
                        }
                        relations.get(options1).setSelect(true);
                        mTextFamily2.setText(relations.get(options1).getPickerViewText());
                    }
                }).setLayoutRes(R.layout.loan_auth_person_item, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView textView = v.findViewById(R.id.text_title);
                        textView.setText(R.string.loan_auth_contact_family);
                        v.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOptionsFamily2.dismiss();
                            }
                        });
                        v.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOptionsFamily2.returnData();
                                mOptionsFamily2.dismiss();
                            }
                        });
                    }
                }).setDividerColor(getResources().getColor(R.color.color_blue)).isDialog(true).build();
        mOptionsFamily2.setPicker(relations);
    }

    @Override
    public void initFriend2(final ArrayList<Relation> relations) {
        mOptionsFriend2 =
                new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        for (int i = 0; i < relations.size(); i++) {
                            relations.get(i).setSelect(false);
                        }
                        relations.get(options1).setSelect(true);
                        mTextFriend2.setText(relations.get(options1).getPickerViewText());
                    }
                }).setLayoutRes(R.layout.loan_auth_person_item, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView textView = v.findViewById(R.id.text_title);
                        textView.setText(R.string.loan_auth_contact_friend);
                        v.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOptionsFriend2.dismiss();
                            }
                        });
                        v.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOptionsFriend2.returnData();
                                mOptionsFriend2.dismiss();
                            }
                        });
                    }
                }).setDividerColor(getResources().getColor(R.color.color_blue)).isDialog(true).build();
        mOptionsFriend2.setPicker(relations);
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

    private void showFamilyPicker2() {
        mOptionsFamily2.show();
    }

    private void showFriendPicker2() {
        mOptionsFriend2.show();
    }
}
