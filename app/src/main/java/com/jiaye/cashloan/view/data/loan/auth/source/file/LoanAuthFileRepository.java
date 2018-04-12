package com.jiaye.cashloan.view.data.loan.auth.source.file;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.SatcatcheClient;
import com.jiaye.cashloan.http.SatcatcheService;
import com.jiaye.cashloan.http.base.ChildRequest;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;
import com.jiaye.cashloan.http.base.SatcatcheRequest;
import com.jiaye.cashloan.http.base.SatcatcheResponse;
import com.jiaye.cashloan.http.data.loan.FileState;
import com.jiaye.cashloan.http.data.loan.FileStateRequest;
import com.jiaye.cashloan.http.data.loan.LoanConfirm;
import com.jiaye.cashloan.http.data.loan.LoanConfirmRequest;
import com.jiaye.cashloan.http.data.loan.UploadFile;
import com.jiaye.cashloan.http.data.loan.UploadFileRequest;
import com.jiaye.cashloan.http.utils.ResponseTransformer;
import com.jiaye.cashloan.http.utils.SatcatcheRequestFunction;
import com.jiaye.cashloan.http.utils.SatcatcheResponseFunction;
import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.utils.Base64Util;

import org.reactivestreams.Publisher;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
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

    @Override
    public Flowable<String> requestLoanConfirm() {
        return Flowable.just("SELECT loan_id FROM user;")
                .map(new Function<String, LoanConfirmRequest>() {
                    @Override
                    public LoanConfirmRequest apply(String sql) throws Exception {
                        String loanId = "";
                        LoanConfirmRequest request = new LoanConfirmRequest();
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
                .compose(new ResponseTransformer<LoanConfirmRequest, LoanConfirm>("loanConfirm"))
                .flatMap(new Function<LoanConfirm, Publisher<String>>() {
                    @Override
                    public Publisher<String> apply(LoanConfirm loanConfirm) throws Exception {
                        return queryLoanId();
                    }
                });
    }

    private Flowable<String> queryLoanId() {
        return Flowable.just("SELECT loan_id FROM user;")
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String sql) throws Exception {
                        String loanId = "";
                        SQLiteDatabase database = LoanApplication.getInstance().getSQLiteDatabase();
                        Cursor cursor = database.rawQuery(sql, null);
                        if (cursor != null) {
                            if (cursor.moveToNext()) {
                                loanId = cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_LOAN_ID));
                            }
                            cursor.close();
                        }
                        return loanId;
                    }
                });
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

    private class SatcatcheResponseTransformer<Upstream extends ChildRequest, Downstream extends SatcatcheChildResponse> implements FlowableTransformer<Upstream, Downstream> {

        private String mMethodName;

        public SatcatcheResponseTransformer(String methodName) {
            mMethodName = methodName;
        }

        @Override
        public Publisher<Downstream> apply(Flowable<Upstream> upstream) {
            return upstream.map(new SatcatcheRequestFunction<Upstream>())
                    .flatMap(new Function<SatcatcheRequest<Upstream>, Publisher<SatcatcheResponse<Downstream>>>() {
                        @Override
                        public Publisher<SatcatcheResponse<Downstream>> apply(SatcatcheRequest<Upstream> upstreamRequest) throws Exception {
                            SatcatcheService service = SatcatcheClient.INSTANCE.getService();
                            Method method = service.getClass().getMethod(mMethodName, upstreamRequest.getClass());
                            //noinspection unchecked
                            return (Publisher<SatcatcheResponse<Downstream>>) method.invoke(service, upstreamRequest);
                        }
                    })
                    .map(new SatcatcheResponseFunction<Downstream>());
        }
    }
}
