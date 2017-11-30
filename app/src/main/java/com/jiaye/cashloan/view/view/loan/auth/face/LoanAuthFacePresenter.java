package com.jiaye.cashloan.view.view.loan.auth.face;

import com.jiaye.cashloan.http.data.loan.LoanFaceAuth;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.loan.auth.source.face.LoanAuthFaceDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * LoanAuthFacePresenter
 *
 * @author 贾博瑄
 */

public class LoanAuthFacePresenter extends BasePresenterImpl implements LoanAuthFaceContract.Presenter {

    private final LoanAuthFaceContract.View mView;

    private final LoanAuthFaceDataSource mDataSource;

    public LoanAuthFacePresenter(LoanAuthFaceContract.View view, LoanAuthFaceDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void submit(byte[] verification) {
        Disposable disposable = mDataSource.upload(verification)
                .compose(new ViewTransformer<LoanFaceAuth>(){
                    @Override
                    public void accept() {
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<LoanFaceAuth>() {
                    @Override
                    public void accept(LoanFaceAuth loanFaceAuth) throws Exception {
                        mView.dismissProgressDialog();
                        mView.result();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
