package com.jiaye.cashloan.view.company;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.search.SaveSalesman;
import com.jiaye.cashloan.http.data.search.SaveSalesmanRequest;
import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.persistence.Salesman;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.company.source.CompanyDataSource;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * CompanyPresenter
 *
 * @author 贾博瑄
 */

public class CompanyPresenter extends BasePresenterImpl implements CompanyContract.Presenter {

    private final CompanyContract.View mView;

    private final CompanyDataSource mDataSource;

    private Salesman mSalesman;

    public CompanyPresenter(CompanyContract.View view, CompanyDataSource dataSource) {
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
                    mView.setInitCity();
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
    public void queryCompany(String city) {
        if (!TextUtils.isEmpty(city)) {
            Disposable disposable = mDataSource.queryPeople(DbContract.Salesman.COLUMN_COMPANY, city)
                    .filter(list -> list != null && list.size() > 0)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(list -> {
                        mView.setCompanyListItemSelected(list.get(0).getCompany());
                        mView.setPersonListDataChanged(list);
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
