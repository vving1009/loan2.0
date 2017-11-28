package com.jiaye.cashloan.view.data.loan.auth.face;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.loan.LoanFaceAuth;
import com.jiaye.cashloan.http.data.loan.LoanFaceAuthRequest;
import com.jiaye.cashloan.http.data.loan.LoanUploadPicture;
import com.jiaye.cashloan.http.data.loan.LoanUploadPictureRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.utils.Base64Util;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * LoanAuthFaceRepository
 *
 * @author 贾博瑄
 */

public class LoanAuthFaceRepository implements LoanAuthFaceDataSource {

    @Override
    public Flowable<LoanFaceAuth> upload(byte[] data) {
        return Flowable.just(data)
                .map(new Function<byte[], String>() {
                    @Override
                    public String apply(byte[] bytes) throws Exception {
                        return Base64Util.encode(bytes).replace("\n", "");
                    }
                })
                .map(new Function<String, LoanUploadPictureRequest>() {
                    @Override
                    public LoanUploadPictureRequest apply(String base64) throws Exception {
                        LoanUploadPictureRequest request = getLoanUploadPictureRequest("02", "03", base64, "face.jpg");
                        return request;
                    }
                })
                .compose(new ResponseTransformer<LoanUploadPictureRequest, LoanUploadPicture>("uploadPicture"))
                .map(new Function<LoanUploadPicture, LoanFaceAuthRequest>() {
                    @Override
                    public LoanFaceAuthRequest apply(LoanUploadPicture loanUploadPicture) throws Exception {
                        LoanFaceAuthRequest request = new LoanFaceAuthRequest();
                        request.setPicId(loanUploadPicture.getPicId());
                        request.setPass(true);
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
