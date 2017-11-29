package com.jiaye.cashloan.view.view.launch;

import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.launch.source.LaunchDataSource;

import java.io.File;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

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
        Flowable<File> area = mDataSource.download("dict_cd", "area.json");
        Flowable<File> education = mDataSource.download("dict_edu", "education.json");
        Flowable<File> marriage = mDataSource.download("dict_mar", "marriage.json");
        Flowable<File> relation = mDataSource.download("dict_rel", "relation.json");
        Disposable disposable = Flowable.merge(area, education, marriage, relation)
                .compose(new ViewTransformer<File>())
                .count()
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long number) throws Exception {
                        if (mDataSource.isNeedGuide()) {
                            mView.showGuideView();
                        } else {
                            mView.showMainView();
                        }
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
