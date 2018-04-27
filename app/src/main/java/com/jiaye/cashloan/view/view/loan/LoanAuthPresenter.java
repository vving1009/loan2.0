package com.jiaye.cashloan.view.view.loan;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.loan.FileState;
import com.jiaye.cashloan.http.data.loan.LoanAuth;
import com.jiaye.cashloan.http.data.loan.UploadContact;
import com.jiaye.cashloan.http.data.loan.UploadLocation;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.loan.LoanAuthModel;
import com.jiaye.cashloan.view.data.loan.source.LoanAuthDataSource;
import com.orhanobut.logger.Logger;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * LoanAuthPresenter
 *
 * @author 贾博瑄
 */

public class LoanAuthPresenter extends BasePresenterImpl implements LoanAuthContract.Presenter {

    private final LoanAuthContract.View mView;

    private final LoanAuthDataSource mDataSource;

    private int mCount;

    private int mStep;

    public LoanAuthPresenter(LoanAuthContract.View view, LoanAuthDataSource dataSource) {
        mView = view;
        mDataSource = dataSource;
    }

    @Override
    public void requestLoanAuth() {
        Disposable disposable = mDataSource.requestFileState()
                .flatMap(new Function<FileState, Publisher<LoanAuth>>() {
                    @Override
                    public Publisher<LoanAuth> apply(FileState fileState) throws Exception {
                        mCount = 0;
                        for (FileState.Data data : fileState.getList()) {
                            mCount += data.getCount();
                        }
                        return mDataSource.requestLoanAuth();
                    }
                })
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
                        isVerify = card.isVerify();
                        if (isVerify) {
                            mStep = 1;
                        }
                        list.add(card);

                        LoanAuthModel file = new LoanAuthModel();
                        file.setIcon(R.drawable.loan_auth_ic_file);
                        file.setName(R.string.loan_auth_file);
                        setLoanAuthModel(mCount > 0 ? "1" : "0", file, true);
                        isVerify = isVerify && mCount > 0;
                        if (isVerify) {
                            mStep = 2;
                        }
                        list.add(file);

                        LoanAuthModel phone = new LoanAuthModel();
                        phone.setIcon(R.drawable.loan_auth_ic_phone);
                        phone.setName(R.string.loan_auth_phone);
                        setLoanAuthModel(loanAuth.getPhoneState(), phone, false);
                        list.add(phone);

                        LoanAuthModel face = new LoanAuthModel();
                        face.setIcon(R.drawable.loan_auth_ic_face);
                        face.setName(R.string.loan_auth_face);
                        setLoanAuthModel(loanAuth.getFaceState(), face, false);
                        isVerify = isVerify && face.isVerify();
                        if (isVerify) {
                            mStep = 3;
                        }
                        list.add(face);

                        LoanAuthModel person = new LoanAuthModel();
                        person.setIcon(R.drawable.loan_auth_ic_person);
                        person.setName(R.string.loan_auth_info);
                        setLoanAuthModel(loanAuth.getPersonState(), person, true);
                        isVerify = isVerify && person.isVerify();
                        if (isVerify) {
                            mStep = 4;
                        }
                        list.add(person);

                        LoanAuthModel visa = new LoanAuthModel();
                        visa.setIcon(R.drawable.loan_auth_ic_visa);
                        visa.setName(R.string.loan_auth_visa);
                        setLoanAuthModel(loanAuth.getSignState(), visa, false);
                        isVerify = isVerify && visa.isVerify();
                        if (isVerify) {
                            mStep = 5;
                        }
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

                        LoanAuthModel taobao = new LoanAuthModel();
                        taobao.setIcon(R.drawable.loan_auth_ic_taobao);
                        taobao.setName(R.string.loan_auth_taobao);
                        setLoanAuthModel(loanAuth.getTaobaoState(), taobao, false);
                        list.add(taobao);

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
        if (!model.isVerify() || model.isCanModify()) {
            switch (model.getName()) {
                case R.string.loan_auth_ocr:
                    mView.showLoanAuthOCRView();
                    break;
                case R.string.loan_auth_file:
                    if (mStep < 1) {
                        mView.showToastById(R.string.error_loan_auth_step);
                    } else {
                        mView.showLoanFileView();
                    }
                    break;
                case R.string.loan_auth_phone:
                    if (mStep < 2) {
                        mView.showToastById(R.string.error_loan_auth_step);
                    } else {
                        mView.showLoanAuthPhoneView();
                    }
                    break;
                case R.string.loan_auth_face:
                    if (mStep < 2) {
                        mView.showToastById(R.string.error_loan_auth_step);
                    } else {
                        mView.showLoanAuthFaceView();
                    }
                    break;
                case R.string.loan_auth_info:
                    if (mStep < 3) {
                        mView.showToastById(R.string.error_loan_auth_step);
                    } else {
                        mView.showLoanAuthInfoView();
                    }
                    break;
                case R.string.loan_auth_visa:
                    if (mStep < 4) {
                        mView.showToastById(R.string.error_loan_auth_step);
                    } else {
                        mView.showLoanAuthVisaView();
                    }
                    break;
                case R.string.loan_auth_visa_history:
                    mView.showLoanAuthVisaHistoryView();
                    break;
                case R.string.loan_auth_taobao:
                    if (mStep < 4) {
                        mView.showToastById(R.string.error_loan_auth_step);
                    } else {
                        mView.showLoanAuthTaoBaoView();
                    }
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
