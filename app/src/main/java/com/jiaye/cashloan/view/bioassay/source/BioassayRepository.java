package com.jiaye.cashloan.view.bioassay.source;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.bioassay.Bioassay;
import com.jiaye.cashloan.http.data.bioassay.BioassayRequest;
import com.jiaye.cashloan.http.data.bioassay.BioassayUploadPictureRequest;
import com.jiaye.cashloan.http.tongdun.TongDunClient;
import com.jiaye.cashloan.http.tongdun.TongDunFace;
import com.jiaye.cashloan.http.tongdun.TongDunFaceRequest;
import com.jiaye.cashloan.http.tongdun.TongDunOCRResponse;
import com.jiaye.cashloan.http.tongdun.TongDunOCRResponseFunction;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.persistence.User;
import com.jiaye.cashloan.utils.Base64Util;
import com.jiaye.cashloan.utils.DateUtil;
import com.jiaye.cashloan.utils.OssManager;
import com.jiaye.cashloan.view.LocalException;
import com.jiaye.cashloan.view.bioassay.BioassayContract;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * BioassayRepository
 *
 * @author 贾博瑄
 */

public class BioassayRepository implements BioassayDataSource {

    private float mSimilarity;

    private String picName;

    @Override
    public Flowable<Bioassay> upload(byte[] data) {
        User user = LoanApplication.getInstance().getDbHelper().queryUser();
        return Flowable.just(data)
                .map(bytes -> Base64Util.encode(bytes, Base64.NO_WRAP))
                .map(base64 -> {
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
                })
                .flatMap((Function<TongDunFaceRequest, Publisher<TongDunOCRResponse<TongDunFace>>>) request -> TongDunClient.INSTANCE.getService().face(BuildConfig.TONGDUN_CODE, BuildConfig.TONGDUN_KEY, request.getName(), request.getId(), request.getBase64(), request.getType()))
                .map(new TongDunOCRResponseFunction<>())
                .map(tongDunFace -> {
                    if (tongDunFace.isPass()) {
                        mSimilarity = tongDunFace.getSimilarity();
                        return tongDunFace.getBase64();
                    }
                    throw new LocalException(R.string.error_loan_auth_face);
                })
                .map(Base64Util::decode)
                .map(bytes -> {
                    picName = DateUtil.formatDateTimeMillis(System.currentTimeMillis()) + ".jpg";
                    String ossPath = user.getPhone() + "/" +
                            user.getLoanId() + "/" + BioassayContract.FOLDER_FILE + "/" + picName;
                    OssManager.getInstance().upload(ossPath, bytes);
                    return ossPath;
                })
                .map(ossPath -> {
                    BioassayUploadPictureRequest request = new BioassayUploadPictureRequest();
                    request.setLoanId(user.getLoanId());
                    request.setPicName(picName);
                    request.setPicUrl(BuildConfig.OSS_BASE_URL + ossPath);
                    request.setPicType("1");
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<BioassayUploadPictureRequest, EmptyResponse>("uploadBioassay"))
                .map(response -> {
                    String loanId = "";
                    SQLiteDatabase database = LoanApplication.getInstance().getSQLiteDatabase();
                    Cursor cursor = database.rawQuery("SELECT * FROM user;", null);
                    if (cursor != null) {
                        if (cursor.moveToNext()) {
                            loanId = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_LOAN_ID));
                        }
                        cursor.close();
                    }

                    BioassayRequest request = new BioassayRequest();
                    request.setLoanId(loanId);
                    request.setPicId(picName);
                    request.setIsPass(1);
                    request.setSimilarity(mSimilarity);
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<BioassayRequest, Bioassay>("loanFaceAuth"));
    }
}
