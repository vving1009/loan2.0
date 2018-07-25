package com.jiaye.cashloan.view.taobao;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.taobao.source.TaoBaoDataSource;

import io.reactivex.disposables.Disposable;

/**
 * TaoBaoPresenter
 *
 * @author 贾博瑄
 */

public class TaoBaoPresenter extends BasePresenterImpl implements TaoBaoContract.Presenter {

    private final TaoBaoContract.View mView;

    private final TaoBaoDataSource mDataSource;

    public TaoBaoPresenter(TaoBaoContract.View view, TaoBaoDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void requestTaoBao() {
        Disposable disposable = mDataSource.requestTaoBao()
                .compose(new ViewTransformer<EmptyResponse>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(emptyResponse -> {
                    mView.dismissProgressDialog();
                    mView.exit();
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
