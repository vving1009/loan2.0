package com.jiaye.cashloan.view;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * BaseActivity
 *
 * @author 贾博瑄
 */

public class BaseActivity extends AppCompatActivity implements BaseViewContract {

    protected ProgressDialog mDialog;

    @Override
    public void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToastById(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        if (mDialog == null) {
            mDialog = new ProgressDialog(this);
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
}
