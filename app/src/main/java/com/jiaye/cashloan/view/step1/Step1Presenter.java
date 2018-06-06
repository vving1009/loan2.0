package com.jiaye.cashloan.view.step1;

import com.jiaye.cashloan.http.data.step1.Step1;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.step1.source.Step1DataSource;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Step1Presenter
 *
 * @author 贾博瑄
 */

public class Step1Presenter extends BasePresenterImpl implements Step1Contract.Presenter {

    private final Step1Contract.View mView;

    private final Step1DataSource mDataSource;

    private List<Step1> mList;

    public Step1Presenter(Step1Contract.View view, Step1DataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        Disposable disposable = mDataSource.requestStep1()
                .compose(new ViewTransformer<>())
                .subscribe(step1s -> {
                    mList = step1s;
                    mView.setList(mList);
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void onClickItem(int position) {
        switch (position) {
            case 0:
                mView.showIDView();
                break;
        }
    }
}
