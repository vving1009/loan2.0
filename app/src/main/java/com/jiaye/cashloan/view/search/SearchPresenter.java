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

    private Salesman mSalesman;

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
