package com.jiaye.cashloan.view.file.source;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.file.SubmitUploadFileRequest;
import com.jiaye.cashloan.http.data.loan.FileState;
import com.jiaye.cashloan.http.data.loan.FileStateRequest;
import com.jiaye.cashloan.http.data.vehcile.UploadCarPapersRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.persistence.User;
import com.jiaye.cashloan.utils.DateUtil;
import com.jiaye.cashloan.utils.OssManager;
import com.jiaye.cashloan.view.file.FileContract;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * FileRepository
 *
 * @author 贾博瑄
 */
public class FileRepository implements FileDataSource {

    private String picName;

    @Override
    public Flowable<FileState> requestFileState() {
        return Flowable.just("SELECT loan_id FROM user;")
                .map(sql -> {
                    String loanId = "";
                    FileStateRequest request = new FileStateRequest();
                    SQLiteDatabase database = LoanApplication.getInstance().getSQLiteDatabase();
                    Cursor cursor = database.rawQuery(sql, null);
                    if (cursor != null) {
                        if (cursor.moveToNext()) {
                            loanId = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_LOAN_ID));
                        }
                        cursor.close();
                    }
                    request.setLoanId(loanId);
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<FileStateRequest, FileState>("fileState"));
    }

    @Override
    public Flowable<EmptyResponse> uploadFile(String folder, List<String> paths) {
        User user = LoanApplication.getInstance().getDbHelper().queryUser();
        return Flowable.fromIterable(paths)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(path -> {
                    picName = DateUtil.formatDateTimeMillis(System.currentTimeMillis()) + ".jpg";
                    String ossPath = user.getPhone() + "/" +
                            user.getLoanId() + "/" + FileContract.ROOT_FOLDER_FILE + "/" +
                            folder + "/" + picName;
                    OssManager.getInstance().upload(ossPath, path);
                    return ossPath;
                })
                .map(ossPath -> {
                    UploadCarPapersRequest request = new UploadCarPapersRequest();
                    request.setLoanId(user.getLoanId());
                    request.setPicName(picName);
                    switch (folder) {
                        case FileContract.FOLDER_WAGE:
                            request.setPicType("2");
                            break;
                        case FileContract.FOLDER_CREDIT:
                            request.setPicType("5");
                            break;
                        case FileContract.FOLDER_ADDRESS:
                            request.setPicType("4");
                            break;
                    }
                    request.setPicUrl(BuildConfig.OSS_BASE_URL + ossPath);
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<UploadCarPapersRequest, EmptyResponse>("uploadFile"));
    }

    @Override
    public Flowable<EmptyResponse> submit() {
        return Flowable.just(LoanApplication.getInstance().getDbHelper().queryUser())
                .map(user -> {
                    SubmitUploadFileRequest request = new SubmitUploadFileRequest();
                    request.setLoanId(user.getLoanId());
                    return request;
                })
                .compose(new SatcatcheResponseTransformer<SubmitUploadFileRequest, EmptyResponse>("submitUploadFile"));
    }
}
