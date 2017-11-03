package com.jiaye.loan.cashloan.view.view.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiaye.loan.cashloan.BuildConfig;
import com.jiaye.loan.cashloan.R;
import com.jiaye.loan.cashloan.http.data.auth.login.Login;
import com.jiaye.loan.cashloan.http.data.auth.login.LoginRequest;
import com.jiaye.loan.cashloan.http.data.auth.register.Register;
import com.jiaye.loan.cashloan.http.data.auth.register.RegisterRequest;
import com.jiaye.loan.cashloan.http.data.auth.register.RegisterVerificationCode;
import com.jiaye.loan.cashloan.http.data.auth.register.RegisterVerificationCodeRequest;
import com.jiaye.loan.cashloan.http.utils.NetworkTransformer;
import com.jiaye.loan.cashloan.utils.RSAUtil;
import com.jiaye.loan.cashloan.view.BaseFragment;
import com.jiaye.loan.cashloan.view.ThrowableConsumer;
import com.jiaye.loan.cashloan.view.data.auth.User;
import com.jiaye.loan.cashloan.view.view.auth.AuthActivity;
import com.orhanobut.logger.Logger;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * MyFragment
 *
 * @author 贾博瑄
 */

public class MyFragment extends BaseFragment implements MyContract.View {

    public final static int REQUEST_CODE_AUTH = 1;

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
        mTextName = (TextView) root.findViewById(R.id.text_name);
        mTextApproveNumber = (TextView) root.findViewById(R.id.text_approve_number);
        mTextLoanNumber = (TextView) root.findViewById(R.id.text_loan_number);
        mTextHistoryNumber = (TextView) root.findViewById(R.id.text_history_number);
        root.findViewById(R.id.text_my_certificate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onClickMyCertificate();
            }
        });
        root.findViewById(R.id.layout_help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
                // startHelpView();
            }
        });
        root.findViewById(R.id.layout_qrcode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test2();
                // startQRCodeView();
            }
        });
        root.findViewById(R.id.layout_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test3();
                // startAboutView();
            }
        });
        root.findViewById(R.id.layout_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test4();
                // startContactView();
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
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.subscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(MyContract.Presenter presenter) {
        mPresenter = presenter;
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
        startActivityForResult(intent, REQUEST_CODE_AUTH);
    }

    @Override
    public void startMyCertificateView(User user) {
        Intent intent = new Intent(getContext(), OtherActivity.class);
        intent.putExtra("view", "certificate");
        intent.putExtra("user", user);
        startActivity(intent);
    }

    private void startHelpView() {

    }

    private void startQRCodeView() {

    }

    private void startAboutView() {

    }

    private void startContactView() {

    }

    private void startSettingsView() {

    }

    private void startShareView() {

    }

    private void test() {
        /*注册获取验证码*/
        RegisterVerificationCodeRequest request = new RegisterVerificationCodeRequest();
        request.setPhone("13752126558");
        Observable.just(request)
                .compose(new NetworkTransformer<RegisterVerificationCodeRequest, RegisterVerificationCode>("requestRegisterVerificationCode"))
                .subscribe(new Consumer<RegisterVerificationCode>() {
                    @Override
                    public void accept(RegisterVerificationCode registerVerificationCode) throws Exception {

                    }
                }, new ThrowableConsumer(this));
    }

    private void test2() {
        /*注册获取验证码*/
        RegisterRequest request = new RegisterRequest();
        request.setPhone("13752126558");
        request.setPassword(RSAUtil.encryptByPublicKeyToBase64("123456", BuildConfig.PUBLIC_KEY));
        request.setSmsVerificationCode("1471");
        request.setImgVerificationCode("z2e4");
        Observable.just(request)
                .compose(new NetworkTransformer<RegisterRequest, Register>("requestRegister"))
                .subscribe(new Consumer<Register>() {
                    @Override
                    public void accept(Register register) throws Exception {

                    }
                }, new ThrowableConsumer(this));
    }

    private void test3() {
        /*登录*/
        // J201711020956258921
        LoginRequest request = new LoginRequest();
        request.setPhone("13752126558");
        request.setPassword(RSAUtil.encryptByPublicKeyToBase64("123456", BuildConfig.PUBLIC_KEY));
        Observable.just(request)
                .compose(new NetworkTransformer<LoginRequest, Login>("requestLogin"))
                .subscribe(new Consumer<Login>() {
                    @Override
                    public void accept(Login login) throws Exception {

                    }
                }, new ThrowableConsumer(this));
    }

    private void test4() {
        String base64 = "MceFn7GkpxRWpmYZqB88S30Dj013EL3QmVN7i+DLuxjeh9KqV6qWvZoNGMrX+43QZE0vnbefYpTOaDnA+9mi+ob/Rbr6y5YZZcIwxWz8izwPMe6fuPtI11B/j87yANCyA+rjrgQ8WalqZzOnksfHp1ysauczZ9tekBXhxGHQnpvO8y804/Bkpj9XaqDdeJ8OUOlBYOGGX8gveHKH4g2Jh+yqfX98zUShIB78gZZgt+RScHtBvGAbElOqDIyS2lCWhxQ4qLTS56oLSndzPOczwLM25BOlpbfftynLP229fSdH9/eRLverblf3coaY5yAImJwnMZOLjTzG2rIt+flIAw==";
        String key = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALSoAL178OEPLSsEVgDYJtYzcG7z\n" +
                "4oHRaCn81bwFSftPVawze8Te28LboAvjP5cfe3opNoPyYH7N/SFPrZpj/jjeA4ZL1R/9VIXlm4J0\n" +
                "3vSeAGEMXmzV2391M+ev99ec5KuvMn1OinM06FUTBIPjAIKnIdPhKfbOUX8g6e0Nh3TLAgMBAAEC\n" +
                "gYBsKhbfXMD5j4OkuODheomuQHg2BlH9Fis+0IIMJEKKdJLAGsclNaXwwlzOIU7mpdPhbaGVWN6L\n" +
                "rbu8YR95TBteYONOeZsMDwW4xm6OOfTfh65UM3MlclJab8TS7yCav+rYBgXr24cdOsxJCZ43AnXx\n" +
                "EaubcN/YXH9GuQ8ZwczPwQJBAOdFvXRHpdRuIQQYizWGlhZTVuw6fEMVSLoLrT+OyJZnEaa5ilYI\n" +
                "xGNX2wfOq+u78GWrwkj6DSYusBeq69BjcKECQQDH+NNSAB8Jcti/kH4XVrB7fWNdKi1vDlbrmNfT\n" +
                "y5s5rnlp0bk+DCBWwek8Kmpw6REqWtlwL5dsrXA4CCAIiHHrAkBIwF+AnKlF0f8A0te31saP71eA\n" +
                "qEU+tQtTuyicvcXLylB7KhKiTc+5kIGOSy050r0kvos3ebF5OWabi2DzBNUBAkACKEIHWW78SBvk\n" +
                "fSePEuVWf7TJtYHF9+6iHgT+CO1EwwgWRyfrbnAO34qnloGNdEY2IcLEvg6xInHaeOP3k5k/AkAC\n" +
                "xSoZNeRTbhOn1LGKDA/8HW23Cx144yhqTzK+ozHL/ClGtPFeZQfd/AD3gSBAEkN8Q92CxqgGjJJg\n" +
                "dmjPaa2D";
        Logger.d(RSAUtil.decryptByPrivateKeyToString(base64, key));
    }
}
