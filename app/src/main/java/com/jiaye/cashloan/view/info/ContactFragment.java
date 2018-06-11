package com.jiaye.cashloan.view.info;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.dictionary.Relation;
import com.jiaye.cashloan.http.data.loan.SaveContact;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.info.source.ContactRepository;
import com.jiaye.cashloan.widget.SatcatcheDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * ContactFragment
 *
 * @author 贾博瑄
 */
public class ContactFragment extends BaseFragment implements ContactContract.View, EasyPermissions.PermissionCallbacks {

    private static final int REQUEST_CODE_CONTACT = 100;

    public static ContactFragment newInstance() {
        Bundle args = new Bundle();
        ContactFragment fragment = new ContactFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ContactContract.Presenter mPresenter;

    private OptionsPickerView mOptions1;

    private OptionsPickerView mOptions2;

    private EditText mEdit1Name;

    private TextView mText1Relation;

    private TextView mText1Phone;

    private EditText mEdit2Name;

    private TextView mText2Relation;

    private TextView mText2Phone;

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        new SatcatcheDialog.Builder(getContext())
                .setTitle("权限申请")
                .setMessage("此应用需要" + "通讯录" + "权限，否则应用会关闭，是否打开设置？")
                .setPositiveButton("好", (dialog, which) ->
                        startActivityForResult(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                        .setData(Uri.fromParts("package", getContext().getPackageName(), null)),
                                REQUEST_CODE_CONTACT))
                .setNegativeButton("不行", (dialog, which) -> getActivity().finish())
                .build()
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            mPresenter.selectPhone(data.getData(), requestCode);
            // 只上传一次通讯录
            if (requestCode == REQUEST_CODE_CONTACT + 1) {
                mPresenter.upLoadContact();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.contact_fragment, container, false);
        mEdit1Name = root.findViewById(R.id.edit_1_name);
        mText1Relation = root.findViewById(R.id.text_1_relation);
        mText1Phone = root.findViewById(R.id.text_1_phone_value);
        mEdit2Name = root.findViewById(R.id.edit_2_name);
        mText2Relation = root.findViewById(R.id.text_2_relation);
        mText2Phone = root.findViewById(R.id.text_2_phone_value);
        root.findViewById(R.id.layout_1_relation).setOnClickListener(v -> show1Picker());
        root.findViewById(R.id.layout_2_relation).setOnClickListener(v -> show2Picker());
        root.findViewById(R.id.layout_1_phone).setOnClickListener(v -> showContact(REQUEST_CODE_CONTACT + 1));
        root.findViewById(R.id.layout_2_phone).setOnClickListener(v -> showContact(REQUEST_CODE_CONTACT + 2));
        mPresenter = new ContactPresenter(this, new ContactRepository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPhone(int requestCode, String phone) {
        switch (requestCode) {
            case REQUEST_CODE_CONTACT + 1:
                mText1Phone.setText(phone);
                break;
            case REQUEST_CODE_CONTACT + 2:
                mText2Phone.setText(phone);
                break;
        }
    }

    @Override
    public String get1Name() {
        return mEdit1Name.getText().toString();
    }

    @Override
    public String get1Relation() {
        return mText1Relation.getText().toString();
    }

    @Override
    public String get1Phone() {
        return mText1Phone.getText().toString();
    }

    @Override
    public String get2Name() {
        return mEdit2Name.getText().toString();
    }

    @Override
    public String get2Relation() {
        return mText2Relation.getText().toString();
    }

    @Override
    public String get2Phone() {
        return mText2Phone.getText().toString();
    }

    @Override
    public void init1Relation(final ArrayList<Relation> relations) {
        mOptions1 =
                new OptionsPickerView.Builder(getActivity(), (options1, options2, options3, v) -> {
                    for (int i = 0; i < relations.size(); i++) {
                        relations.get(i).setSelect(false);
                    }
                    relations.get(options1).setSelect(true);
                    mText1Relation.setText(relations.get(options1).getPickerViewText());
                }).setLayoutRes(R.layout.loan_auth_person_item, v -> {
                    TextView textView = v.findViewById(R.id.text_title);
                    textView.setText(R.string.loan_auth_contact_family);
                    v.findViewById(R.id.btn_cancel).setOnClickListener(v1 -> mOptions1.dismiss());
                    v.findViewById(R.id.btn_submit).setOnClickListener(v12 -> {
                        mOptions1.returnData();
                        mOptions1.dismiss();
                    });
                }).setDividerColor(getResources().getColor(R.color.color_blue)).isDialog(true).build();
        mOptions1.setPicker(relations);
    }

    @Override
    public void init2Relation(final ArrayList<Relation> relations) {
        mOptions2 =
                new OptionsPickerView.Builder(getActivity(), (options1, options2, options3, v) -> {
                    for (int i = 0; i < relations.size(); i++) {
                        relations.get(i).setSelect(false);
                    }
                    relations.get(options1).setSelect(true);
                    mText2Relation.setText(relations.get(options1).getPickerViewText());
                }).setLayoutRes(R.layout.loan_auth_person_item, v -> {
                    TextView textView = v.findViewById(R.id.text_title);
                    textView.setText(R.string.loan_auth_contact_friend);
                    v.findViewById(R.id.btn_cancel).setOnClickListener(v12 -> mOptions2.dismiss());
                    v.findViewById(R.id.btn_submit).setOnClickListener(v1 -> {
                        mOptions2.returnData();
                        mOptions2.dismiss();
                    });
                }).setDividerColor(getResources().getColor(R.color.color_blue)).isDialog(true).build();
        mOptions2.setPicker(relations);
    }

    @Override
    public void result() {
        getActivity().finish();
    }

    public Flowable<Boolean> canSubmit() {
        return mPresenter.canSubmit();
    }

    public Flowable<SaveContact> submit() {
        return mPresenter.submit();
    }

    private void show1Picker() {
        mOptions1.show();
    }

    private void show2Picker() {
        mOptions2.show();
    }

    private void showContact(int requestCode) {
        EasyPermissions.requestPermissions(this, requestCode, Manifest.permission.READ_CONTACTS);
    }
}
