package com.jiaye.cashloan.view.file.source;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.loan.FileState;
import com.jiaye.cashloan.http.data.vehcile.CarPapersState;

import java.util.List;

import io.reactivex.Flowable;

/**
 * FileDataSource
 *
 * @author 贾博瑄
 */
public interface FileDataSource {

    Flowable<FileState> requestFileState();

    Flowable<EmptyResponse> uploadFile(String folder, List<String> paths);

    Flowable<EmptyResponse> submit();
}
