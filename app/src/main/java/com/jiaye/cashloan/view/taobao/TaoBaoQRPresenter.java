package com.jiaye.cashloan.view.taobao;

import android.graphics.Bitmap;

import com.jiaye.cashloan.http.data.loan.SaveTaoBao;
import com.jiaye.cashloan.http.gongxinbao.GongXinBao;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.taobao.source.TaoBaoQRDataSource;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * TaoBaoQRPresenter
 *
 * @author 贾博瑄
 */

public class TaoBaoQRPresenter extends BasePresenterImpl implements TaoBaoQRContract.Presenter {

    private final TaoBaoQRContract.View mView;

    private final TaoBaoQRDataSource mDataSource;

    public TaoBaoQRPresenter(TaoBaoQRContract.View view, TaoBaoQRDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void request() {
        Disposable disposable = mDataSource.requestQRCode()
                .compose(new ViewTransformer<Bitmap>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        mView.dismissProgressDialog();
                        mView.setImg(bitmap);
                        mView.showRpc();
                        polling();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public String getRpc() {
        return mDataSource.getRpc();
    }

    private void polling() {
        Disposable disposable = mDataSource.requestTaoBaoLoginStatus()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GongXinBao>() {
                    @Override
                    public void accept(GongXinBao response) throws Exception {
                        switch (response.getPhaseStatus()) {
                            case "LOGIN_WAITING":
                                break;
                            case "LOGIN_SUCCESS":
                                break;
                            case "LOGIN_FAILED":
                                mView.showToast(response.getExtra().getRemark());
                                break;
                            case "QR_VERIFY_CONFIRMED":
                                mView.showProgressDialog();
                                break;
                            case "REFRESH_QR_CODE_SUCCESS":
                                mView.setImg(mDataSource.getBitmap());
                                break;
                            case "WAITING":
                                break;
                            case "SUCCESS":
                                if (response.getStage().equals("FINISHED")) {
                                    saveTaoBao(response.getToken());
                                }
                                break;
                            case "FAILED":
                                mView.showToast(response.getExtra().getRemark());
                                break;
                        }
                    }
                }, new ThrowableConsumer(mView), new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.dismissProgressDialog();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void saveTaoBao(String token) {
        Disposable disposable = mDataSource.requestSaveTaoBao(token)
                .compose(new ViewTransformer<SaveTaoBao>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<SaveTaoBao>() {
                    @Override
                    public void accept(SaveTaoBao savePhone) throws Exception {
                        mView.dismissProgressDialog();
                        mView.result();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }
}
