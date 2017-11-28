package com.jiaye.cashloan.view.view.loan.auth;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.gongxinbao.GongXinBao;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoAuth;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoClient;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoOperatorsConfig;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoResponse;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoResponseFunction;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoSubmitRequest;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoTokenRequest;
import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.utils.Base64Util;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.widget.LoanEditText;
import com.orhanobut.logger.Logger;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * LoanAuthPhoneActivity
 *
 * @author 贾博瑄
 */

public class LoanAuthPhoneActivity extends AppCompatActivity {

    protected CompositeDisposable mCompositeDisposable;

    private String mPhone;

    private String mToken;

    private String mTips;

    private ProgressDialog mDialog;

    private TextView mTextPhone;

    private TextView mTextOperators;

    private LoanEditText mEditPassword;

    private TextView mTextForgetPassword;

    private LoanEditText mEditSmsVerificationCode;

    private LoanEditText mEditImgVerificationCode;

    private boolean mIsPollingEnd;

    private boolean isSecond;

    private boolean isSMS;

    private boolean isIMG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCompositeDisposable = new CompositeDisposable();
        mDialog = new ProgressDialog(this);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        setContentView(R.layout.loan_auth_phone_activity);
        mTextPhone = (TextView) findViewById(R.id.text_phone);
        mTextOperators = (TextView) findViewById(R.id.text_operators);
        mEditPassword = (LoanEditText) findViewById(R.id.edit_password);
        mTextForgetPassword = (TextView) findViewById(R.id.text_forget_password);
        mEditSmsVerificationCode = (LoanEditText) findViewById(R.id.edit_sms_verification_code);
        mEditImgVerificationCode = (LoanEditText) findViewById(R.id.edit_img_verification_code);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mTextForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoanAuthPhoneActivity.this, mTips, Toast.LENGTH_SHORT).show();
            }
        });
        mEditSmsVerificationCode.setOnClickVerificationCode(new LoanEditText.OnClickVerificationCode() {
            @Override
            public void onClickVerificationCode() {
                requestSMSVerification();
            }
        });
        mEditImgVerificationCode.setOnClickVerificationCode(new LoanEditText.OnClickVerificationCode() {
            @Override
            public void onClickVerificationCode() {
                requestIMGVerification();
            }
        });
        findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit();
            }
        });
        request();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    private void request() {
        Disposable disposable = Flowable.just("")
                .map(new Function<String, GongXinBaoTokenRequest>() {
                    @Override
                    public GongXinBaoTokenRequest apply(String s) throws Exception {
                        GongXinBaoTokenRequest request = new GongXinBaoTokenRequest("operator_pro");
                        String sql = "SELECT * FROM user;";
                        Cursor cursor = LoanApplication.getInstance().getSQLiteDatabase().rawQuery(sql, null);
                        if (cursor != null) {
                            if (cursor.moveToNext()) {
                                mPhone = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE));
                                String name = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_NAME));
                                String ocrID = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_ID));
                                request.setPhone(mPhone);
                                request.setName(name);
                                request.setIdCard(ocrID);
                            }
                            cursor.close();
                        }
                        return request;
                    }
                })
                .flatMap(new Function<GongXinBaoTokenRequest, Publisher<GongXinBaoResponse<GongXinBaoAuth>>>() {
                    @Override
                    public Publisher<GongXinBaoResponse<GongXinBaoAuth>> apply(GongXinBaoTokenRequest request) throws Exception {
                        return GongXinBaoClient.INSTANCE.getService().auth(request);
                    }
                })
                .map(new GongXinBaoResponseFunction<GongXinBaoAuth>())
                .map(new Function<GongXinBaoAuth, GongXinBaoAuth>() {
                    @Override
                    public GongXinBaoAuth apply(GongXinBaoAuth token) throws Exception {
                        mToken = token.getToken();
                        return token;
                    }
                })
                .flatMap(new Function<GongXinBaoAuth, Publisher<GongXinBaoResponse<GongXinBaoOperatorsConfig>>>() {
                    @Override
                    public Publisher<GongXinBaoResponse<GongXinBaoOperatorsConfig>> apply(GongXinBaoAuth token) throws Exception {
                        return GongXinBaoClient.INSTANCE.getService().operatorsConfig(token.getToken(), mPhone);
                    }
                })
                .map(new GongXinBaoResponseFunction<GongXinBaoOperatorsConfig>())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        mDialog.show();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<GongXinBaoOperatorsConfig, Boolean>() {
                    @Override
                    public Boolean apply(GongXinBaoOperatorsConfig operators) throws Exception {
                        mTextPhone.setText(String.format(getString(R.string.loan_auth_phone_phone), mPhone));
                        mTextOperators.setText(String.format(getString(R.string.loan_auth_phone_operators), operators.getOperatorType()));
                        GongXinBaoOperatorsConfig.LoginForms.Fields[] fields = operators.getLoginForms()[0].getFields();
                        boolean isNeedRequestImgVerificationCode = false;
                        for (GongXinBaoOperatorsConfig.LoginForms.Fields field : fields) {
                            switch (field.getField()) {
                                case "password":
                                    mEditPassword.setVisibility(View.VISIBLE);
                                    mTextForgetPassword.setVisibility(View.VISIBLE);
                                    break;
                                case "code":
                                    if (field.getFieldExtraConfig().getFieldExtraType().equals("PIC")) {
                                        mEditImgVerificationCode.setVisibility(View.VISIBLE);
                                        isNeedRequestImgVerificationCode = true;
                                    }
                                    break;
                                case "randomPassword":
                                    if (field.getFieldExtraConfig().getFieldExtraType().equals("SMS")) {
                                        mEditSmsVerificationCode.setVisibility(View.VISIBLE);
                                    }
                                    break;
                            }
                        }
                        mTips = operators.getLoginForms()[0].getPwdResetConfig().getResetTips();
                        return isNeedRequestImgVerificationCode;
                    }
                })
                .filter(new Predicate<Boolean>() {
                    @Override
                    public boolean test(Boolean isNeedRequestImgVerificationCode) throws Exception {
                        return isNeedRequestImgVerificationCode;
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<Boolean, Publisher<GongXinBaoResponse<GongXinBao>>>() {
                    @Override
                    public Publisher<GongXinBaoResponse<GongXinBao>> apply(Boolean aBoolean) throws Exception {
                        return GongXinBaoClient.INSTANCE.getService().refreshOperatorVerifyCode(mToken);
                    }
                })
                .map(new GongXinBaoResponseFunction<GongXinBao>())
                .map(new Function<GongXinBao, Bitmap>() {
                    @Override
                    public Bitmap apply(GongXinBao response) throws Exception {
                        return Base64Util.base64ToBitmap(response.getExtra().getRemark());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        mDialog.dismiss();
                        mEditImgVerificationCode.setCode(bitmap);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mDialog.dismiss();
                        Logger.d(throwable.getMessage());
                        Toast.makeText(LoanAuthPhoneActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void requestSMSVerification() {
        Disposable disposable = GongXinBaoClient.INSTANCE.getService().refreshOperatorSmsCode(mToken)
                .compose(new ViewTransformer<GongXinBaoResponse<GongXinBao>>())
                .subscribe(new Consumer<GongXinBaoResponse<GongXinBao>>() {
                    @Override
                    public void accept(GongXinBaoResponse<GongXinBao> response) throws Exception {
                        mEditSmsVerificationCode.startCountDown();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.d(throwable.getMessage());
                        Toast.makeText(LoanAuthPhoneActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void requestIMGVerification() {
        Disposable disposable = GongXinBaoClient.INSTANCE.getService().refreshOperatorVerifyCode(mToken)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        mDialog.show();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .map(new GongXinBaoResponseFunction<GongXinBao>())
                .map(new Function<GongXinBao, Bitmap>() {
                    @Override
                    public Bitmap apply(GongXinBao response) throws Exception {
                        return Base64Util.base64ToBitmap(response.getExtra().getRemark());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        mDialog.dismiss();
                        mEditImgVerificationCode.setCode(bitmap);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.d(throwable.getMessage());
                        Toast.makeText(LoanAuthPhoneActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void commit() {
        if (isSecond) {
            commitSecond();
        } else {
            GongXinBaoSubmitRequest request = new GongXinBaoSubmitRequest();
            request.setUsername(mPhone);
            request.setPassword(mEditPassword.getText().toString());
            request.setCode(mEditImgVerificationCode.getText().toString());
            request.setRandomPassword(mEditSmsVerificationCode.getText().toString());
            Disposable disposable = GongXinBaoClient.INSTANCE.getService().operatorLogin(mToken, request)
                    .map(new GongXinBaoResponseFunction<GongXinBao>())
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Consumer<Subscription>() {
                        @Override
                        public void accept(Subscription subscription) throws Exception {
                            mDialog.show();
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<GongXinBao>() {
                        @Override
                        public void accept(GongXinBao response) throws Exception {
                            polling();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Logger.d(throwable.getMessage());
                            Toast.makeText(LoanAuthPhoneActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
            mCompositeDisposable.add(disposable);
        }
    }

    private void polling() {
        mIsPollingEnd = false;
        Disposable disposable = GongXinBaoClient.INSTANCE.getService().getOperatorLoginStatus(mToken)
                .map(new GongXinBaoResponseFunction<GongXinBao>())
                .map(new Function<GongXinBao, GongXinBao>() {
                    @Override
                    public GongXinBao apply(GongXinBao response) throws Exception {
                        switch (response.getPhaseStatus()) {
                            case "LOGIN_WAITING":
                                break;
                            case "LOGIN_SUCCESS":
                                break;
                            case "LOGIN_FAILED":
                                mIsPollingEnd = true;
                                break;
                            case "REFRESH_IMAGE_SUCCESS":
                                mIsPollingEnd = true;
                                break;
                            case "REFRESH_IMAGE_FAILED":
                                mIsPollingEnd = true;
                                break;
                            case "REFRESH_SMS_SUCCESS":
                                mIsPollingEnd = true;
                                break;
                            case "REFRESH_SMS_FAILED":
                                mIsPollingEnd = true;
                                break;
                            case "SMS_VERIFY_NEW":
                                mIsPollingEnd = true;
                                break;
                            case "IMAGE_VERIFY_NEW":
                                mIsPollingEnd = true;
                                break;
                            case "WAITING":
                                break;
                            case "SUCCESS":
                                if (response.getStage().equals("FINISHED")) {
                                    mIsPollingEnd = true;
                                }
                                break;
                            case "FAILED":
                                mIsPollingEnd = true;
                                break;
                        }
                        return response;
                    }
                })
                .subscribeOn(Schedulers.io())
                .delay(1, TimeUnit.SECONDS)
                .repeatUntil(new BooleanSupplier() {
                    @Override
                    public boolean getAsBoolean() throws Exception {
                        return mIsPollingEnd;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GongXinBao>() {
                    @Override
                    public void accept(GongXinBao response) throws Exception {
                        switch (response.getPhaseStatus()) {
                            case "LOGIN_WAITING":
                                break;
                            case "LOGIN_SUCCESS":
                                break;
                            case "LOGIN_FAILED":
                                Toast.makeText(LoanAuthPhoneActivity.this, response.getExtra().getRemark(), Toast.LENGTH_SHORT).show();
                                break;
                            case "REFRESH_IMAGE_SUCCESS":
                                isSecond = true;
                                isIMG = true;
                                // 更新图形验证码
                                mEditImgVerificationCode.setText("");
                                mEditImgVerificationCode.setVisibility(View.VISIBLE);
                                mEditImgVerificationCode.setCode(Base64Util.base64ToBitmap(response.getExtra().getRemark()));
                                break;
                            case "REFRESH_IMAGE_FAILED":
                                Toast.makeText(LoanAuthPhoneActivity.this, "系统繁忙，刷新重试", Toast.LENGTH_SHORT).show();
                                break;
                            case "REFRESH_SMS_SUCCESS":
                                Toast.makeText(LoanAuthPhoneActivity.this, "短信发送成功", Toast.LENGTH_SHORT).show();
                                break;
                            case "REFRESH_SMS_FAILED":
                                Toast.makeText(LoanAuthPhoneActivity.this, "系统繁忙，刷新重试", Toast.LENGTH_SHORT).show();
                                break;
                            case "SMS_VERIFY_NEW":
                                isSecond = true;
                                isSMS = true;
                                // 输入收到的短信
                                mEditSmsVerificationCode.setText("");
                                mEditSmsVerificationCode.setVisibility(View.VISIBLE);
                                break;
                            case "IMAGE_VERIFY_NEW":
                                isSecond = true;
                                isIMG = true;
                                // 更新图形验证码
                                mEditImgVerificationCode.setText("");
                                mEditImgVerificationCode.setVisibility(View.VISIBLE);
                                mEditImgVerificationCode.setCode(Base64Util.base64ToBitmap(response.getExtra().getRemark()));
                                break;
                            case "WAITING":
                                break;
                            case "SUCCESS":
                                if (response.getStage().equals("FINISHED")) {
                                    result();
                                    // 将结果告知服务器
                                    // 记录token
                                    // 原始数据拉取接口 https://prod.gxb.io/crawler/data/rawdata/{token}
                                    // 数据报告拉取接口 https://prod.gxb.io/crawler/data/report/{token}
                                }
                                break;
                            case "FAILED":
                                Toast.makeText(LoanAuthPhoneActivity.this, response.getExtra().getRemark(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.d(throwable.getMessage());
                        Toast.makeText(LoanAuthPhoneActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        mDialog.dismiss();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void commitSecond() {
        GongXinBaoSubmitRequest request = new GongXinBaoSubmitRequest();
        if (isSMS) {
            request.setCode(mEditSmsVerificationCode.getText().toString());
        } else if (isIMG) {
            request.setCode(mEditImgVerificationCode.getText().toString());
        }
        Disposable disposable = GongXinBaoClient.INSTANCE.getService().operatorSecond(mToken, request)
                .map(new GongXinBaoResponseFunction<GongXinBao>())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        mDialog.show();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GongXinBao>() {
                    @Override
                    public void accept(GongXinBao response) throws Exception {
                        isSecond = false;
                        isSMS = false;
                        isIMG = false;
                        polling();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.d(throwable.getMessage());
                        Toast.makeText(LoanAuthPhoneActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void result() {
        finish();
    }
}
