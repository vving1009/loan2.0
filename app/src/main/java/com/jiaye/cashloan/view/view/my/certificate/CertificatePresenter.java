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
                        mView.setPhone(auth.getPhone());
                        // 银行卡
                        if (auth.getBankState().equals("1")) {
                            mView.setBankStatus("已绑定");
                        } else if (auth.getBankState().equals("0")) {
                            mView.setBankStatus("未绑定");
                        } else if (auth.getBankState().equals("2")) {
                            mView.setBankStatus("绑定失败");
                        }
                        // 身份认证
                        if (auth.getCardState().equals("1")) {
                            mView.setOCRStatus("已认证");
                        } else if (auth.getCardState().equals("0")) {
                            mView.setOCRStatus("未认证");
                        } else if (auth.getCardState().equals("2")) {
                            mView.setOCRStatus("认证失败");
                        }
                        // 个人资料
                        if (auth.getPersonState().equals("1")) {
                            mView.setInfoStatus("已完善");
                        } else if (auth.getPersonState().equals("0")) {
                            mView.setInfoStatus("未完善");
                        }
                        // 手机运营商
                        if (auth.getPhoneState().equals("1")) {
                            mView.setPhoneStatus("已认证");
                        } else if (auth.getPhoneState().equals("0")) {
                            mView.setPhoneStatus("未认证");
                        } else if (auth.getPhoneState().equals("2")) {
                            mView.setPhoneStatus("认证失败");
                        }
                        // 淘宝认证
                        if (auth.getTaobaoState().equals("1")) {
                            mView.setTaoBaoStatus("已认证");
                        } else if (auth.getTaobaoState().equals("0")) {
                            mView.setTaoBaoStatus("未认证");
                        } else if (auth.getTaobaoState().equals("2")) {
                            mView.setTaoBaoStatus("认证失败");
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
