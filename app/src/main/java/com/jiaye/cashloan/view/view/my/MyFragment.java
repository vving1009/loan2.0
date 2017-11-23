package com.jiaye.cashloan.view.view.my;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.data.auth.User;
import com.jiaye.cashloan.view.data.my.source.MyRepository;
import com.jiaye.cashloan.view.view.auth.AuthActivity;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import java.io.ByteArrayOutputStream;

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
        mDialog = new BottomSheetDialog(getActivity());
        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.share_layout, null);
        layout.findViewById(R.id.text_wechat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wx(SendMessageToWX.Req.WXSceneSession);
            }
        });
        layout.findViewById(R.id.text_moments).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wx(SendMessageToWX.Req.WXSceneSession);
            }
        });
        layout.findViewById(R.id.text_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.setContentView(layout);
        mPresenter = new MyPresenter(this, new MyRepository());
        mPresenter.subscribe();
        return root;
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
        mDialog.show();
    }

    private void wx(int type) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://www.qq.com";
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title WebPage Title Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
        msg.description = "WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description WebPage Description Very Long Very Long Very Long Very Long Very Long Very Long Very Long";
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.icon);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
        bmp.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = type;
        //LoanApplication.getInstance().getIWXAPI().sendReq(req);
        LoanApplication.getInstance().getIWXAPI().openWXApp();
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
