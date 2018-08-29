package com.jiaye.cashloan.view.info2;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.LocalException;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.info2.source.Info2DataSource;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Info2Presenter
 *
 * @author 贾博瑄
 */

public class Info2Presenter extends BasePresenterImpl implements Info2Contract.Presenter {

    private final Info2Contract.View mView;

    private final Info2DataSource mDataSource;

    public Info2Presenter(Info2Contract.View view, Info2DataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void submit() {
        Disposable disposable = canSubmit()
                .doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {

                    }
                })
                .compose(new ViewTransformer<Boolean>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(saveContact -> {
                    mView.dismissProgressDialog();
                    mView.finish();
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    private Flowable<Boolean> canSubmit() {
        if (TextUtils.isEmpty(mView.getMarried())) {
            return Flowable.error(new LocalException(R.string.error_step3_info_married));
        } else if (TextUtils.isEmpty(mView.getEducation())) {
            return Flowable.error(new LocalException(R.string.error_step3_info_education));
        } else if (TextUtils.isEmpty(mView.getIndustry())) {
            return Flowable.error(new LocalException(R.string.error_step3_info_industry));
        } else if (TextUtils.isEmpty(mView.getYears())) {
            return Flowable.error(new LocalException(R.string.error_step3_info_years));
        } else if (TextUtils.isEmpty(mView.getJob())) {
            return Flowable.error(new LocalException(R.string.error_step3_info_job));
        } else if (TextUtils.isEmpty(mView.getSalary())) {
            return Flowable.error(new LocalException(R.string.error_step3_info_salary));
        } else if (TextUtils.isEmpty(mView.getHouse())) {
            return Flowable.error(new LocalException(R.string.error_step3_info_house));
        } else if (TextUtils.isEmpty(mView.getCreditCardNum())) {
            return Flowable.error(new LocalException(R.string.error_step3_info_card_num));
        } else if (TextUtils.isEmpty(mView.getCreditCardLimit())) {
            return Flowable.error(new LocalException(R.string.error_step3_info_card_limit));
        } else if (TextUtils.isEmpty(mView.getDescription())) {
            return Flowable.error(new LocalException(R.string.error_step3_info_description));
        } else {
            return Flowable.just(true);
        }
    }
}
