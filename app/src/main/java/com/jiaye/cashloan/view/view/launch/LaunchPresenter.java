package com.jiaye.cashloan.view.view.launch;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.http.data.launch.CheckUpdate;
import com.jiaye.cashloan.http.download.DownloadProgressListener;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.launch.source.LaunchDataSource;
import com.syd.oden.gesturelock.view.GesturePreference;

import org.reactivestreams.Publisher;

import java.io.File;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * LaunchPresenter
 *
 * @author 贾博瑄
 */

public class LaunchPresenter extends BasePresenterImpl implements LaunchContract.Presenter {

    private final LaunchContract.View mView;

    private final LaunchDataSource mDataSource;

    private GesturePreference mPreference;

    public LaunchPresenter(LaunchContract.View view, LaunchDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
        mPreference = new GesturePreference(LoanApplication.getInstance(), -1);
    }

    @Override
    public void subscribe() {
        super.subscribe();
        Disposable disposable = mDataSource.requestDictionaryList()
                .flatMap(new Function<Object, Publisher<CheckUpdate>>() {
                    @Override
                    public Publisher<CheckUpdate> apply(Object file) throws Exception {
                        return mDataSource.checkUpdate();
                    }
                })
                .compose(new ViewTransformer<CheckUpdate>())
                .subscribe(new Consumer<CheckUpdate>() {
                    @Override
                    public void accept(CheckUpdate checkUpdate) throws Exception {
                        if (checkUpdate.getVersionCode() > BuildConfig.VERSION_CODE) {
                            mView.showUpdateView(checkUpdate);
                        } else {
                            auto();
                        }
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void download() {
        Disposable disposable = mDataSource.download(new DownloadProgressListener() {
            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
                if (!done) {
                    mView.setProgress((int) (bytesRead * 100 / contentLength));
                } else {
                    mView.setProgress(100);
                }
            }
        }).compose(new ViewTransformer<File>()).subscribe(new Consumer<File>() {
            @Override
            public void accept(File file) throws Exception {
                mView.install(file);
            }
        }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void auto() {
        if (mDataSource.isNeedGuide()) {
            mView.showGuideView();
        } else {
            if (mPreference.ReadStringPreference().equals("null")) {
                mView.showMainView();
            } else {
                mView.showGestureView();
            }
        }
    }
}
