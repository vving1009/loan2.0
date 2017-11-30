package com.jiaye.cashloan.view.view.loan.auth.phone;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;

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
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * LoanAuthPhonePresenter
 *
 * @author 贾博瑄
 */

public class LoanAuthPhonePresenter extends BasePresenterImpl implements LoanAuthPhoneContract.Presenter {

    private final LoanAuthPhoneContract.View mView;

    private String mPhone;

    private String mToken;

    private String mTips;

    private boolean mIsPollingEnd;

    private boolean isSecond;

    private boolean isSMS;

    private boolean isIMG;

    public LoanAuthPhonePresenter(LoanAuthPhoneContract.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {
        super.subscribe();
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
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<GongXinBaoOperatorsConfig, Boolean>() {
                    @Override
                    public Boolean apply(GongXinBaoOperatorsConfig operators) throws Exception {
                        mView.setPhone(String.format(LoanApplication.getInstance().getString(R.string.loan_auth_phone_phone), mPhone));
                        mView.setOperators(String.format(LoanApplication.getInstance().getString(R.string.loan_auth_phone_operators), operators.getOperatorType()));
                        GongXinBaoOperatorsConfig.LoginForms.Fields[] fields = operators.getLoginForms()[0].getFields();
                        boolean isNeedRequestImgVerificationCode = false;
                        for (GongXinBaoOperatorsConfig.LoginForms.Fields field : fields) {
                            switch (field.getField()) {
                                case "password":
                                    mView.setPasswordVisibility(View.VISIBLE);
                                    mView.setForgetPasswordVisibility(View.VISIBLE);
                                    break;
                                case "code":
                                    if (field.getFieldExtraConfig().getFieldExtraType().equals("PIC")) {
                                        mView.setImgVerificationCodeVisibility(View.VISIBLE);
                                        isNeedRequestImgVerificationCode = true;
                                    }
                                    break;
                                case "randomPassword":
                                    if (field.getFieldExtraConfig().getFieldExtraType().equals("SMS")) {
                                        mView.setSmsVerificationCodeVisibility(View.VISIBLE);
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
                .compose(new ViewTransformer<Bitmap>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        mView.dismissProgressDialog();
                        mView.setImgVerificationCode(bitmap);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void forgetPassword() {
        mView.showToast(mTips);
    }

    @Override
    public void requestSMSVerification() {
        Disposable disposable = GongXinBaoClient.INSTANCE.getService().refreshOperatorSmsCode(mToken)
                .map(new GongXinBaoResponseFunction<GongXinBao>())
                .compose(new ViewTransformer<GongXinBao>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<GongXinBao>() {
                    @Override
                    public void accept(GongXinBao response) throws Exception {
                        mView.setSmsVerificationCodeCountDown();
                        mView.dismissProgressDialog();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void requestIMGVerification() {
        Disposable disposable = GongXinBaoClient.INSTANCE.getService().refreshOperatorVerifyCode(mToken)
                .map(new GongXinBaoResponseFunction<GongXinBao>())
                .map(new Function<GongXinBao, Bitmap>() {
                    @Override
                    public Bitmap apply(GongXinBao response) throws Exception {
                        return Base64Util.base64ToBitmap(response.getExtra().getRemark());
                    }
                })
                .compose(new ViewTransformer<Bitmap>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        mView.setImgVerificationCode(bitmap);
                        mView.dismissProgressDialog();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void submit() {
        if (TextUtils.isEmpty(mView.getPassword())) {
            mView.showToastById(R.string.error_loan_phone_password);
        } else if (mView.isSmsVerificationCodeVisibility() && TextUtils.isEmpty(mView.getSmsVerificationCode())) {
            mView.showToastById(R.string.error_loan_phone_sms);
        } else if (mView.isImgVerificationCodeVisibility() && TextUtils.isEmpty(mView.getImgVerificationCode())) {
            mView.showToastById(R.string.error_loan_phone_img);
        } else {
            if (isSecond) {
                commitSecond();
            } else {
                GongXinBaoSubmitRequest request = new GongXinBaoSubmitRequest();
                request.setUsername(mPhone);
                request.setPassword(mView.getPassword());
                request.setCode(mView.getImgVerificationCode());
                request.setRandomPassword(mView.getSmsVerificationCode());
                Disposable disposable = GongXinBaoClient.INSTANCE.getService().operatorLogin(mToken, request)
                        .map(new GongXinBaoResponseFunction<GongXinBao>())
                        .compose(new ViewTransformer<GongXinBao>() {
                            @Override
                            public void accept() {
                                super.accept();
                                mView.showProgressDialog();
                            }
                        })
                        .subscribe(new Consumer<GongXinBao>() {
                            @Override
                            public void accept(GongXinBao response) throws Exception {
                                polling();
                            }
                        }, new ThrowableConsumer(mView));
                mCompositeDisposable.add(disposable);
            }
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
                                mView.showToast(response.getExtra().getRemark());
                                break;
                            case "REFRESH_IMAGE_SUCCESS":
                                isSecond = true;
                                isIMG = true;
                                // 更新图形验证码
                                mView.cleanImgVerificationCodeText();
                                mView.setImgVerificationCodeVisibility(View.VISIBLE);
                                mView.setImgVerificationCode(Base64Util.base64ToBitmap(response.getExtra().getRemark()));
                                break;
                            case "REFRESH_IMAGE_FAILED":
                                mView.showToast("系统繁忙，刷新重试");
                                break;
                            case "REFRESH_SMS_SUCCESS":
                                mView.showToast("短信发送成功");
                                break;
                            case "REFRESH_SMS_FAILED":
                                mView.showToast("系统繁忙，刷新重试");
                                break;
                            case "SMS_VERIFY_NEW":
                                isSecond = true;
                                isSMS = true;
                                // 输入收到的短信
                                mView.cleanSmsVerificationCodeText();
                                mView.setSmsVerificationCodeVisibility(View.VISIBLE);
                                break;
                            case "IMAGE_VERIFY_NEW":
                                isSecond = true;
                                isIMG = true;
                                // 更新图形验证码
                                mView.cleanImgVerificationCodeText();
                                mView.setImgVerificationCodeVisibility(View.VISIBLE);
                                mView.setImgVerificationCode(Base64Util.base64ToBitmap(response.getExtra().getRemark()));
                                break;
                            case "WAITING":
                                break;
                            case "SUCCESS":
                                if (response.getStage().equals("FINISHED")) {
                                    mView.result();
                                    // TODO: 2017/11/30 将结果告知服务器
                                    // 记录token
                                    // 原始数据拉取接口 https://prod.gxb.io/crawler/data/rawdata/{token}
                                    // 数据报告拉取接口 https://prod.gxb.io/crawler/data/report/{token}
                                }
                                break;
                            case "FAILED":
                                mView.showToast(response.getExtra().getRemark());
                                break;
                        }
                    }
                }, new ThrowableConsumer(mView), new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.dismissProgressDialog();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void commitSecond() {
        GongXinBaoSubmitRequest request = new GongXinBaoSubmitRequest();
        if (isSMS) {
            request.setCode(mView.getSmsVerificationCode());
        } else if (isIMG) {
            request.setCode(mView.getImgVerificationCode());
        }
        Disposable disposable = GongXinBaoClient.INSTANCE.getService().operatorSecond(mToken, request)
                .map(new GongXinBaoResponseFunction<GongXinBao>())
                .compose(new ViewTransformer<GongXinBao>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<GongXinBao>() {
                    @Override
                    public void accept(GongXinBao response) throws Exception {
                        isSecond = false;
                        isSMS = false;
                        isIMG = false;
                        polling();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
