package com.jiaye.cashloan.view.data.loan.auth.source.file;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.loan.FileState;
import com.jiaye.cashloan.http.data.loan.FileStateRequest;
import com.jiaye.cashloan.http.data.loan.UploadFile;
import com.jiaye.cashloan.http.data.loan.UploadFileRequest;
import com.jiaye.cashloan.http.utils.SatcatcheResponseTransformer;
import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.utils.Base64Util;

import org.reactivestreams.Publisher;

import java.io.File;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * LoanAuthFileRepository
 *
 * @author 贾博瑄
 */
public class LoanAuthFileRepository implements LoanAuthFileDataSource {

    private int mType;

    private List<String> mList;

    private int mCount;

    @Override
    public Flowable<FileState> requestFileState() {
        return Flowable.just("SELECT loan_id FROM user;")
                .map(new Function<String, FileStateRequest>() {
                    @Override
                    public FileStateRequest apply(String sql) throws Exception {
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
                    }
                })
                .compose(new SatcatcheResponseTransformer<FileStateRequest, FileState>("fileState"));
    }

    @Override
    public Flowable<UploadFile> uploadFile(int type, List<String> list) {
        mType = type;
        mList = list;
        mCount = list.size();
        return upload();
    }

    private Flowable<UploadFile> upload() {
        UploadFileRequest request = new UploadFileRequest();
        request.setType(mType);
        return Flowable.just(request)
                // 查询loanId
                .map(new Function<UploadFileRequest, UploadFileRequest>() {
                    @Override
                    public UploadFileRequest apply(UploadFileRequest request) throws Exception {
                        String loanId = "";
                        SQLiteDatabase database = LoanApplication.getInstance().getSQLiteDatabase();
                        Cursor cursor = database.rawQuery("SELECT loan_id FROM user;", null);
                        if (cursor != null) {
                            if (cursor.moveToNext()) {
                                loanId = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_LOAN_ID));
                            }
                            cursor.close();
                        }
                        request.setLoanId(loanId);
                        return request;
                    }
                })
                // base64
                .map(new Function<UploadFileRequest, UploadFileRequest>() {
                    @Override
                    public UploadFileRequest apply(UploadFileRequest request) throws Exception {
                        String path = mList.get(mList.size() - mCount);
                        request.setBase64(Base64Util.fileToBase64(new File(path)));
                        return request;
                    }
                })
                .compose(new SatcatcheResponseTransformer<UploadFileRequest, UploadFile>("uploadFile"))
                .flatMap(new Function<UploadFile, Publisher<UploadFile>>() {
                    @Override
                    public Publisher<UploadFile> apply(UploadFile uploadFile) throws Exception {
                        mCount--;
                        if (mCount == 0) {
                            return Flowable.just(uploadFile);
                        } else {
                            return upload();
                        }
                    }
                });
    }
}
