package com.jiaye.cashloan.view.info2;

import android.text.TextUtils;

import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.data.saveauth.SaveAuthRequest;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.info2.source.Info2DataSource;

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
    public void submit(SaveAuthRequest request) {
        if (!TextUtils.isEmpty(request.getMarriageMsg()) && !TextUtils.isEmpty(request.getEduMsg()) &&
                !TextUtils.isEmpty(request.getWorkIndustry()) && !TextUtils.isEmpty(request.getWorkNumber()) &&
                !TextUtils.isEmpty(request.getWorkPost()) && !TextUtils.isEmpty(request.getMonthlyIncome()) &&
                !TextUtils.isEmpty(request.getHouseProperty()) && !TextUtils.isEmpty(request.getCreditCardNum()) &&
                !TextUtils.isEmpty(request.getCreditCardQuota()) && !TextUtils.isEmpty(request.getLoanDescribe())) {
            Disposable disposable = mDataSource.submit(request)
                    .flatMap(response -> mDataSource.requestUpdateStep())
                    .compose(new ViewTransformer<EmptyResponse>() {
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
        } else {
            mView.showToast("请填写完整上述信息");
        }
    }
}
