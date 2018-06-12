package com.jiaye.cashloan.view;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.jiaye.cashloan.widget.CustomProgressDialog;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * BaseFragment
 *
 * @author 贾博瑄
 */

public abstract class BaseFragment extends Fragment implements BaseViewContract {

    protected CustomProgressDialog mDialog;

    @Override
    public void showToast(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToastById(int resId) {
        Toast.makeText(getActivity(), getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        if (mDialog == null) {
            mDialog = new CustomProgressDialog(getActivity());
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
        }
        mDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
