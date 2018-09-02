package com.jiaye.cashloan.view.search;

import android.text.TextUtils;

import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.persistence.Salesman;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.search.source.SearchDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * SearchPresenter
 *
 * @author 贾博瑄
 */

public class SearchPresenter extends BasePresenterImpl implements SearchContract.Presenter {

    private final SearchContract.View mView;

    private final SearchDataSource mDataSource;

    public SearchPresenter(SearchContract.View view, SearchDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
    }

    @Override
    public void querySalesman(String newText) {
        List<Salesman> Salesmen = new ArrayList<>();
        if (!TextUtils.isEmpty(newText)) {
            Disposable disposable = mDataSource.queryPeople(DbContract.Salesman.COLUMN_NAME, newText)
                    .filter(list -> list != null && list.size() != 0)
                    .switchIfEmpty(mDataSource.queryPeople(DbContract.Salesman.COLUMN_WORK_ID, newText))
                    .filter(list -> list != null && list.size() != 0)
                    .defaultIfEmpty(Salesmen)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mView::setListDataChanged, new ThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        } else {
            mView.setListBlankContent();
        }
    }
}
