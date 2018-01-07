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
                        // 银行卡
                        switch (auth.getBankState()) {
                            case "1":
                                mView.setBankStatus("已绑定");
                                break;
                            case "0":
                                mView.setBankStatus("未绑定");
                                break;
                            case "2":
                                mView.setBankStatus("认证失败");
                                break;
                        }
                        // 身份认证
                        switch (auth.getCardState()) {
                            case "1":
                                mView.setOCRStatus("已认证");
                                break;
                            case "0":
                                mView.setOCRStatus("未认证");
                                break;
                            case "2":
                                mView.setOCRStatus("认证失败");
                                break;
                        }
                        // 个人资料
                        switch (auth.getPersonState()) {
                            case "1":
                                mView.setInfoStatus("已完善");
                                break;
                            case "0":
                                mView.setInfoStatus("未完善");
                                break;
                            case "2":
                                mView.setInfoStatus("认证失败");
                                break;
                        }
                        // 手机运营商
                        switch (auth.getPhoneState()) {
                            case "1":
                                mView.setPhoneStatus("已认证");
                                break;
                            case "0":
                                mView.setPhoneStatus("未认证");
                                break;
                            case "2":
                                mView.setPhoneStatus("认证失败");
                                break;
                        }
                        // 淘宝认证
                        switch (auth.getTaobaoState()) {
                            case "1":
                                mView.setTaoBaoStatus("已认证");
                                break;
                            case "0":
                                mView.setTaoBaoStatus("未认证");
                                break;
                            case "2":
                                mView.setTaoBaoStatus("认证失败");
                                break;
                        }
                        // 芝麻信用
                        if (auth.getSesameState().equals("1")) {
                            mView.setSesameStatus(auth.getSesame());
                        } else if (auth.getSesameState().equals("0")) {
                            mView.setSesameStatus("未认证");
                        }
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void bank() {
        if (mDataSource.getAuth().getBankState().equals("1")) {
            mView.showBankView();
        }
    }

    @Override
    public void ocr() {
        if (mDataSource.getAuth().getCardState().equals("1")) {
            mView.showIdCardView();
        }
    }

    @Override
    public void info() {
        if (mDataSource.getAuth().getPersonState().equals("1")) {
            mView.showInfoView();
        }
    }

    @Override
    public void phone() {
        if (mDataSource.getAuth().getPhoneState().equals("1")) {
            mView.showOperatorView();
        }
    }
}
