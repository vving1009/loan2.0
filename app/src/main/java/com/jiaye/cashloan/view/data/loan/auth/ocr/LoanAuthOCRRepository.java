package com.jiaye.cashloan.view.data.loan.auth.ocr;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.loan.LoanIDCardAuth;
import com.jiaye.cashloan.http.data.loan.LoanIDCardAuthRequest;
import com.jiaye.cashloan.http.data.loan.LoanUploadPicture;
import com.jiaye.cashloan.http.data.loan.LoanUploadPictureRequest;
import com.jiaye.cashloan.http.tongdun.TongDunClient;
import com.jiaye.cashloan.http.tongdun.TongDunOCRBack;
import com.jiaye.cashloan.http.tongdun.TongDunOCRFront;
import com.jiaye.cashloan.http.tongdun.TongDunResponse;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;

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

    @Override
    public Flowable<TongDunOCRFront> ocrFront(String code, String key, String base64) {
        mBase64Front = base64;
        return TongDunClient.INSTANCE.getService()
                .ocrFront(BuildConfig.TONGDUN_CODE, BuildConfig.TONGDUN_KEY, base64)
                .map(new Function<TongDunResponse<TongDunOCRFront>, TongDunOCRFront>() {
                    @Override
                    public TongDunOCRFront apply(TongDunResponse<TongDunOCRFront> tongDunResponse) throws Exception {
                        return tongDunResponse.getBody();
                    }
                })
                .map(new Function<TongDunOCRFront, TongDunOCRFront>() {
                    @Override
                    public TongDunOCRFront apply(TongDunOCRFront tongDunOCRFront) throws Exception {
                        Gson gson = new Gson();
                        mDataFront = gson.toJson(tongDunOCRFront);
                        ContentValues values = new ContentValues();
                        values.put("name", tongDunOCRFront.getName());
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
    public Flowable<TongDunOCRBack> ocrBack(String code, String key, String base64) {
        mBase64Back = base64;
        return TongDunClient.INSTANCE.getService()
                .ocrBack(BuildConfig.TONGDUN_CODE, BuildConfig.TONGDUN_KEY, base64)
                .map(new Function<TongDunResponse<TongDunOCRBack>, TongDunOCRBack>() {
                    @Override
                    public TongDunOCRBack apply(TongDunResponse<TongDunOCRBack> tongDunResponse) throws Exception {
                        return tongDunResponse.getBody();
                    }
                })
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
                LoanIDCardAuthRequest request = new LoanIDCardAuthRequest();
                request.setName(ocrName);
                request.setId(ocrId);
                request.setValidDate(ocrDateBegin + "-" + ocrDateEnd);
                request.setPicFront(mPicFront);
                request.setPicBack(mPicBack);
                request.setDataFront(mDataFront);
                request.setDataBack(mDataBack);
                return request;
            }
        }).compose(new ResponseTransformer<LoanIDCardAuthRequest, LoanIDCardAuth>("loanIDCardAuth"));
    }

    // TODO: 2017/11/27 mock数据
    @Override
    public Flowable<LoanIDCardAuth> loanIDCardAuth(String front, String back) {
        mBase64Front = front;
        mBase64Back = back;
        return Flowable.zip(uploadFront(), uploadBack(), new BiFunction<LoanUploadPicture, LoanUploadPicture, LoanIDCardAuthRequest>() {
            @Override
            public LoanIDCardAuthRequest apply(LoanUploadPicture loanUploadPicture, LoanUploadPicture loanUploadPicture2) throws Exception {
                String ocrName = "";
                String ocrId = "";
                String ocrBirthday = "";
                String ocrGender = "";
                String ocrNation = "";
                String ocrAddress = "";
                String ocrDateBegin = "";
                String ocrDateEnd = "";
                String ocrAgency = "";
                SQLiteDatabase database = LoanApplication.getInstance().getSQLiteDatabase();
                Cursor cursor = database.rawQuery("SELECT * FROM user;", null);
                if (cursor != null) {
                    if (cursor.moveToNext()) {
                        ocrName = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_NAME));
                        ocrId = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_ID));
                        ocrBirthday = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_BIRTHDAY));
                        ocrGender = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_GENDER));
                        ocrNation = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_NATION));
                        ocrAddress = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_ADDRESS));
                        ocrDateBegin = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_DATE_BEGIN));
                        ocrDateEnd = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_DATE_END));
                        ocrAgency = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_AGENCY));
                    }
                    cursor.close();
                }
                LoanIDCardAuthRequest request = new LoanIDCardAuthRequest();
                request.setName(ocrName);
                request.setId(ocrId);
                request.setValidDate(ocrDateBegin + "-" + ocrDateEnd);
                request.setPicFront(mPicFront);
                request.setPicBack(mPicBack);

                TongDunOCRFront front = new TongDunOCRFront();
                front.setIdNumber(ocrId);
                front.setName(ocrName);
                front.setBirthday(ocrBirthday);
                front.setGender(ocrGender);
                front.setNation(ocrNation);
                front.setAddress(ocrAddress);

                TongDunOCRBack back = new TongDunOCRBack();
                back.setDateBegin(ocrDateBegin);
                back.setDateEnd(ocrDateEnd);
                back.setAgency(ocrAgency);

                request.setDataFront(new Gson().toJson(front));
                request.setDataBack(new Gson().toJson(back));
                return request;
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
