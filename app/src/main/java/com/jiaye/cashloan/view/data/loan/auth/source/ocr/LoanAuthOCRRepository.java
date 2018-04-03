package com.jiaye.cashloan.view.data.loan.auth.source.ocr;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.UploadClient;
import com.jiaye.cashloan.http.base.Request;
import com.jiaye.cashloan.http.data.loan.LoanIDCardAuth;
import com.jiaye.cashloan.http.data.loan.LoanIDCardAuthRequest;
import com.jiaye.cashloan.http.data.loan.LoanUploadPicture;
import com.jiaye.cashloan.http.data.loan.LoanUploadPictureRequest;
import com.jiaye.cashloan.http.data.loan.Upload;
import com.jiaye.cashloan.http.data.loan.UploadAuthRequest;
import com.jiaye.cashloan.http.tongdun.TongDunAntifraudRealName;
import com.jiaye.cashloan.http.tongdun.TongDunAntifraudResponseFunction;
import com.jiaye.cashloan.http.tongdun.TongDunClient;
import com.jiaye.cashloan.http.tongdun.TongDunOCRBack;
import com.jiaye.cashloan.http.tongdun.TongDunOCRFront;
import com.jiaye.cashloan.http.tongdun.TongDunOCRResponseFunction;
import com.jiaye.cashloan.http.utils.RequestFunction;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.utils.Base64Util;
import com.jiaye.cashloan.view.LocalException;

import org.reactivestreams.Publisher;

import java.io.File;

import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * LoanAuthOCRRepository
 *
 * @author 贾博瑄
 */

public class LoanAuthOCRRepository implements LoanAuthOCRDataSource {

    private String mBase64Front;

    private String mBase64Back;

    private String mPicFront;

    private String mPicBack;

    private String mDataFront;

    private String mDataBack;

    private LoanIDCardAuthRequest mRequest;

    @Override
    public Flowable<TongDunOCRFront> ocrFront(String path) {
        mBase64Front = Base64Util.fileToBase64(new File(path)).replace("\n", "");
        return TongDunClient.INSTANCE.getService()
                .ocrFront(BuildConfig.TONGDUN_CODE, BuildConfig.TONGDUN_KEY, mBase64Front)
                .map(new TongDunOCRResponseFunction<TongDunOCRFront>())
                .map(new Function<TongDunOCRFront, TongDunOCRFront>() {
                    @Override
                    public TongDunOCRFront apply(TongDunOCRFront tongDunOCRFront) throws Exception {
                        Gson gson = new Gson();
                        mDataFront = gson.toJson(tongDunOCRFront);
                        ContentValues values = new ContentValues();
                        values.put("ocr_id", tongDunOCRFront.getIdNumber());
                        values.put("ocr_name", tongDunOCRFront.getName());
                        values.put("ocr_birthday", tongDunOCRFront.getBirthday());
                        values.put("ocr_gender", tongDunOCRFront.getGender());
                        values.put("ocr_nation", tongDunOCRFront.getNation());
                        values.put("ocr_address", tongDunOCRFront.getAddress());
                        LoanApplication.getInstance().getSQLiteDatabase().update("user", values, null, null);
                        return tongDunOCRFront;
                    }
                });
    }

    @Override
    public Flowable<TongDunOCRBack> ocrBack(String path) {
        mBase64Back = Base64Util.fileToBase64(new File(path)).replace("\n", "");
        return TongDunClient.INSTANCE.getService()
                .ocrBack(BuildConfig.TONGDUN_CODE, BuildConfig.TONGDUN_KEY, mBase64Back)
                .map(new TongDunOCRResponseFunction<TongDunOCRBack>())
                .map(new Function<TongDunOCRBack, TongDunOCRBack>() {
                    @Override
                    public TongDunOCRBack apply(TongDunOCRBack tongDunOCRBack) throws Exception {
                        Gson gson = new Gson();
                        mDataBack = gson.toJson(tongDunOCRBack);
                        ContentValues values = new ContentValues();
                        values.put("ocr_date_begin", tongDunOCRBack.getDateBegin());
                        values.put("ocr_date_end", tongDunOCRBack.getDateEnd());
                        values.put("ocr_agency", tongDunOCRBack.getAgency());
                        LoanApplication.getInstance().getSQLiteDatabase().update("user", values, null, null);
                        return tongDunOCRBack;
                    }
                });
    }

    @Override
    public Flowable<TongDunAntifraudRealName> check(String id, final String name) {
        return TongDunClient.INSTANCE.getService()
                .check(BuildConfig.TONGDUN_CODE, BuildConfig.TONGDUN_KEY, BuildConfig.TONGDUN_APP_NAME, "jiayeshimng", id, name)
                .map(new TongDunAntifraudResponseFunction<TongDunAntifraudRealName>())
                .map(new Function<TongDunAntifraudRealName, TongDunAntifraudRealName>() {
                    @Override
                    public TongDunAntifraudRealName apply(TongDunAntifraudRealName realName) throws Exception {
                        if (realName.getRealNameCheck().getCode() == 0) {
                            ContentValues values = new ContentValues();
                            values.put("ocr_name", name);
                            LoanApplication.getInstance().getSQLiteDatabase().update("user", values, null, null);
                            return realName;
                        } else {
                            throw new LocalException(R.string.error_loan_auth_ocr);
                        }
                    }
                });
    }

    @Override
    public Flowable<LoanIDCardAuth> loanIDCardAuth() {
        return Flowable.zip(uploadFront(), uploadBack(), new BiFunction<LoanUploadPicture, LoanUploadPicture, LoanIDCardAuthRequest>() {
            @Override
            public LoanIDCardAuthRequest apply(LoanUploadPicture loanUploadPicture, LoanUploadPicture loanUploadPicture2) throws Exception {
                String ocrName = "";
                String ocrId = "";
                String ocrDateBegin = "";
                String ocrDateEnd = "";
                SQLiteDatabase database = LoanApplication.getInstance().getSQLiteDatabase();
                Cursor cursor = database.rawQuery("SELECT * FROM user;", null);
                if (cursor != null) {
                    if (cursor.moveToNext()) {
                        ocrName = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_NAME));
                        ocrId = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_ID));
                        ocrDateBegin = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_DATE_BEGIN));
                        ocrDateEnd = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_DATE_END));
                    }
                    cursor.close();
                }
                mRequest = new LoanIDCardAuthRequest();
                mRequest.setName(ocrName);
                mRequest.setId(ocrId);
                mRequest.setValidDate(ocrDateBegin + "-" + ocrDateEnd);
                mRequest.setPicFrontId(mPicFront);
                mRequest.setPicBackId(mPicBack);
                mRequest.setDataFront(mDataFront);
                mRequest.setDataBack(mDataBack);
                return mRequest;
            }
        })
                .map(new Function<LoanIDCardAuthRequest, UploadAuthRequest>() {
                    @Override
                    public UploadAuthRequest apply(LoanIDCardAuthRequest request) throws Exception {
                        UploadAuthRequest authRequest = new UploadAuthRequest();
                        authRequest.setName(request.getName());
                        authRequest.setId(request.getId());
                        authRequest.setValidDate(request.getValidDate());
                        authRequest.setPicFrontId(request.getPicFrontId());
                        authRequest.setPicBackId(request.getPicBackId());
                        authRequest.setDataFront(request.getDataFront());
                        authRequest.setDataBack(request.getDataBack());
                        return authRequest;
                    }
                })
                .map(new RequestFunction<UploadAuthRequest>())
                .flatMap(new Function<Request<UploadAuthRequest>, Publisher<Upload>>() {
                    @Override
                    public Publisher<Upload> apply(Request<UploadAuthRequest> request) throws Exception {
                        return UploadClient.INSTANCE.getService().uploadAuth(request);
                    }
                }).map(new Function<Upload, LoanIDCardAuthRequest>() {
                    @Override
                    public LoanIDCardAuthRequest apply(Upload upload) throws Exception {
                        return mRequest;
                    }
                }).compose(new ResponseTransformer<LoanIDCardAuthRequest, LoanIDCardAuth>("loanIDCardAuth"));
    }

    // 上传正面照片,并保存正面照片id
    private Flowable<LoanUploadPicture> uploadFront() {
        return Flowable.just(mBase64Front)
                .map(new Function<String, LoanUploadPictureRequest>() {
                    @Override
                    public LoanUploadPictureRequest apply(String base64) throws Exception {
                        return getLoanUploadPictureRequest("01", "01", base64, "front.jpg");
                    }
                })
                .compose(new ResponseTransformer<LoanUploadPictureRequest, LoanUploadPicture>("uploadPicture"))
                .map(new Function<LoanUploadPicture, LoanUploadPicture>() {
                    @Override
                    public LoanUploadPicture apply(LoanUploadPicture loanUploadPicture) throws Exception {
                        mPicFront = loanUploadPicture.getPicId();
                        return loanUploadPicture;
                    }
                });
    }

    // 上传背面照片,并保存背面照片id
    private Flowable<LoanUploadPicture> uploadBack() {
        return Flowable.just(mBase64Back)
                .map(new Function<String, LoanUploadPictureRequest>() {
                    @Override
                    public LoanUploadPictureRequest apply(String base64) throws Exception {
                        return getLoanUploadPictureRequest("01", "02", base64, "back.jpg");
                    }
                })
                .compose(new ResponseTransformer<LoanUploadPictureRequest, LoanUploadPicture>("uploadPicture"))
                .map(new Function<LoanUploadPicture, LoanUploadPicture>() {
                    @Override
                    public LoanUploadPicture apply(LoanUploadPicture loanUploadPicture) throws Exception {
                        mPicBack = loanUploadPicture.getPicId();
                        return loanUploadPicture;
                    }
                });
    }

    private LoanUploadPictureRequest getLoanUploadPictureRequest(String source, String type, String base64, String name) {
        String phone = "";
        String loanId = "";
        SQLiteDatabase database = LoanApplication.getInstance().getSQLiteDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM user;", null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                phone = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_PHONE));
                loanId = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_LOAN_ID));
            }
            cursor.close();
        }
        LoanUploadPictureRequest request = new LoanUploadPictureRequest();
        request.setPhone(phone);
        request.setSource(source);
        request.setLoanId(loanId);
        request.setType(type);
        request.setBase64(base64);
        request.setName(name);
        return request;
    }
}
