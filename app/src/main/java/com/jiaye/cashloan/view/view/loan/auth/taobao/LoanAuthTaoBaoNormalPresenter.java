package com.jiaye.cashloan.view.view.loan.auth.taobao;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.jiaye.cashloan.http.data.loan.SaveTaoBao;
import com.jiaye.cashloan.http.gongxinbao.GongXinBao;
import com.jiaye.cashloan.utils.Base64Util;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.loan.auth.source.taobao.LoanAuthTaoBaoNormalDataSource;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * LoanAuthTaoBaoNormalPresenter
 *
 * @author 贾博瑄
 */

public class LoanAuthTaoBaoNormalPresenter extends BasePresenterImpl implements LoanAuthTaoBaoNormalContract.Presenter {

    private final LoanAuthTaoBaoNormalContract.View mView;

    private final LoanAuthTaoBaoNormalDataSource mDataSource;

    private boolean isSecond;

    public LoanAuthTaoBaoNormalPresenter(LoanAuthTaoBaoNormalContract.View view, LoanAuthTaoBaoNormalDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        Disposable disposable = mDataSource.requestGongXinBaoInit()
                .compose(new ViewTransformer<Object>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object object) throws Exception {
                        mView.dismissProgressDialog();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void requestSMS() {
        Disposable disposable = mDataSource.requestSMS()
                .compose(new ViewTransformer<GongXinBao>())
                .subscribe(new Consumer<GongXinBao>() {
                    @Override
                    public void accept(GongXinBao response) throws Exception {
                        mView.startCountDown();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void requestIMG() {
        Disposable disposable = mDataSource.requestIMG()
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
                        mView.setImg(bitmap);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void checkInput() {
        if (TextUtils.isEmpty(mView.getAccount())) {/*检测账号*/
            mView.setEnable(false);
        } else if (TextUtils.isEmpty(mView.getPassword())) {/*检测密码*/
            mView.setEnable(false);
        } else {
            mView.setEnable(true);
        }
    }

    @Override
    public void submit() {
        if (isSecond) {
            Disposable disposable = mDataSource.requestSubmitSecond(mView.getSMSCode(), mView.getImgCode())
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
            Disposable disposable = mDataSource.requestSubmit(mView.getAccount(), mView.getPassword())
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

    private void polling() {
        Disposable disposable = mDataSource.requestTaoBaoLoginStatus()
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
                                // 更新图形验证码
                                mView.cleanImgVerificationCodeText();
                                mView.setImgVerificationCodeVisibility();
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
                                mView.setSmsVerificationCodeVisibility();
                                break;
                            case "IMAGE_VERIFY_NEW":
                                isSecond = true;
                                // 更新图形验证码
                                mView.cleanImgVerificationCodeText();
                                mView.setImgVerificationCodeVisibility();
                                mView.setImgVerificationCode(Base64Util.base64ToBitmap(response.getExtra().getRemark()));
                                break;
                            case "WAITING":
                                break;
                            case "SUCCESS":
                                if (response.getStage().equals("FINISHED")) {
                                    saveTaoBao(response.getToken());
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

    private void saveTaoBao(String token) {
        Disposable disposable = mDataSource.requestSaveTaoBao(token)
                .compose(new ViewTransformer<SaveTaoBao>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<SaveTaoBao>() {
                    @Override
                    public void accept(SaveTaoBao savePhone) throws Exception {
                        mView.dismissProgressDialog();
                        mView.result();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
