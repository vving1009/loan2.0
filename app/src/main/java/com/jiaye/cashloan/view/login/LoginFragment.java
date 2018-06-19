package com.jiaye.cashloan.view.login;

import android.Manifest;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.login.source.LoginRepository;
import com.jiaye.cashloan.widget.LoanEditText;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * LoginFragment
 *
 * @author 贾博瑄
 */

public class LoginFragment extends BaseFragment implements LoginContract.View, EasyPermissions.PermissionCallbacks {

    private final int READ_SMS_REQUEST = 101;

    private LoginContract.Presenter mPresenter;

    private LoanEditText mEditPhone;

    private LoanEditText mEditCode;

    private Button mBtnLogin;

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.login_fragment, container, false);
        root.getBackground().setAlpha(26);
        root.findViewById(R.id.img_close).setOnClickListener(v -> getActivity().finish());
        mEditPhone = root.findViewById(R.id.edit_phone);
        mEditPhone.setOnClickVerificationCode(() -> {
            clearError();
            EasyPermissions.requestPermissions(this, READ_SMS_REQUEST, Manifest.permission.READ_SMS);
        });
        mEditCode = root.findViewById(R.id.edit_code);
        mBtnLogin = root.findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(v -> {
            clearError();
            mPresenter.login();
        });
        mPresenter = new LoginPresenter(this, new LoginRepository());
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
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void smsVerificationCodeCountDown() {
        mEditPhone.startCountDown();
    }

    /*清除错误信息*/
    private void clearError() {
        mEditPhone.setError("");
        mEditCode.setError("");
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
        if (requestCode == READ_SMS_REQUEST && EasyPermissions.hasPermissions(getContext(),
                Manifest.permission.READ_SMS)) {
            mPresenter.verificationCode();
        }
    }

    @Override
    public void writeSmsCode(String code) {
        mEditCode.setText(code);
    }
}