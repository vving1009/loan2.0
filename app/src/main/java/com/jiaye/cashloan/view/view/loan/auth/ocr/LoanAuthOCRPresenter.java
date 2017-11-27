package com.jiaye.cashloan.view.view.loan.auth.ocr;

import android.text.TextUtils;

import com.jiaye.cashloan.http.data.loan.LoanIDCardAuth;
import com.jiaye.cashloan.utils.Base64Util;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.loan.auth.ocr.LoanAuthOCRDataSource;

import java.io.File;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

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

    public LoanAuthOCRPresenter(LoanAuthOCRContract.View view, LoanAuthOCRDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void pickFront() {
        if (TextUtils.isEmpty(mFront)) {
            mState = 1;
            mView.pickFront("/card/" + "front.jpg");
        }
    }

    @Override
    public void pickBack() {
        if (TextUtils.isEmpty(mBack)) {
            mState = 2;
            mView.pickBack("/card/" + "back.jpg");
        }
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
            mView.setButtonEnable();
        }
    }

    @Override
    public void commit() {
        // 临时测试
        Disposable disposable =
                mDataSource.loanIDCardAuth(Base64Util.fileToBase64(new File(mFront)).replace("\n", ""), Base64Util.fileToBase64(new File(mBack)).replace("\n", ""))
                        .compose(new ViewTransformer<LoanIDCardAuth>() {
                            @Override
                            public void accept() {
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

        /*Disposable disposable = Flowable.just(Base64Util.fileToBase64(new File(mFront)))
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String base64) throws Exception {
                        return base64.replace("\n", "");
                    }
                })
                .flatMap(new Function<String, Publisher<TongDunOCRFront>>() {
                    @Override
                    public Publisher<TongDunOCRFront> apply(String base64) throws Exception {
                        return mDataSource.ocrFront(BuildConfig.TONGDUN_CODE, BuildConfig.TONGDUN_KEY, base64);
                    }
                })
                .map(new Function<TongDunOCRFront, String>() {
                    @Override
                    public String apply(TongDunOCRFront tongDunOCRFront) throws Exception {
                        return Base64Util.fileToBase64(new File(mBack));
                    }
                })
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String base64) throws Exception {
                        return base64.replace("\n", "");
                    }
                })
                .flatMap(new Function<String, Publisher<TongDunOCRBack>>() {
                    @Override
                    public Publisher<TongDunOCRBack> apply(String base64) throws Exception {
                        return mDataSource.ocrBack(BuildConfig.TONGDUN_CODE, BuildConfig.TONGDUN_KEY, base64);
                    }
                }).flatMap(new Function<TongDunOCRBack, Publisher<LoanIDCardAuth>>() {
                    @Override
                    public Publisher<LoanIDCardAuth> apply(TongDunOCRBack tongDunOCRBack) throws Exception {
                        return mDataSource.loanIDCardAuth();
                    }
                })
                .compose(new ViewTransformer<LoanIDCardAuth>())
                .subscribe(new Consumer<LoanIDCardAuth>() {
                    @Override
                    public void accept(LoanIDCardAuth loanIDCardAuth) throws Exception {
                        mView.dismissProgressDialog();
                        mView.result();
                    }
                }, new ThrowableConsumer(mView));*/
        mCompositeDisposable.add(disposable);
    }
}
