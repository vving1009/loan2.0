package com.jiaye.cashloan.view.taobao.source;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.loan.SaveTaoBao;
import com.jiaye.cashloan.http.data.loan.SaveTaoBaoRequest;
import com.jiaye.cashloan.http.gongxinbao.GongXinBao;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoAuth;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoClient;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoResponse;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoResponseFunction;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoSubmitRequest;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoTokenRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * TaoBaoNormalRepository
 *
 * @author 贾博瑄
 */

public class TaoBaoNormalRepository implements TaoBaoNormalDataSource {

    private String mToken;

    private boolean mIsPollingEnd;

    private boolean isSMS;

    private boolean isIMG;

    @Override
    public Flowable<Object> requestGongXinBaoInit() {
        return Flowable.just("")
                .map(new Function<String, GongXinBaoTokenRequest>() {
                    @Override
                    public GongXinBaoTokenRequest apply(String s) throws Exception {
                        GongXinBaoTokenRequest request = new GongXinBaoTokenRequest("ecommerce");
                        String sql = "SELECT * FROM user;";
                        Cursor cursor = LoanApplication.getInstance().getSQLiteDatabase().rawQuery(sql, null);
                        if (cursor != null) {
                            if (cursor.moveToNext()) {
                                String phone = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE));
                                String name = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_NAME));
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
                .map(new GongXinBaoResponseFunction<>());
    }

    @Override
    public Flowable<GongXinBao> requestSMS() {
        return GongXinBaoClient.INSTANCE.getService()
                .refreshEcommerceSmsCode(mToken)
                .map(new GongXinBaoResponseFunction<GongXinBao>());
    }

    @Override
    public Flowable<GongXinBao> requestIMG() {
        return GongXinBaoClient.INSTANCE.getService()
                .refreshEcommerceVerifyCode(mToken)
                .map(new GongXinBaoResponseFunction<GongXinBao>());
    }

    @Override
    public Flowable<GongXinBao> requestSubmit(String account, String password) {
        GongXinBaoSubmitRequest request = new GongXinBaoSubmitRequest();
        request.setUsername(account);
        request.setPassword(password);
        return GongXinBaoClient.INSTANCE.getService()
                .ecommerceLogin(mToken, request)
                .map(new GongXinBaoResponseFunction<GongXinBao>());
    }

    @Override
    public Flowable<GongXinBao> requestSubmitSecond(String sms, String img) {
        GongXinBaoSubmitRequest request = new GongXinBaoSubmitRequest();
        if (isSMS) {
            request.setCode(sms);
        } else if (isIMG) {
            request.setCode(img);
        }
        return GongXinBaoClient.INSTANCE.getService()
                .ecommerceSecond(mToken, request)
                .map(new GongXinBaoResponseFunction<GongXinBao>())
                .map(new Function<GongXinBao, GongXinBao>() {
                    @Override
                    public GongXinBao apply(GongXinBao gongXinBao) throws Exception {
                        isSMS = false;
                        isIMG = false;
                        return gongXinBao;
                    }
                });
    }

    @Override
    public Flowable<GongXinBao> requestTaoBaoLoginStatus() {
        mIsPollingEnd = false;
        return GongXinBaoClient.INSTANCE.getService()
                .getEcommerceLoginStatus(mToken)
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
                                isIMG = true;
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
                                isSMS = true;
                                break;
                            case "IMAGE_VERIFY_NEW":
                                mIsPollingEnd = true;
                                isIMG = true;
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
                });
    }

    @Override
    public Flowable<SaveTaoBao> requestSaveTaoBao(String token) {
        String loanId = "";
        SQLiteDatabase database = LoanApplication.getInstance().getSQLiteDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM user;", null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                loanId = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_LOAN_ID));
            }
            cursor.close();
        }
        SaveTaoBaoRequest request = new SaveTaoBaoRequest();
        request.setLoanId(loanId);
        request.setToken(token);
        return Flowable.just(request)
                .compose(new SatcatcheResponseTransformer<SaveTaoBaoRequest, SaveTaoBao>
                        ("saveTaoBao"));
    }
}
