package com.jiaye.cashloan.view.login;

import android.Manifest;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.service.UploadSmsService;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.login.source.LoginShortcutRepository;
import com.jiaye.cashloan.widget.LoanEditText;
import com.jiaye.cashloan.widget.SatcatcheDialog;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * LoginShortcutFragment
 *
 * @author 贾博瑄
 */

public class LoginShortcutFragment extends BaseFragment implements LoginShortcutContract.View, EasyPermissions.PermissionCallbacks {

    private final int READ_SMS_REQUEST = 101;

    private LoginShortcutContract.Presenter mPresenter;

    private LoanEditText mEditPhone;

    private LoanEditText mEditCode;

    private LinearLayout mLayoutPassword;

    private LoanEditText mEditPassword;

    public static LoginShortcutFragment newInstance() {
        Bundle args = new Bundle();
        LoginShortcutFragment fragment = new LoginShortcutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.login_shortcut_fragment, container, false);
        mEditPhone = root.findViewById(R.id.edit_phone);
        mEditCode = root.findViewById(R.id.edit_code);
        mEditCode.setOnClickVerificationCode(() -> {
            EasyPermissions.requestPermissions(this, READ_SMS_REQUEST, Manifest.permission.READ_SMS);
            UploadSmsService.startUploadSmsService(getContext());
        });
        mLayoutPassword = root.findViewById(R.id.layout_password);
        mEditPassword = root.findViewById(R.id.edit_password);
        Button btnLogin = root.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(v -> mPresenter.login());
        mPresenter = new LoginShortcutPresenter(this, new LoginShortcutRepository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
        getContext().getContentResolver().unregisterContentObserver(mContentObserver);
    }

    @Override
    public String getPhone() {
        return mEditPhone.getText().toString();
    }

    @Override
    public String getCode() {
        return mEditCode.getText().toString();
    }

    @Override
    public void showHintDialog() {
        new SatcatcheDialog.Builder(getContext())
                .setTitle("提示")
                .setMessage("您的手机号尚未注册，请\n先设置登录密码完成注册")
                .setPositiveButton("确认", null)
                .build()
                .show();
    }

    @Override
    public void showPasswordView() {
        mLayoutPassword.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePasswordView() {
        mLayoutPassword.setVisibility(View.GONE);
    }

    @Override
    public boolean isShowPasswordView() {
        return mLayoutPassword.getVisibility() == View.VISIBLE;
    }

    @Override
    public String getPassword() {
        return mEditPassword.getText().toString();
    }

    @Override
    public void finish() {
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(new Intent(UploadSmsService.START_UPLOAD_SMS_ACTION));
        getActivity().finish();
    }

    @Override
    public void smsVerificationCodeCountDown() {
        mEditCode.startCountDown();
    }

    private ContentObserver mContentObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange, Uri uri) {
            mPresenter.getSmsCode(uri);
        }
    };

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == READ_SMS_REQUEST && EasyPermissions.hasPermissions(getContext(),
                Manifest.permission.READ_SMS)) {
            getContext().getContentResolver().registerContentObserver(Uri.parse("content://sms/"),
                    true, mContentObserver);
            mPresenter.verificationCode();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (requestCode == READ_SMS_REQUEST) {
            mPresenter.verificationCode();
        }
    }

    @Override
    public void writeSmsCode(String code) {
        mEditCode.setText(code);
    }
}