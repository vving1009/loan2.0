package com.jiaye.cashloan.view.data.loan.auth.source.file;

import com.jiaye.cashloan.http.data.loan.FileState;
import com.jiaye.cashloan.http.data.loan.UploadFile;

import java.util.List;

import io.reactivex.Flowable;

/**
 * LoanAuthFileDataSource
 *
 * @author 贾博瑄
 */
public interface LoanAuthFileDataSource {

    /**
     * 请求进件上传的状态
     */
    Flowable<FileState> requestFileState();

    /**
     * 请求上传文件
     */
    Flowable<UploadFile> uploadFile(int type, List<String> list);

    /**
     * 请求确认借款
     */
    Flowable<String> requestLoanConfirm();
}
