package com.jiaye.cashloan.view.my;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.FunctionActivity;
import com.jiaye.cashloan.view.login.LoginActivity;
import com.jiaye.cashloan.view.my.source.MyRepository;
import com.jiaye.cashloan.view.view.my.MyActivity;

/**
 * MyFragment
 *
 * @author 贾博瑄
 */

public class MyFragment extends BaseFragment implements MyContract.View {

    private MyContract.Presenter mPresenter;

    private Button mBtn;

    public static MyFragment newInstance() {
        Bundle args = new Bundle();
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.my_fragment, container, false);
        mBtn = root.findViewById(R.id.btn_login);
        mBtn.setOnClickListener(v -> mPresenter.loginOrLogout());
        root.findViewById(R.id.layout_bank).setOnClickListener(v -> mPresenter.bank());
        root.findViewById(R.id.layout_plan).setOnClickListener(v -> mPresenter.plan());
        root.findViewById(R.id.layout_about).setOnClickListener(v -> startAboutView());
        root.findViewById(R.id.layout_phone).setOnClickListener(v -> startPhoneView());
        mPresenter = new MyPresenter(this, new MyRepository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.update();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void startAuthView() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void setBtnText(String text) {
        mBtn.setText(text);
    }

    @Override
    public void showAccountView() {
        FunctionActivity.function(getActivity(), "Account");
    }

    @Override
    public void showPlanView() {
        FunctionActivity.function(getActivity(), "Plan");
    }

    private void startAboutView() {
        FunctionActivity.function(getActivity(), "About");
    }

    private void startPhoneView() {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getString(R.string.my_contact_phone)));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException exception) {
            // 捕获在不支持拨号功能的设备上调用拨号功能时发生的异常
            exception.printStackTrace();
        }
    }
}
