package com.jiaye.cashloan.view.view.my.certificate.operator;

import com.jiaye.cashloan.LoanApplication;
import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.my.Phone;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.my.certificate.operator.source.OperatorDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * OperatorPresenter
 *
 * @author 贾博瑄
 */

public class OperatorPresenter extends BasePresenterImpl implements OperatorContract.Presenter {

    private final OperatorContract.View mView;

    private final OperatorDataSource mDataSource;

    public OperatorPresenter(OperatorContract.View view, OperatorDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        Disposable disposable = mDataSource.phone()
                .compose(new ViewTransformer<Phone>(){
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<Phone>() {
                    @Override
                    public void accept(Phone phone) throws Exception {
                        mView.dismissProgressDialog();
                        mView.setPhone(String.format(LoanApplication.getInstance().getString(R.string.loan_auth_phone_phone), phone.getPhone()));
                        mView.setOperators(String.format(LoanApplication.getInstance().getString(R.string.loan_auth_phone_operators), phone.getOperator()));
                    }
                },new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
