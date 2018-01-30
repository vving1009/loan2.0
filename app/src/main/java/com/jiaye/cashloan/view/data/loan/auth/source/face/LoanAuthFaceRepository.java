package com.jiaye.cashloan.view.data.loan.auth.source.face;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.loan.LoanFaceAuth;
import com.jiaye.cashloan.http.data.loan.LoanFaceAuthRequest;
import com.jiaye.cashloan.http.data.loan.LoanUploadPicture;
import com.jiaye.cashloan.http.data.loan.LoanUploadPictureRequest;
import com.jiaye.cashloan.http.tongdun.TongDunClient;
import com.jiaye.cashloan.http.tongdun.TongDunFace;
import com.jiaye.cashloan.http.tongdun.TongDunFaceRequest;
import com.jiaye.cashloan.http.tongdun.TongDunOCRResponse;
import com.jiaye.cashloan.http.tongdun.TongDunOCRResponseFunction;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.utils.Base64Util;
import com.jiaye.cashloan.view.LocalException;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * LoanAuthFaceRepository
 *
 * @author 贾博瑄
 */

public class LoanAuthFaceRepository implements LoanAuthFaceDataSource {

    private float mSimilarity;

    @Override
    public Flowable<LoanFaceAuth> upload(byte[] data) {
        return Flowable.just(data)
                .map(new Function<byte[], String>() {
                    @Override
                    public String apply(byte[] bytes) throws Exception {
                        return Base64Util.encode(bytes, Base64.NO_WRAP);
                    }
                })
                .map(new Function<String, TongDunFaceRequest>() {
                    @Override
                    public TongDunFaceRequest apply(String base64) throws Exception {
                        String name = "";
                        String id = "";
                        SQLiteDatabase database = LoanApplication.getInstance().getSQLiteDatabase();
                        Cursor cursor = database.rawQuery("SELECT * FROM user;", null);
                        if (cursor != null) {
                            if (cursor.moveToNext()) {
                                name = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_NAME));
                                id = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_ID));
                            }
                            cursor.close();
                        }

                        TongDunFaceRequest request = new TongDunFaceRequest();
                        request.setName(name);
                        request.setId(id);
                        request.setBase64(base64);
                        request.setType("1");
                        return request;
                    }
                })
                .flatMap(new Function<TongDunFaceRequest, Publisher<TongDunOCRResponse<TongDunFace>>>() {
                    @Override
                    public Publisher<TongDunOCRResponse<TongDunFace>> apply(TongDunFaceRequest request) throws Exception {
                        return TongDunClient.INSTANCE.getService().face(BuildConfig.TONGDUN_CODE, BuildConfig.TONGDUN_KEY, request.getName(), request.getId(), request.getBase64(), request.getType());
                    }
                })
                .map(new TongDunOCRResponseFunction<TongDunFace>())
                .map(new Function<TongDunFace, String>() {
                    @Override
                    public String apply(TongDunFace tongDunFace) throws Exception {
                        if (tongDunFace.isPass()) {
                            mSimilarity = tongDunFace.getSimilarity();
                            return tongDunFace.getBase64();
                        }
                        throw new LocalException(R.string.error_loan_auth_face);
                    }
                })
                .map(new Function<String, LoanUploadPictureRequest>() {
                    @Override
                    public LoanUploadPictureRequest apply(String base64) throws Exception {
                        return getLoanUploadPictureRequest("02", "03", base64, "face.jpg");
                    }
                })
                .compose(new ResponseTransformer<LoanUploadPictureRequest, LoanUploadPicture>("uploadPicture"))
                .map(new Function<LoanUploadPicture, LoanFaceAuthRequest>() {
                    @Override
                    public LoanFaceAuthRequest apply(LoanUploadPicture loanUploadPicture) throws Exception {
                        LoanFaceAuthRequest request = new LoanFaceAuthRequest();
                        request.setPicId(loanUploadPicture.getPicId());
                        request.setPass(true);
                        request.setSimilarity(mSimilarity);
                        return request;
                    }
                })
                .compose(new ResponseTransformer<LoanFaceAuthRequest, LoanFaceAuth>("loanFaceAuth"));
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
