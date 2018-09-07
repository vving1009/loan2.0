package com.jiaye.cashloan.view.loancar.company;

import android.text.TextUtils;

import com.jiaye.cashloan.persistence.DbContract;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.loancar.company.source.CompanyDataSource;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * CompanyPresenter
 *
 * @author 贾博瑄
 */

public class CompanyPresenter extends BasePresenterImpl implements CompanyContract.Presenter {

    private final CompanyContract.View mView;

    private final CompanyDataSource mDataSource;

    public CompanyPresenter(CompanyContract.View view, CompanyDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        Disposable disposable = mDataSource.queryCompany()
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
}
