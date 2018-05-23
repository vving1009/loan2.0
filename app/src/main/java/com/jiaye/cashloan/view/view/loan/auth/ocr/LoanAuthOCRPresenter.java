package com.jiaye.cashloan.view.view.loan.auth.ocr;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.loan.LoanIDCardAuth;
import com.jiaye.cashloan.http.tongdun.TongDunOCRBack;
import com.jiaye.cashloan.http.tongdun.TongDunOCRFront;
import com.jiaye.cashloan.utils.RegexUtil;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.loan.auth.source.ocr.LoanAuthOCRDataSource;

import org.reactivestreams.Publisher;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * LoanAuthOCRPresenter
 *
 * @author 贾博瑄
 */

public class LoanAuthOCRPresenter extends BasePresenterImpl implements LoanAuthOCRContract.Presenter {

    private final LoanAuthOCRContract.View mView;

    private final LoanAuthOCRDataSource mDataSource;

    private int mState;

    private String mFront;

    private String mBack;

    private String mName;

    private String mId;

    private String mDate;

    public LoanAuthOCRPresenter(LoanAuthOCRContract.View view, LoanAuthOCRDataSource dataSource) {
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
                .flatMap(new Function<Object, Publisher<LoanIDCardAuth>>() {
                    @Override
                    public Publisher<LoanIDCardAuth> apply(Object object) throws Exception {
                        return mDataSource.loanIDCardAuth();
                    }
                })
                .compose(new ViewTransformer<LoanIDCardAuth>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<LoanIDCardAuth>() {
                    @Override
                    public void accept(LoanIDCardAuth loanIDCardAuth) throws Exception {
                        mView.dismissProgressDialog();
                        mView.result();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
