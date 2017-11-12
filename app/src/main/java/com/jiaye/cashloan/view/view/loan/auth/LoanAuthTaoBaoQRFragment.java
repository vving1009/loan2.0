package com.jiaye.cashloan.view.view.loan.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.gongxinbao.GongXinBao;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoAuth;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoClient;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoResponse;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoResponseFunction;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoTokenRequest;
import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.view.BaseFragment;
import com.orhanobut.logger.Logger;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

import java.net.URL;
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
 * LoanAuthTaoBaoQRFragment
 *
 * @author 贾博瑄
 */

public class LoanAuthTaoBaoQRFragment extends BaseFragment {

    protected CompositeDisposable mCompositeDisposable;

    private ProgressDialog mDialog;

    private ImageView mImgQRCode;

    private String mToken;

    private boolean mIsPollingEnd;

    private Bitmap mBitmap;

    public static LoanAuthTaoBaoQRFragment newInstance() {
        Bundle args = new Bundle();
        LoanAuthTaoBaoQRFragment fragment = new LoanAuthTaoBaoQRFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDialog = new ProgressDialog(getActivity());
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        View root = inflater.inflate(R.layout.loan_auth_taobao_qrcode_fragment, container, false);
        mImgQRCode = (ImageView) root.findViewById(R.id.img_qrcode);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    public void request() {
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
                .flatMap(new Function<GongXinBaoResponse<Object>, Publisher<GongXinBaoResponse<GongXinBao>>>() {
                    @Override
                    public Publisher<GongXinBaoResponse<GongXinBao>> apply(GongXinBaoResponse<Object> object) throws Exception {
                        return GongXinBaoClient.INSTANCE.getService().refreshEcommerceQRCode(mToken);
                    }
                })
                .map(new GongXinBaoResponseFunction<GongXinBao>())
                .map(new Function<GongXinBao, Bitmap>() {
                    @Override
                    public Bitmap apply(GongXinBao gongXinBao) throws Exception {
                        URL url = new URL(gongXinBao.getExtra().getQrCode().getHttpQRCode());
                        mBitmap = BitmapFactory.decodeStream(url.openStream());
                        return mBitmap;
                    }
                })
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        mDialog.show();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        mDialog.dismiss();
                        mImgQRCode.setImageBitmap(bitmap);
                        polling();
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
                        URL url = new URL(response.getExtra().getQrCode().getHttpQRCode());
                        mBitmap = BitmapFactory.decodeStream(url.openStream());
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
                                mDialog.show();
                                break;
                            case "LOGIN_SUCCESS":
                                break;
                            case "LOGIN_FAILED":
                                Toast.makeText(getActivity(), response.getExtra().getRemark(), Toast.LENGTH_SHORT).show();
                                break;
                            case "REFRESH_IMAGE_SUCCESS":
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
                                break;
                            case "IMAGE_VERIFY_NEW":
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
                        // 刷新QRCode
                        mImgQRCode.setImageBitmap(mBitmap);
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
