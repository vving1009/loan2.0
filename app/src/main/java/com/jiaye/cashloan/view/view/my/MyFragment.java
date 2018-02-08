package com.jiaye.cashloan.view.view.my;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import static android.support.v4.content.ContextCompat.checkSelfPermission;

/**
 * MyFragment
 *
 * @author 贾博瑄
 */

public class MyFragment extends BaseFragment implements MyContract.View {

    private static final int REQUEST_WRITE_PERMISSION = 101;

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean grant = true;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                grant = false;
            }
        }
        if (grant) {
            switch (requestCode) {
                case REQUEST_WRITE_PERMISSION:
                    mPresenter.save();
                    break;
            }
        } else {
            showToastById(R.string.error_loan_auth_camera_and_write);
        }
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
        root.findViewById(R.id.layout_credit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.credit();
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
        View qrCode = LayoutInflater.from(getActivity()).inflate(R.layout.qrcode_dialog_layout, null);
        qrCode.findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQRCodeDialog.dismiss();
            }
        });
        qrCode.findViewById(R.id.img_qrcdoe).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (hasPermission(REQUEST_WRITE_PERMISSION)) {
                    mPresenter.save();
                }
                return true;
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
    public void showMyCertificateView() {
        Intent intent = new Intent(getActivity(), MyActivity.class);
        intent.putExtra("view", "certificate");
        startActivity(intent);
    }

    @Override
    public void showApproveView() {
        Intent intent = new Intent(getActivity(), LoanDetailsActivity.class);
        intent.putExtra("title", getString(R.string.loan_details_approve_title));
        startActivity(intent);
    }

    @Override
    public void showProgressView() {
        Intent intent = new Intent(getActivity(), LoanDetailsActivity.class);
        intent.putExtra("title", getString(R.string.loan_details_progress_title));
        startActivity(intent);
    }

    @Override
    public void showHistoryView() {
        Intent intent = new Intent(getActivity(), LoanDetailsActivity.class);
        intent.putExtra("title", getString(R.string.loan_details_history_title));
        startActivity(intent);
    }

    @Override
    public void showCreditView() {
        Intent intent = new Intent(getActivity(), MyActivity.class);
        intent.putExtra("view", "credit");
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
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getString(R.string.my_contact_phone)));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException exception) {
            // 捕获在不支持拨号功能的设备上调用拨号功能时发生的异常
            exception.printStackTrace();
        }
    }

    private boolean hasPermission(int requestCode) {
        boolean hasPermission = false;
        boolean requestWrite = checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
        if (requestWrite) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
        } else {
            hasPermission = true;
        }
        return hasPermission;
    }
}
