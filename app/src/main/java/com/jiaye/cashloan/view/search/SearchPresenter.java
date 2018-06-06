package com.jiaye.cashloan.view.search;

import android.text.TextUtils;

import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.search.source.Salesman;
import com.jiaye.cashloan.view.search.source.SearchDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * SearchPresenter
 *
 * @author 贾博�?
 */

public class SearchPresenter extends BasePresenterImpl implements SearchContract.Presenter {

    private final SearchContract.View mView;

    private final SearchDataSource mDataSource;

    private int step;

    public SearchPresenter(SearchContract.View view, SearchDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void queryCompany() {
        Disposable disposable = mDataSource.queryCompany()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mView::setCompanyListDataChanged);
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void queryPeopleByCompanyList(String column, String word) {
        Disposable disposable = mDataSource.queryPeople(column, word)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mView::setPersonListDataChanged);
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void queryPeopleBySearchView(String newText) {
        List<Salesman> Salesmen = new ArrayList<>();
        step = 0;
        if (TextUtils.isEmpty(newText)) {
            Disposable disposable = mDataSource.queryPeople(DbContract.Salesman.COLUMN_COMPANY, newText)
                    .filter(list -> list != null && list.size() != 0)
                    .doOnNext(list -> {
                        if (step == 0) {
                            step = 1;
                        }
                    })
                    .switchIfEmpty(mDataSource.queryPeople(DbContract.Salesman.COLUMN_NAME, newText))
                    .filter(list -> list != null && list.size() != 0)
                    .doOnNext(list -> {
                        if (step == 0) {
                            step = 2;
                        }
                    })
                    .switchIfEmpty(mDataSource.queryPeople(DbContract.Salesman.COLUMN_WORK_ID, newText))
                    .filter(list -> list != null && list.size() != 0)
                    .doOnNext(list -> {
                        if (step == 0) {
                            step = 3;
                        }
                    })
                    .defaultIfEmpty(Salesmen)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(list -> {
                        switch (step) {
                            case 1:
                                mView.setCompanyListItemSelected(list.get(0).getCompany());
                                mView.setPersonListDataChanged(list);
                                break;
                            case 2:
                            case 3:
                                mView.setCompanyListNoneSelected();
                                mView.setPersonListDataChanged(list);
                                break;
                            default:
                                mView.setCompanyListNoneSelected();
                                mView.setPersonListDataChanged(list);
                                break;
                        }
                    });
            mCompositeDisposable.add(disposable);
        } else {
            mView.setCompanyListNoneSelected();
            mView.setPersonListBlankContent();
        }
    }
}
