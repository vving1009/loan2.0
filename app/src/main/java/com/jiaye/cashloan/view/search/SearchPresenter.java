package com.jiaye.cashloan.view.search;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.search.SaveSalesman;
import com.jiaye.cashloan.http.data.search.SaveSalesmanRequest;
import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.persistence.Salesman;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.search.source.SearchDataSource;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * SearchPresenter
 *
 * @author 贾博瑄
 */

public class SearchPresenter extends BasePresenterImpl implements SearchContract.Presenter {

    private final SearchContract.View mView;

    private final SearchDataSource mDataSource;

    private int step;

    private Salesman mSalesman;

    public SearchPresenter(SearchContract.View view, SearchDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        Disposable disposable = mDataSource.salesman()
                .concatMap((Function<com.jiaye.cashloan.http.data.search.Salesman, Publisher<List<String>>>) search -> mDataSource.queryCompany())
                .compose(new ViewTransformer<List<String>>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(list -> {
                    mView.dismissProgressDialog();
                    mView.setCompanyListDataChanged(list);
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void queryPeopleByCompanyList(String column, String word) {
        Disposable disposable = mDataSource.queryPeople(column, word)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mView::setPersonListDataChanged, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void queryPeopleBySearchView(String newText) {
        List<com.jiaye.cashloan.persistence.Salesman> Salesmen = new ArrayList<>();
        step = 0;
        if (!TextUtils.isEmpty(newText)) {
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
                    }, new ThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        } else {
            mView.setCompanyListNoneSelected();
            mView.setPersonListBlankContent();
        }
    }

    @Override
    public void selectSalesman(Salesman salesman) {
        mSalesman = salesman;
    }

    @Override
    public void saveSalesman() {
        if (mSalesman == null) {
            mView.showToastById(R.string.search_error);
        } else {
            Disposable disposable = Flowable.just(mSalesman)
                    .flatMap((Function<Salesman, Publisher<SaveSalesman>>) salesman -> {
                        SaveSalesmanRequest request = new SaveSalesmanRequest();
                        request.setCompanyId(mSalesman.getCompanyId());
                        request.setCompanyName(mSalesman.getCompany());
                        request.setName(mSalesman.getName());
                        request.setNumber(mSalesman.getWorkId());
                        return mDataSource.saveSalesman(request);
                    })
                    .compose(new ViewTransformer<SaveSalesman>() {
                        @Override
                        public void accept() {
                            super.accept();
                            mView.showProgressDialog();
                        }
                    })
                    .subscribe(saveSalesman -> {
                        mView.dismissProgressDialog();
                        mView.showCertificationView();
                    }, new ThrowableConsumer(mView));
            mCompositeDisposable.add(disposable);
        }
    }
}
