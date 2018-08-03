package com.jiaye.cashloan.view.id.source;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.id.IDRequest;
import com.jiaye.cashloan.http.data.id.IDUploadPictureRequest;
import com.jiaye.cashloan.http.tongdun.TongDunAntifraudRealName;
import com.jiaye.cashloan.http.tongdun.TongDunAntifraudResponseFunction;
import com.jiaye.cashloan.http.tongdun.TongDunClient;
import com.jiaye.cashloan.http.tongdun.TongDunOCRBack;
import com.jiaye.cashloan.http.tongdun.TongDunOCRFront;
import com.jiaye.cashloan.http.tongdun.TongDunOCRResponseFunction;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.persistence.User;
import com.jiaye.cashloan.http.OssManager;
import com.jiaye.cashloan.view.LocalException;
import com.jiaye.cashloan.view.id.IDContract;
import com.satcatche.library.utils.Base64Util;
import com.satcatche.library.utils.DateUtil;

import java.io.File;

import io.reactivex.Flowable;

/**
 * IDRepository
 *
 * @author 贾博瑄
 */

public class IDRepository implements IDDataSource {

    private String mPathFront;

    private String mPathBack;

    private String mPicFront;

    private String mPicBack;

    private User user = LoanApplication.getInstance().getDbHelper().queryUser();

    @Override
    public Flowable<TongDunOCRFront> ocrFront(String path) {
        mPathFront = path;
        String mBase64Front = Base64Util.fileToBase64(new File(path)).replace("\n", "");
        return TongDunClient.INSTANCE.getService()
                .ocrFront(BuildConfig.TONGDUN_CODE, BuildConfig.TONGDUN_KEY, mBase64Front)
                .map(new TongDunOCRResponseFunction<>())
                .map(tongDunOCRFront -> {
                    ContentValues values = new ContentValues();
                    values.put("ocr_id", tongDunOCRFront.getIdNumber());
                    values.put("ocr_name", tongDunOCRFront.getName());
                    values.put("ocr_birthday", tongDunOCRFront.getBirthday());
                    values.put("ocr_gender", tongDunOCRFront.getGender());
                    values.put("ocr_nation", tongDunOCRFront.getNation());
                    values.put("ocr_address", tongDunOCRFront.getAddress());
                    LoanApplication.getInstance().getSQLiteDatabase().update("user", values, null, null);
                    return tongDunOCRFront;
                });
    }

    @Override
    public Flowable<TongDunOCRBack> ocrBack(String path) {
        mPathBack = path;
        String mBase64Back = Base64Util.fileToBase64(new File(path)).replace("\n", "");
        return TongDunClient.INSTANCE.getService()
                .ocrBack(BuildConfig.TONGDUN_CODE, BuildConfig.TONGDUN_KEY, mBase64Back)
                .map(new TongDunOCRResponseFunction<>())
                .map(tongDunOCRBack -> {
                    ContentValues values = new ContentValues();
                    values.put("ocr_date_begin", tongDunOCRBack.getDateBegin());
                    values.put("ocr_date_end", tongDunOCRBack.getDateEnd());
                    values.put("ocr_agency", tongDunOCRBack.getAgency());
                    LoanApplication.getInstance().getSQLiteDatabase().update("user", values, null, null);
                    return tongDunOCRBack;
                });
    }

    @Override
    public Flowable<TongDunAntifraudRealName> check(String id, final String name) {
        return TongDunClient.INSTANCE.getService()
                .check(BuildConfig.TONGDUN_CODE, BuildConfig.TONGDUN_KEY, BuildConfig.TONGDUN_APP_NAME, "jiayeshimng", id, name)
                .map(new TongDunAntifraudResponseFunction<>())
                .map(realName -> {
                    if (realName.getRealNameCheck().getCode() == 0) {
                        ContentValues values = new ContentValues();
                        values.put("ocr_name", name);
                        LoanApplication.getInstance().getSQLiteDatabase().update("user", values, null, null);
                        return realName;
                    } else {
                        throw new LocalException(R.string.error_loan_auth_ocr);
                    }
                });
    }

    @Override
    public Flowable<EmptyResponse> loanIDCardAuth() {
        return Flowable.zip(uploadFront(), uploadBack(), (loanUploadPicture, loanUploadPicture2) -> {
            String loanId = "";
            String ocrId = "";
            String ocrName = "";
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
                    loanId = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_LOAN_ID));
                    ocrId = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_ID));
                    ocrName = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_OCR_NAME));
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
            IDRequest request = new IDRequest();
            request.setLoanId(loanId);
            request.setId(ocrId);
            request.setName(ocrName);
            request.setBirthday(ocrBirthday);
            request.setGender(ocrGender);
            request.setNation(ocrNation);
            request.setAddress(ocrAddress);
            request.setDateBeigin(ocrDateBegin);
            request.setDateEnd(ocrDateEnd);
            request.setAgency(ocrAgency);
            request.setPicFrontId(mPicFront);
            request.setPicBackId(mPicBack);
            return request;
        }).compose(new SatcatcheResponseTransformer<IDRequest, EmptyResponse>("loanIDCardAuth"));
    }

    // 上传正面照片,并保存正面照片id
    private Flowable<EmptyResponse> uploadFront() {
        mPicFront = DateUtil.formatDateTimeMillis(System.currentTimeMillis()) + ".jpg";
        return Flowable.just(mPathFront)
                .map(path -> {
                    String ossPath = user.getPhone() + "/" + user.getLoanId() + "/" +
                            IDContract.FOLDER_FILE + "/" + mPicFront;
                    OssManager.getInstance().upload(ossPath, path);
                    return ossPath;
                })
                .map(ossPath -> {
                    IDUploadPictureRequest request = new IDUploadPictureRequest();
                    request.setLoanId(user.getLoanId());
                    request.setPicName(mPicFront);
                    request.setPicUrl(BuildConfig.OSS_BASE_URL + ossPath);
                    request.setPicType("1");
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<IDUploadPictureRequest, EmptyResponse>("uploadIdCard"));
    }

    // 上传背面照片,并保存背面照片id
    private Flowable<EmptyResponse> uploadBack() {
        mPicBack = DateUtil.formatDateTimeMillis(System.currentTimeMillis()) + ".jpg";
        return Flowable.just(mPathBack)
                .map(path -> {
                    String ossPath = user.getPhone() + "/" + user.getLoanId() + "/" +
                            IDContract.FOLDER_FILE + "/" + mPicBack;
                    OssManager.getInstance().upload(ossPath, path);
                    return ossPath;
                })
                .map(ossPath -> {
                    IDUploadPictureRequest request = new IDUploadPictureRequest();
                    request.setLoanId(user.getLoanId());
                    request.setPicName(mPicBack);
                    request.setPicUrl(BuildConfig.OSS_BASE_URL + ossPath);
                    request.setPicType("2");
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<IDUploadPictureRequest, EmptyResponse>("uploadIdCard"));
    }
}
