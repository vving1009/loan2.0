package com.jiaye.cashloan.view.view.my.certificate;

import com.jiaye.cashloan.http.data.auth.Auth;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.my.certificate.source.CertificateDataSource;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * CertificatePresenter
 *
 * @author 贾博瑄
 */

public class CertificatePresenter extends BasePresenterImpl implements CertificateContract.Presenter {

    private final CertificateContract.View mView;

    private final CertificateDataSource mDataSource;

    public CertificatePresenter(CertificateContract.View view, CertificateDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        Disposable disposable = mDataSource.requestAuth()
                .compose(new ViewTransformer<Auth>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<Auth>() {
                    @Override
                    public void accept(Auth auth) throws Exception {
                        mView.dismissProgressDialog();
                        String phone = auth.getPhone();
                        String start = phone.substring(0, 3);
                        String end = phone.substring(7, 11);
                        mView.setPhone(start + "****" + end);
                        // 身份认证
                        switch (auth.getCardState()) {
                            case 1:
                                mView.setOCRStatus("已认证");
                                break;
                            case 0:
                                mView.setOCRStatus("未认证");
                                break;
                        }
                        // 个人资料
                        switch (auth.getPersonState()) {
                            case 1:
                                mView.setInfoStatus("已完善");
                                break;
                            case 0:
                                mView.setInfoStatus("未完善");
                                break;
                        }
                        // 手机运营商
                        switch (auth.getPhoneState()) {
                            case 1:
                                mView.setPhoneStatus("已认证");
                                break;
                            case 0:
                                mView.setPhoneStatus("未认证");
                                break;
                        }
                        // 淘宝认证
                        switch (auth.getTaobaoState()) {
                            case 1:
                                mView.setTaoBaoStatus("已认证");
                                break;
                            case 0:
                                mView.setTaoBaoStatus("未认证");
                                break;
                        }
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void ocr() {
        if (mDataSource.getAuth().getCardState() == 1) {
            mView.showIdCardView();
        }
    }

    @Override
    public void info() {
        if (mDataSource.getAuth().getPersonState() == 1) {
            mView.showInfoView();
        }
    }

    @Override
    public void phone() {
        if (mDataSource.getAuth().getPhoneState() == 1) {
            mView.showOperatorView();
        }
    }
}
