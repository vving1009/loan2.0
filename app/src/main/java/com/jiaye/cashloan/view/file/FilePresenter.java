package com.jiaye.cashloan.view.file;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.loan.FileState;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.file.source.FileDataSource;
import com.jph.takephoto.model.TImage;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * FilePresenter
 *
 * @author 贾博瑄
 */
public class FilePresenter extends BasePresenterImpl implements FileContract.Presenter {

    private FileContract.View mView;

    private FileDataSource mDataSource;

    private String mFolder;

    public FilePresenter(FileContract.View view, FileDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        requestFileState();
    }

    @Override
    public void setFolder(String folder) {
        mFolder = folder;
    }

    @Override
    public void upload(ArrayList<TImage> list) {
        ArrayList<String> paths = new ArrayList<>();
        for (TImage image : list) {
            String path = image.getCompressPath();
            paths.add(path);
        }
        Disposable disposable = mDataSource.uploadFile(mFolder, paths)
                .toList()
                .toFlowable()
                .flatMap((Function<List<EmptyResponse>, Publisher<FileState>>)
                        responses -> mDataSource.requestFileState())
                .compose(new ViewTransformer<FileState>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(fileState -> {
                    mView.dismissProgressDialog();
                    mView.setList(fileState.getList());
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void submit() {
        Disposable disposable = mDataSource.submit()
                .compose(new ViewTransformer<EmptyResponse>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(emptyResponse -> {
                    mView.dismissProgressDialog();
                    mView.finish();
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    private void requestFileState() {
        Disposable disposable = mDataSource.requestFileState()
                .compose(new ViewTransformer<FileState>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(fileState -> {
                    mView.dismissProgressDialog();
                    mView.setList(fileState.getList());
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
