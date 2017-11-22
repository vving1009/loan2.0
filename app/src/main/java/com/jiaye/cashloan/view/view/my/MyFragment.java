package com.jiaye.cashloan.view.view.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.data.auth.User;
import com.jiaye.cashloan.view.data.my.source.MyRepository;
import com.jiaye.cashloan.view.view.auth.AuthActivity;

/**
 * MyFragment
 *
 * @author 贾博瑄
 */

public class MyFragment extends BaseFragment implements MyContract.View {

    private MyContract.Presenter mPresenter;

    private TextView mTextName;

    private TextView mTextApproveNumber;

    private TextView mTextLoanNumber;

    private TextView mTextHistoryNumber;

    public static MyFragment newInstance() {
        Bundle args = new Bundle();
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.my_fragment, container, false);
        mTextName = root.findViewById(R.id.text_name);
        mTextApproveNumber = root.findViewById(R.id.text_approve_number);
        mTextLoanNumber = root.findViewById(R.id.text_loan_number);
        mTextHistoryNumber = root.findViewById(R.id.text_history_number);
        root.findViewById(R.id.layout_my_certificate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onClickMyCertificate();
            }
        });
        root.findViewById(R.id.layout_help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHelpView();
            }
        });
        root.findViewById(R.id.layout_qrcode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQRCodeView();
            }
        });
        root.findViewById(R.id.layout_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAboutView();
            }
        });
        root.findViewById(R.id.layout_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startContactView();
            }
        });
        root.findViewById(R.id.layout_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSettingsView();
            }
        });
        root.findViewById(R.id.layout_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startShareView();
            }
        });
        mPresenter = new MyPresenter(this, new MyRepository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void showUserInfo(User user) {
        mTextName.setText(user.getShowName());
        mTextApproveNumber.setText(user.getApproveNumber());
        mTextLoanNumber.setText(user.getApproveNumber());
        mTextHistoryNumber.setText(user.getApproveNumber());
    }

    @Override
    public void startAuthView() {
        Intent intent = new Intent(getContext(), AuthActivity.class);
        startActivity(intent);
    }

    @Override
    public void showMyCertificateView(User user) {
        Intent intent = new Intent(getContext(), MyActivity.class);
        intent.putExtra("view", "certificate");
        intent.putExtra("user", user);
        startActivity(intent);
    }

    private void startHelpView() {
        Intent intent = new Intent(getContext(), MyActivity.class);
        intent.putExtra("view", "help");
        startActivity(intent);
    }

    private void startQRCodeView() {

    }

    private void startAboutView() {
        Intent intent = new Intent(getContext(), MyActivity.class);
        intent.putExtra("view", "about");
        startActivity(intent);
    }

    private void startContactView() {

    }

    private void startSettingsView() {
        Intent intent = new Intent(getContext(), MyActivity.class);
        intent.putExtra("view", "settings");
        startActivity(intent);
    }

    private void startShareView() {

    }
}
