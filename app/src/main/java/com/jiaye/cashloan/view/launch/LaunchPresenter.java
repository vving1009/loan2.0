package com.jiaye.cashloan.view.launch;

import com.jiaye.cashloan.BuildConfig;
import com.jiaye.cashloan.http.data.launch.CheckUpdate;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.launch.source.LaunchDataSource;

import org.reactivestreams.Publisher;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * LaunchPresenter
 *
 * @author 贾博瑄
 */

public class LaunchPresenter extends BasePresenterImpl implements LaunchContract.Presenter {

    private final LaunchContract.View mView;

    private final LaunchDataSource mDataSource;

    public LaunchPresenter(LaunchContract.View view, LaunchDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        Disposable disposable = mDataSource.requestDictionaryList()
                .flatMap((Function<Object, Publisher<CheckUpdate>>) file -> mDataSource.checkUpdate())
                .compose(new ViewTransformer<>())
                .subscribe(checkUpdate -> {
                    if (checkUpdate.getVersionCode() > BuildConfig.VERSION_CODE) {
                        mView.showUpdateView(checkUpdate);
                    } else {
                        auto();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void download() {
        Disposable disposable = mDataSource.download((bytesRead, contentLength, done) -> {
            if (!done) {
                mView.setProgress((int) (bytesRead * 100 / contentLength));
            } else {
                mView.setProgress(100);
            }
        }).compose(new ViewTransformer<>()).subscribe(mView::install, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void auto() {
        if (mDataSource.isNeedGuide()) {
            mView.showGuideView();
        } else {
            mView.showMainView();
        }
    }
}
