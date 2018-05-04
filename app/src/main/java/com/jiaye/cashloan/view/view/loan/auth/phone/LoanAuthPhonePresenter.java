package com.jiaye.cashloan.view.view.loan.auth.phone;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.loan.SavePhone;
import com.jiaye.cashloan.http.gongxinbao.GongXinBao;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoOperatorsConfig;
import com.jiaye.cashloan.utils.Base64Util;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.loan.auth.source.phone.LoanAuthPhoneDataSource;

import org.reactivestreams.Publisher;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
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

    private final LoanAuthPhoneDataSource mDataSource;

    private boolean isSecond;

    public LoanAuthPhonePresenter(LoanAuthPhoneContract.View view, LoanAuthPhoneDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        Disposable disposable = mDataSource.requestGongXinBaoOperatorsConfig()
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<GongXinBaoOperatorsConfig, Boolean>() {
                    @Override
                    public Boolean apply(GongXinBaoOperatorsConfig operators) throws Exception {
                        mView.dismissProgressDialog();
                        mView.setPhone(String.format(LoanApplication.getInstance().getString(R.string.loan_auth_phone_phone), mDataSource.getPhone()));
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
                .flatMap(new Function<Boolean, Publisher<Bitmap>>() {
                    @Override
                    public Publisher<Bitmap> apply(Boolean aBoolean) throws Exception {
                        return mDataSource.requestImgVerificationCode();
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
    public void requestSMSVerification() {
        Disposable disposable = mDataSource.requestSmsVerificationCode()
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
        Disposable disposable = mDataSource.requestImgVerificationCode()
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
                Disposable disposable = mDataSource.requestSubmitSecond(mView.getImgVerificationCode(), mView.getSmsVerificationCode())
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
                                polling();
                            }
                        }, new ThrowableConsumer(mView));
                mCompositeDisposable.add(disposable);
            } else {
                Disposable disposable = mDataSource.requestSubmit(mView.getPassword(), mView.getImgVerificationCode(), mView.getSmsVerificationCode())
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
        Disposable disposable = mDataSource.requestOperatorLoginStatus()
                .compose(new ViewTransformer<GongXinBao>())
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
                                // 输入收到的短信
                                mView.cleanSmsVerificationCodeText();
                                mView.setSmsVerificationCodeVisibility(View.VISIBLE);
                                mView.setSmsVerificationCodeCountDown();
                                break;
                            case "IMAGE_VERIFY_NEW":
                                isSecond = true;
                                // 更新图形验证码
                                mView.cleanImgVerificationCodeText();
                                mView.setImgVerificationCodeVisibility(View.VISIBLE);
                                mView.setImgVerificationCode(Base64Util.base64ToBitmap(response.getExtra().getRemark()));
                                break;
                            case "WAITING":
                                break;
                            case "SUCCESS":
                                if (response.getStage().equals("FINISHED")) {
                                    savePhone(response.getToken());
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

    private void savePhone(String token) {
        Disposable disposable = mDataSource.requestSavePhone(token)
                .compose(new ViewTransformer<SavePhone>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<SavePhone>() {
                    @Override
                    public void accept(SavePhone savePhone) throws Exception {
                        mView.dismissProgressDialog();
                        mView.result();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
