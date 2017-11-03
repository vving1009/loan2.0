package com.jiaye.loan.cashloan.view.view.home;

import com.jiaye.loan.cashloan.view.BasePresenterImpl;
import com.jiaye.loan.cashloan.view.data.home.Card;
import com.jiaye.loan.cashloan.view.data.home.source.HomeDataSource;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * HomePresenter
 *
 * @author 贾博瑄
 */

public class HomePresenter extends BasePresenterImpl implements HomeContract.Presenter {

    private HomeContract.View mView;

    private HomeDataSource mDataSource;

    public HomePresenter(HomeContract.View view, HomeDataSource dataSource) {
        super(dataSource);
        mView = view;
        mView.setPresenter(this);
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        Disposable disposable = mDataSource.getCardList().subscribe(new Consumer<List<Card>>() {
            @Override
            public void accept(List<Card> cards) throws Exception {
                mView.setList(cards);
            }
        });
        mCompositeDisposable.add(disposable);
    }
}
