package com.jiaye.cashloan.view.data.loan.auth.source.phone;

import android.database.Cursor;
import android.graphics.Bitmap;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.UploadClient;
import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.data.loan.SavePhone;
import com.jiaye.cashloan.http.data.loan.SavePhoneRequest;
import com.jiaye.cashloan.http.data.loan.Upload;
import com.jiaye.cashloan.http.data.loan.UploadOperatorRequest;
import com.jiaye.cashloan.http.gongxinbao.GongXinBao;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoAuth;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoClient;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoOperatorsConfig;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoResponse;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoResponseFunction;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoSubmitRequest;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoTokenRequest;
import com.jiaye.cashloan.http.utils.RequestFunction;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.utils.Base64Util;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Function;

/**
 * LoanAuthPhoneRepository
 *
 * @author 贾博瑄
 */

public class LoanAuthPhoneRepository implements LoanAuthPhoneDataSource {

    private String mPhone;

    private String mOperators;

    private String mPassword;

    private String mToken;

    private boolean mIsPollingEnd;

    private boolean isSMS;

    private boolean isIMG;

    private SavePhoneRequest mRequest;

    @Override
    public Flowable<GongXinBaoOperatorsConfig> requestGongXinBaoOperatorsConfig() {
        return Flowable.just("")
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
                .map(new Function<GongXinBaoOperatorsConfig, GongXinBaoOperatorsConfig>() {
                    @Override
                    public GongXinBaoOperatorsConfig apply(GongXinBaoOperatorsConfig gongXinBaoOperatorsConfig) throws Exception {
                        mOperators = gongXinBaoOperatorsConfig.getOperatorType();
                        return gongXinBaoOperatorsConfig;
                    }
                });
    }

    @Override
    public Flowable<Bitmap> requestImgVerificationCode() {
        return GongXinBaoClient.INSTANCE.getService()
                .refreshOperatorVerifyCode(mToken)
                .map(new GongXinBaoResponseFunction<GongXinBao>())
                .map(new Function<GongXinBao, Bitmap>() {
                    @Override
                    public Bitmap apply(GongXinBao response) throws Exception {
                        return Base64Util.base64ToBitmap(response.getExtra().getRemark());
                    }
                });
    }

    @Override
    public Flowable<GongXinBao> requestSmsVerificationCode() {
        return GongXinBaoClient.INSTANCE.getService()
                .refreshOperatorSmsCode(mToken)
                .map(new GongXinBaoResponseFunction<GongXinBao>());
    }

    @Override
    public Flowable<GongXinBao> requestSubmit(String password, String imgCode, String smsCode) {
        mPassword = password;
        GongXinBaoSubmitRequest request = new GongXinBaoSubmitRequest();
        request.setUsername(mPhone);
        request.setPassword(mPassword);
        request.setCode(imgCode);
        request.setRandomPassword(smsCode);
        return GongXinBaoClient.INSTANCE.getService().operatorLogin(mToken, request)
                .map(new GongXinBaoResponseFunction<GongXinBao>());
    }

    @Override
    public Flowable<GongXinBao> requestOperatorLoginStatus() {
        mIsPollingEnd = false;
        return GongXinBaoClient.INSTANCE.getService()
                .getOperatorLoginStatus(mToken)
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
                .delay(1, TimeUnit.SECONDS)
                .repeatUntil(new BooleanSupplier() {
                    @Override
                    public boolean getAsBoolean() throws Exception {
                        return mIsPollingEnd;
                    }
                });
    }

    @Override
    public Flowable<GongXinBao> requestSubmitSecond(String imgCode, String smsCode) {
        GongXinBaoSubmitRequest request = new GongXinBaoSubmitRequest();
        if (isSMS) {
            request.setCode(smsCode);
        } else if (isIMG) {
            request.setCode(imgCode);
        }
        return GongXinBaoClient.INSTANCE.getService()
                .operatorSecond(mToken, request)
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
    public Flowable<SavePhone> requestSavePhone(String token) {
        mRequest = new SavePhoneRequest();
        mRequest.setToken(token);
        mRequest.setPhone(mPhone);
        mRequest.setOperator(mOperators);
        mRequest.setPassword(mPassword);
        return Flowable.just(mRequest)
                .map(new Function<SavePhoneRequest, UploadOperatorRequest>() {
                    @Override
                    public UploadOperatorRequest apply(SavePhoneRequest savePhoneRequest) throws Exception {
                        UploadOperatorRequest request = new UploadOperatorRequest();
                        request.setToken(savePhoneRequest.getToken());
                        request.setPhone(savePhoneRequest.getPhone());
                        request.setOperator(savePhoneRequest.getOperator());
                        request.setPassword(savePhoneRequest.getPassword());
                        return request;
                    }
                })
                .map(new RequestFunction<UploadOperatorRequest>())
                .flatMap(new Function<Request<UploadOperatorRequest>, Publisher<Upload>>() {
                    @Override
                    public Publisher<Upload> apply(Request<UploadOperatorRequest> request) throws Exception {
                        return UploadClient.INSTANCE.getService().uploadOperator(request);
                    }
                })
                .map(new Function<Upload, SavePhoneRequest>() {
                    @Override
                    public SavePhoneRequest apply(Upload upload) throws Exception {
                        return mRequest;
                    }
                })
                .compose(new ResponseTransformer<SavePhoneRequest, SavePhone>("savePhone"));
    }

    @Override
    public String getPhone() {
        return mPhone;
    }
}
