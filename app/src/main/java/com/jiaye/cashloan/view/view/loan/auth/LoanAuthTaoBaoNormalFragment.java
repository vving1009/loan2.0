package com.jiaye.cashloan.view.view.loan.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.gongxinbao.GongXinBao;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoAuth;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoClient;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoResponse;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoResponseFunction;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoSubmitRequest;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoTokenRequest;
import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.utils.Base64Util;
import com.jiaye.cashloan.view.BaseFragment;
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
import io.reactivex.schedulers.Schedulers;

import static com.jiaye.cashloan.view.view.loan.auth.LoanAuthTaoBaoActivity.REQUEST_TAOBAO;

/**
 * LoanAuthTaoBaoNormalFragment
 *
 * @author 贾博瑄
 */

public class LoanAuthTaoBaoNormalFragment extends BaseFragment {

    protected CompositeDisposable mCompositeDisposable;

    private ProgressDialog mDialog;

    private LoanEditText mEditAccount;

    private LoanEditText mEditPassword;

    private LoanEditText mEditSMS;

    private LoanEditText mEditIMG;

    private String mToken;

    private boolean mIsPollingEnd;

    private boolean isSecond;

    private boolean isSMS;

    private boolean isIMG;

    public static LoanAuthTaoBaoNormalFragment newInstance() {
        Bundle args = new Bundle();
        LoanAuthTaoBaoNormalFragment fragment = new LoanAuthTaoBaoNormalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDialog = new ProgressDialog(getActivity());
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        View root = inflater.inflate(R.layout.loan_auth_taobao_normal_fragment, container, false);
        mEditAccount = (LoanEditText) root.findViewById(R.id.edit_account);
        mEditPassword = (LoanEditText) root.findViewById(R.id.edit_password);
        mEditSMS = (LoanEditText) root.findViewById(R.id.edit_sms);
        mEditIMG = (LoanEditText) root.findViewById(R.id.edit_img);
        mEditSMS.setOnClickVerificationCode(new LoanEditText.OnClickVerificationCode() {
            @Override
            public void onClickVerificationCode() {
                requestSMS();
            }
        });
        mEditIMG.setOnClickVerificationCode(new LoanEditText.OnClickVerificationCode() {
            @Override
            public void onClickVerificationCode() {
                requestIMG();
            }
        });
        root.findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCompositeDisposable = new CompositeDisposable();
        request();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    private void request() {
        Disposable disposable = Flowable.just("")
                .map(new Function<String, GongXinBaoTokenRequest>() {
                    @Override
                    public GongXinBaoTokenRequest apply(String s) throws Exception {
                        GongXinBaoTokenRequest request = new GongXinBaoTokenRequest("ecommerce");
                        String sql = "SELECT * FROM user;";
                        Cursor cursor = LoanApplication.getInstance().getSQLiteDatabase().rawQuery(sql, null);
                        if (cursor != null) {
                            if (cursor.moveToNext()) {
                                String phone = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE));
                                String name = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_NAME));
                                String ocrID = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_ID));
                                request.setPhone(phone);
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
                .flatMap(new Function<GongXinBaoAuth, Publisher<GongXinBaoResponse<Object>>>() {
                    @Override
                    public Publisher<GongXinBaoResponse<Object>> apply(GongXinBaoAuth token) throws Exception {
                        mToken = token.getToken();
                        return GongXinBaoClient.INSTANCE.getService().ecommerceConfig(mToken);
                    }
                })
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        mDialog.show();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object object) throws Exception {
                        mDialog.dismiss();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mDialog.dismiss();
                        Logger.d(throwable.getMessage());
                        Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void requestSMS() {
        Disposable disposable = GongXinBaoClient.INSTANCE.getService().refreshEcommerceSmsCode(mToken)
                .compose(new ViewTransformer<GongXinBaoResponse<GongXinBao>>())
                .subscribe(new Consumer<GongXinBaoResponse<GongXinBao>>() {
                    @Override
                    public void accept(GongXinBaoResponse<GongXinBao> response) throws Exception {
                        mEditSMS.startCountDown();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.d(throwable.getMessage());
                        Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void requestIMG() {
        Disposable disposable = GongXinBaoClient.INSTANCE.getService().refreshEcommerceVerifyCode(mToken)
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
                        mEditIMG.setCode(bitmap);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.d(throwable.getMessage());
                        Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void submit() {
        if (isSecond) {
            submitSecond();
        } else {
            GongXinBaoSubmitRequest request = new GongXinBaoSubmitRequest();
            request.setUsername(mEditAccount.getText().toString());
            request.setPassword(mEditPassword.getText().toString());
            Disposable disposable = GongXinBaoClient.INSTANCE.getService().ecommerceLogin(mToken, request)
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
                            Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
            mCompositeDisposable.add(disposable);
        }
    }

    private void submitSecond() {
        GongXinBaoSubmitRequest request = new GongXinBaoSubmitRequest();
        if (isSMS) {
            request.setCode(mEditSMS.getText().toString());
        } else if (isIMG) {
            request.setCode(mEditIMG.getText().toString());
        }
        Disposable disposable = GongXinBaoClient.INSTANCE.getService().ecommerceSecond(mToken, request)
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
                        Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void polling() {
        mIsPollingEnd = false;
        Disposable disposable = GongXinBaoClient.INSTANCE.getService().getEcommerceLoginStatus(mToken)
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
                                Toast.makeText(getActivity(), response.getExtra().getRemark(), Toast.LENGTH_SHORT).show();
                                break;
                            case "REFRESH_IMAGE_SUCCESS":
                                isSecond = true;
                                isIMG = true;
                                // 更新图形验证码
                                mEditIMG.setText("");
                                mEditIMG.setVisibility(View.VISIBLE);
                                mEditIMG.setCode(Base64Util.base64ToBitmap(response.getExtra().getRemark()));
                                break;
                            case "REFRESH_IMAGE_FAILED":
                                Toast.makeText(getActivity(), "系统繁忙，刷新重试", Toast.LENGTH_SHORT).show();
                                break;
                            case "REFRESH_SMS_SUCCESS":
                                Toast.makeText(getActivity(), "短信发送成功", Toast.LENGTH_SHORT).show();
                                break;
                            case "REFRESH_SMS_FAILED":
                                Toast.makeText(getActivity(), "系统繁忙，刷新重试", Toast.LENGTH_SHORT).show();
                                break;
                            case "SMS_VERIFY_NEW":
                                isSecond = true;
                                isSMS = true;
                                // 输入收到的短信
                                mEditSMS.setText("");
                                mEditSMS.setVisibility(View.VISIBLE);
                                break;
                            case "IMAGE_VERIFY_NEW":
                                isSecond = true;
                                isIMG = true;
                                // 更新图形验证码
                                mEditIMG.setText("");
                                mEditIMG.setVisibility(View.VISIBLE);
                                mEditIMG.setCode(Base64Util.base64ToBitmap(response.getExtra().getRemark()));
                                break;
                            case "WAITING":
                                break;
                            case "SUCCESS":
                                if (response.getStage().equals("FINISHED")) {
                                    result();
                                    // 将结果告知服务器
                                    // 记录token
                                    // 原始数据拉取接口 https://prod.gxb.io/ecommerce/data/rawdata/{token}
                                    // 数据报告拉取接口 https://prod.gxb.io/ecommerce/data/report/{token}
                                }
                                break;
                            case "FAILED":
                                Toast.makeText(getActivity(), response.getExtra().getRemark(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.d(throwable.getMessage());
                        Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        mDialog.dismiss();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void result() {
        Intent intent = new Intent();
        intent.putExtra("is_success", true);
        getActivity().setResult(REQUEST_TAOBAO, intent);
        getActivity().finish();
    }
}
