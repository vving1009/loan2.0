package com.jiaye.cashloan.view.bankcard;

import com.jiaye.cashloan.http.data.my.CreditInfo;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.bankcard.source.BankCardDataSource;

import io.reactivex.disposables.Disposable;

/**
 * BankCardPresenter
 *
 * @author 贾博瑄
 */

public class BankCardPresenter extends BasePresenterImpl implements BankCardContract.Presenter {

    private BankCardContract.View mView;

    private BankCardDataSource mDataSource;

    public BankCardPresenter(BankCardContract.View view, BankCardDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void init() {
        Disposable disposable = mDataSource.creditInfo()
                .compose(new ViewTransformer<CreditInfo>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(creditInfo -> {
                    mView.dismissProgressDialog();
                    //01-未开户;02-已开户未绑卡;03-已开户已绑卡
                    switch (creditInfo.getBankStatus()) {
                        case "02":
                            // 绑卡
                            mView.showBindCardView();
                            break;
                        case "03":
                            // 解绑
                            mView.showUnbindCardView(creditInfo.getAccountName(), creditInfo.getName(), creditInfo.getBankNo());
                            break;
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
