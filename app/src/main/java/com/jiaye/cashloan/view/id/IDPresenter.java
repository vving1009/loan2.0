package com.jiaye.cashloan.view.id;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.base.EmptyResponse;
import com.jiaye.cashloan.http.tongdun.TongDunOCRBack;
import com.jiaye.cashloan.http.tongdun.TongDunOCRFront;
import com.jiaye.cashloan.utils.RegexUtil;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.id.source.IDDataSource;

import org.reactivestreams.Publisher;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * IDPresenter
 *
 * @author 贾博瑄
 */

public class IDPresenter extends BasePresenterImpl implements IDContract.Presenter {

    private final IDContract.View mView;

    private final IDDataSource mDataSource;

    private int mState;

    private String mFront;

    private String mBack;

    private String mName;

    private String mId;

    private String mDate;

    public IDPresenter(IDContract.View view, IDDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void pickFront() {
        mState = 1;
        mView.pickFront("/card/" + "front.jpg");
    }

    @Override
    public void pickBack() {
        mState = 2;
        mView.pickBack("/card/" + "back.jpg");
    }

    @Override
    public void savePath(String path) {
        switch (mState) {
            case 1:
                mFront = path;
                mView.setFrontDrawable(mFront);
                break;
            case 2:
                mBack = path;
                mView.setBackDrawable(mBack);
                break;
        }
        if (!TextUtils.isEmpty(mFront) && !TextUtils.isEmpty(mBack)) {
            mView.setCheckEnable();
        }
    }

    @Override
    public void check() {
        Disposable disposable = mDataSource.ocrFront(mFront)
                .flatMap(new Function<TongDunOCRFront, Publisher<TongDunOCRBack>>() {
                    @Override
                    public Publisher<TongDunOCRBack> apply(TongDunOCRFront tongDunOCRFront) throws Exception {
                        mName = tongDunOCRFront.getName();
                        mId = tongDunOCRFront.getIdNumber();
                        return mDataSource.ocrBack(mBack);
                    }
                })
                .compose(new ViewTransformer<TongDunOCRBack>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<TongDunOCRBack>() {
                    @Override
                    public void accept(TongDunOCRBack tongDunOCRBack) throws Exception {
                        mView.dismissProgressDialog();
                        mDate = tongDunOCRBack.getDateBegin() + "-" + tongDunOCRBack.getDateEnd();
                        mView.showInfo(mName, mId, mDate);
                        mView.setSubmitEnable();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void submit() {
        if (TextUtils.isEmpty(mView.getName())) {
            mView.showToastById(R.string.error_loan_auth_ocr);
            return;
        }
        if (!mView.getIdCard().matches(RegexUtil.idCard())) {
            mView.showToastById(R.string.error_loan_auth_ocr_idcard);
            return;
        }
        if (!mView.getIdCardDate().matches(RegexUtil.idCardDate())) {
            mView.showToastById(R.string.error_loan_auth_ocr_date);
            return;
        }
        Disposable disposable = mDataSource.check(mId, mView.getName())
                .flatMap((Function<Object, Publisher<EmptyResponse>>) object -> mDataSource.loanIDCardAuth())
                .compose(new ViewTransformer<EmptyResponse>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(loanIDCardAuth -> {
                    mView.dismissProgressDialog();
                    mView.result();
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
