package com.jiaye.cashloan.view.bioassay;

import com.jiaye.cashloan.http.data.bioassay.Bioassay;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.bioassay.source.BioassayDataSource;

import io.reactivex.disposables.Disposable;

/**
 * BioassayPresenter
 *
 * @author 贾博瑄
 */

public class BioassayPresenter extends BasePresenterImpl implements BioassayContract.Presenter {

    private final BioassayContract.View mView;

    private final BioassayDataSource mDataSource;

    public BioassayPresenter(BioassayContract.View view, BioassayDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void submit(byte[] verification) {
        Disposable disposable = mDataSource.upload(verification)
                .compose(new ViewTransformer<Bioassay>(){
                    @Override
                    public void accept() {
                        mView.showProgressDialog();
                    }
                })
                .subscribe(loanFaceAuth -> {
                    mView.dismissProgressDialog();
                    mView.result();
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
