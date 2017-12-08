package com.jiaye.cashloan.view.view.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.my.User;
import com.jiaye.cashloan.view.BaseDialog;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.data.my.source.MyRepository;
import com.jiaye.cashloan.view.view.auth.AuthActivity;
import com.jiaye.cashloan.view.view.help.LoanAuthHelpActivity;
import com.jiaye.cashloan.view.view.loan.LoanDetailsActivity;

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

    private BottomSheetDialog mDialog;

    private BaseDialog mQRCodeDialog;

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
        root.findViewById(R.id.layout_approve).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.approve();
            }
        });
        root.findViewById(R.id.layout_progress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.progress();
            }
        });
        root.findViewById(R.id.layout_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.history();
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
                mPresenter.settings();
            }
        });
        root.findViewById(R.id.layout_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.share();
            }
        });
        mDialog = new BottomSheetDialog(getActivity());
        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.share_layout, null);
        layout.findViewById(R.id.text_wechat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                mPresenter.shareWeChat();
            }
        });
        layout.findViewById(R.id.text_moments).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                mPresenter.shareMoments();
            }
        });
        layout.findViewById(R.id.text_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.setContentView(layout);
        mQRCodeDialog = new BaseDialog(getActivity());
        View qrCode = LayoutInflater.from(getActivity()).inflate(R.layout.qrcode_dialog_layout,null);
        qrCode.findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQRCodeDialog.dismiss();
            }
        });
        mQRCodeDialog.setContentView(qrCode);
        mPresenter = new MyPresenter(this, new MyRepository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.requestUser();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDialog.dismiss();
        mPresenter.unsubscribe();
    }

    @Override
    public void showUserInfo(User user) {
        mTextName.setText(user.getShowName());
        mTextApproveNumber.setText(user.getApproveNumber());
        mTextLoanNumber.setText(user.getProgressNumber());
        mTextHistoryNumber.setText(user.getHistoryNumber());
    }

    @Override
    public void startAuthView() {
        Intent intent = new Intent(getActivity(), AuthActivity.class);
        startActivity(intent);
    }

    @Override
    public void showMyCertificateView(User user) {
        Intent intent = new Intent(getActivity(), MyActivity.class);
        intent.putExtra("view", "certificate");
        intent.putExtra("user", user);
        startActivity(intent);
    }

    @Override
    public void showApproveView(String loanId) {
        Intent intent = new Intent(getActivity(), LoanDetailsActivity.class);
        intent.putExtra("title", getString(R.string.loan_details_approve_title));
        intent.putExtra("loanId", loanId);
        startActivity(intent);
    }

    @Override
    public void showProgressView(String loanId) {
        Intent intent = new Intent(getActivity(), LoanDetailsActivity.class);
        intent.putExtra("title", getString(R.string.loan_details_progress_title));
        intent.putExtra("loanId", loanId);
        startActivity(intent);
    }

    @Override
    public void showHistoryView() {
        Intent intent = new Intent(getActivity(), LoanDetailsActivity.class);
        intent.putExtra("title", getString(R.string.loan_details_history_title));
        startActivity(intent);
    }

    @Override
    public void showSettingsView() {
        Intent intent = new Intent(getActivity(), MyActivity.class);
        intent.putExtra("view", "settings");
        startActivity(intent);
    }

    @Override
    public void showShareView() {
        mDialog.show();
    }

    private void startHelpView() {
        LoanAuthHelpActivity.show(getActivity(), R.string.my_help, "mine/helpCenter");
    }

    private void startQRCodeView() {
        mQRCodeDialog.show();
    }

    private void startAboutView() {
        Intent intent = new Intent(getActivity(), MyActivity.class);
        intent.putExtra("view", "about");
        startActivity(intent);
    }

    private void startContactView() {

    }
}
