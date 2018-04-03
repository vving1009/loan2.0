package com.jiaye.cashloan.view.data.loan.auth.source.taobao;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.UploadClient;
import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.data.loan.SaveTaoBao;
import com.jiaye.cashloan.http.data.loan.SaveTaoBaoRequest;
import com.jiaye.cashloan.http.data.loan.Upload;
import com.jiaye.cashloan.http.data.loan.UploadTaoBaoRequest;
import com.jiaye.cashloan.http.gongxinbao.GongXinBao;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoAuth;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoClient;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoResponse;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoResponseFunction;
import com.jiaye.cashloan.http.gongxinbao.GongXinBaoTokenRequest;
import com.jiaye.cashloan.http.utils.RequestFunction;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;

import org.reactivestreams.Publisher;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * LoanAuthTaoBaoQRRepository
 *
 * @author 贾博瑄
 */

public class LoanAuthTaoBaoQRRepository implements LoanAuthTaoBaoQRDataSource {

    private String mToken;

    private boolean mIsPollingEnd;

    private Bitmap mBitmap;

    private SaveTaoBaoRequest mRequest;

    @Override
    public Flowable<Bitmap> requestQRCode() {
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
                .flatMap(new Function<GongXinBaoResponse<Object>, Publisher<GongXinBaoResponse<GongXinBao>>>() {
                    @Override
                    public Publisher<GongXinBaoResponse<GongXinBao>> apply(GongXinBaoResponse<Object> object) throws Exception {
                        return GongXinBaoClient.INSTANCE.getService().refreshEcommerceQRCode(mToken);
                    }
                })
                .map(new GongXinBaoResponseFunction<GongXinBao>())
                .filter(new Predicate<GongXinBao>() {
                    @Override
                    public boolean test(GongXinBao response) throws Exception {
                        boolean empty = true;
                        if (response.getExtra() != null) {
                            if (response.getExtra().getQrCode() != null) {
                                if (response.getExtra().getQrCode().getHttpQRCode() != null) {
                                    empty = false;
                                }
                            }
                        }
                        return !empty;
                    }
                })
                .map(new Function<GongXinBao, Bitmap>() {
                    @Override
                    public Bitmap apply(GongXinBao gongXinBao) throws Exception {
                        URL url = new URL(gongXinBao.getExtra().getQrCode().getHttpQRCode());
                        mBitmap = BitmapFactory.decodeStream(url.openStream());
                        return mBitmap;
                    }
                });
    }

    @Override
    public Flowable<GongXinBao> requestTaoBaoLoginStatus() {
        mIsPollingEnd = false;
        return GongXinBaoClient.INSTANCE.getService().getEcommerceLoginStatus(mToken)
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
                            case "QR_VERIFY_CONFIRMED":
                                break;
                            case "REFRESH_QR_CODE_SUCCESS":
                                if (response.getExtra() != null) {
                                    if (response.getExtra().getQrCode() != null) {
                                        if (response.getExtra().getQrCode().getHttpQRCode() != null) {
                                            URL url = new URL(response.getExtra().getQrCode().getHttpQRCode());
                                            mBitmap = BitmapFactory.decodeStream(url.openStream());
                                        }
                                    }
                                }
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
    public Bitmap getBitmap() {
        return mBitmap;
    }

    @Override
    public Flowable<SaveTaoBao> requestSaveTaoBao(String token) {
        mRequest = new SaveTaoBaoRequest();
        mRequest.setToken(token);
        return Flowable.just(mRequest)
                .map(new Function<SaveTaoBaoRequest, UploadTaoBaoRequest>() {
                    @Override
                    public UploadTaoBaoRequest apply(SaveTaoBaoRequest saveTaoBaoRequest) throws Exception {
                        UploadTaoBaoRequest request = new UploadTaoBaoRequest();
                        request.setToken(saveTaoBaoRequest.getToken());
                        return request;
                    }
                })
                .map(new RequestFunction<UploadTaoBaoRequest>())
                .flatMap(new Function<Request<UploadTaoBaoRequest>, Publisher<Upload>>() {
                    @Override
                    public Publisher<Upload> apply(Request<UploadTaoBaoRequest> request) throws Exception {
                        return UploadClient.INSTANCE.getService().uploadTaoBao(request);
                    }
                })
                .map(new Function<Upload, SaveTaoBaoRequest>() {
                    @Override
                    public SaveTaoBaoRequest apply(Upload upload) throws Exception {
                        return mRequest;
                    }
                })
                .compose(new ResponseTransformer<SaveTaoBaoRequest, SaveTaoBao>("saveTaoBao"));
    }
}
