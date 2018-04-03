package com.jiaye.cashloan.view.view.loan;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.loan.LoanAuth;
import com.jiaye.cashloan.http.data.loan.UploadContact;
import com.jiaye.cashloan.http.data.loan.UploadLocation;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.loan.LoanAuthModel;
import com.jiaye.cashloan.view.data.loan.source.LoanAuthDataSource;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * LoanAuthPresenter
 *
 * @author 贾博瑄
 */

public class LoanAuthPresenter extends BasePresenterImpl implements LoanAuthContract.Presenter {

    private final LoanAuthContract.View mView;

    private final LoanAuthDataSource mDataSource;

    /*身份证是否已经认证*/
    private boolean mIsCardVerify;

    public LoanAuthPresenter(LoanAuthContract.View view, LoanAuthDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void requestLoanAuth() {
        Disposable disposable = mDataSource.requestLoanAuth()
                .compose(new ViewTransformer<LoanAuth>() {
                    @Override
                    public void accept() {
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<LoanAuth>() {
                    @Override
                    public void accept(LoanAuth loanAuth) throws Exception {
                        List<LoanAuthModel> list = new ArrayList<>();
                        boolean isVerify;

                        LoanAuthModel card = new LoanAuthModel();
                        card.setIcon(R.drawable.loan_auth_ic_card);
                        card.setName(R.string.loan_auth_ocr);
                        setLoanAuthModel(loanAuth.getCardState(), card, false);
                        mIsCardVerify = card.isVerify();
                        isVerify = card.isVerify();
                        list.add(card);

                        LoanAuthModel visa = new LoanAuthModel();
                        visa.setIcon(R.drawable.loan_auth_ic_visa);
                        visa.setName(R.string.loan_auth_visa);
                        setLoanAuthModel(loanAuth.getSignState(), visa, false);
                        isVerify = isVerify && visa.isVerify();
                        list.add(visa);

                        boolean needHistory = !TextUtils.isEmpty(loanAuth.getNeedSignHistory()) && loanAuth.getNeedSignHistory().equals("1");
                        if (needHistory) {
                            LoanAuthModel visaHistory = new LoanAuthModel();
                            visaHistory.setIcon(R.drawable.loan_auth_ic_visa_history);
                            visaHistory.setName(R.string.loan_auth_visa_history);
                            setLoanAuthModel(loanAuth.getSignHistoryState(), visaHistory, false);
                            isVerify = isVerify && visaHistory.isVerify();
                            list.add(visaHistory);
                        }

                        LoanAuthModel face = new LoanAuthModel();
                        face.setIcon(R.drawable.loan_auth_ic_face);
                        face.setName(R.string.loan_auth_face);
                        setLoanAuthModel(loanAuth.getFaceState(), face, false);
                        list.add(face);

                        LoanAuthModel person = new LoanAuthModel();
                        person.setIcon(R.drawable.loan_auth_ic_person);
                        person.setName(R.string.loan_auth_info);
                        setLoanAuthModel(loanAuth.getPersonState(), person, true);
                        list.add(person);

                        LoanAuthModel phone = new LoanAuthModel();
                        phone.setIcon(R.drawable.loan_auth_ic_phone);
                        phone.setName(R.string.loan_auth_phone);
                        setLoanAuthModel(loanAuth.getPhoneState(), phone, false);
                        list.add(phone);

                        LoanAuthModel taobao = new LoanAuthModel();
                        taobao.setIcon(R.drawable.loan_auth_ic_taobao);
                        taobao.setName(R.string.loan_auth_taobao);
                        setLoanAuthModel(loanAuth.getTaobaoState(), taobao, false);
                        list.add(taobao);

                        LoanAuthModel sesame = new LoanAuthModel();
                        sesame.setIcon(R.drawable.loan_auth_ic_sesame);
                        sesame.setName(R.string.loan_auth_sesame);
                        setLoanAuthModel(loanAuth.getSesameState(), sesame, false);
                        list.add(sesame);

                        mView.setList(list);
                        if (isVerify) {
                            mView.setNextEnable();
                        }
                        mView.dismissProgressDialog();
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void selectLoanAuthModel(LoanAuthModel model) {
        /*认证失败*/
        if (model.isFailure()) {
            mView.showToastById(R.string.error_loan_auth_card_failure);
            return;
        }
        /*身份证未认证时选择其他认证*/
        if (!mIsCardVerify && model.getName() != R.string.loan_auth_ocr) {
            mView.showToastById(R.string.error_loan_auth_card_first);
            return;
        }
        if (!model.isVerify() || model.isCanModify()) {
            switch (model.getName()) {
                case R.string.loan_auth_ocr:
                    mView.showLoanAuthOCRView();
                    break;
                case R.string.loan_auth_visa:
                    mView.showLoanAuthVisaView();
                    break;
                case R.string.loan_auth_visa_history:
                    mView.showLoanAuthVisaHistoryView();
                    break;
                case R.string.loan_auth_face:
                    mView.showLoanAuthFaceView();
                    break;
                case R.string.loan_auth_info:
                    mView.showLoanAuthInfoView();
                    break;
                case R.string.loan_auth_phone:
                    mView.showLoanAuthPhoneView();
                    break;
                case R.string.loan_auth_taobao:
                    mView.showLoanAuthTaoBaoView();
                    break;
                case R.string.loan_auth_sesame:
                    mView.showLoanAuthSesameView();
                    break;
            }
        }
    }

    @Override
    public void uploadContact() {
        Disposable disposable = mDataSource.uploadContact()
                .compose(new ViewTransformer<UploadContact>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<UploadContact>() {
                    @Override
                    public void accept(UploadContact uploadContact) throws Exception {
                        Logger.d("通讯录上传成功");
                        mView.dismissProgressDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissProgressDialog();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void uploadLocation() {
        Disposable disposable = mDataSource.uploadLocation()
                .compose(new ViewTransformer<UploadLocation>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<UploadLocation>() {
                    @Override
                    public void accept(UploadLocation uploadLocation) throws Exception {
                        Logger.d("地理位置上传成功");
                        mView.dismissProgressDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissProgressDialog();
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void confirm() {
        Disposable disposable = mDataSource.requestLoanConfirm()
                .compose(new ViewTransformer<String>() {
                    @Override
                    public void accept() {
                        super.accept();
                        mView.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String loanId) throws Exception {
                        mView.dismissProgressDialog();
                        mView.showLoanProgressView(loanId);
                    }
                }, new ThrowableConsumer(mView));
        mCompositeDisposable.add(disposable);
    }

    private void setLoanAuthModel(String state, LoanAuthModel model, boolean canModify) {
        if (TextUtils.isEmpty(state) || state.equals("0")) {
            model.setIcState(R.drawable.ic_angle_blue);
            model.setIcBackground(R.drawable.loan_auth_ic_bg_gray);
            model.setColor(R.color.color_gray);
            model.setBackground(R.drawable.loan_auth_bg_gray);
            model.setVerify(false);
            model.setCanModify(true);
        } else if (state.equals("1")) {
            if (canModify) {
                model.setIcState(R.drawable.ic_angle_blue);
            } else {
                model.setIcState(R.drawable.loan_auth_ic_pass);
            }
            model.setIcBackground(R.drawable.loan_auth_ic_bg_blue);
            model.setColor(R.color.color_blue);
            model.setBackground(R.drawable.loan_auth_bg_blue);
            model.setVerify(true);
            model.setCanModify(canModify);
        } else if (state.equals("2")) {
            model.setIcState(R.drawable.ic_angle_blue);
            model.setIcBackground(R.drawable.loan_auth_ic_bg_gray);
            model.setColor(R.color.color_gray);
            model.setBackground(R.drawable.loan_auth_bg_gray);
            model.setVerify(false);
            model.setCanModify(true);
        }
    }
}
