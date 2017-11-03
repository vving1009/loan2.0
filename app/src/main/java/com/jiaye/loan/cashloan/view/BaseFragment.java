package com.jiaye.loan.cashloan.view;

import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * BaseFragment
 *
 * @author 贾博瑄
 */

public abstract class BaseFragment extends Fragment implements BaseViewContract {

    @Override
    public void showToast(String string) {
        Toast.makeText(getContext(), string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToastById(int resId) {
        Toast.makeText(getContext(), getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {

    }
}
