package com.jiaye.cashloan.view.view.loan;

import android.text.TextUtils;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.loan.LoanAuth;
import com.jiaye.cashloan.view.BasePresenterImpl;
import com.jiaye.cashloan.view.ThrowableConsumer;
import com.jiaye.cashloan.view.ViewTransformer;
import com.jiaye.cashloan.view.data.loan.LoanAuthModel;
import com.jiaye.cashloan.view.data.loan.source.LoanAuthDataSource;

import java.util.ArrayList;
import java.util.List;

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
        mDataSource.requestLoanAuth()
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

                        LoanAuthModel face = new LoanAuthModel();
                        face.setIcon(R.drawable.loan_auth_ic_face);
                        face.setName(R.string.loan_auth_face);
                        setLoanAuthModel(loanAuth.getFaceState(), face, false);
                        isVerify = isVerify && face.isVerify();

                        LoanAuthModel person = new LoanAuthModel();
                        person.setIcon(R.drawable.loan_auth_ic_person);
                        person.setName(R.string.loan_auth_info);
                        setLoanAuthModel(loanAuth.getPersonState(), person, true);
                        isVerify = isVerify && person.isVerify();

                        LoanAuthModel phone = new LoanAuthModel();
                        phone.setIcon(R.drawable.loan_auth_ic_phone);
                        phone.setName(R.string.loan_auth_phone);
                        setLoanAuthModel(loanAuth.getPhoneState(), phone, false);
                        isVerify = isVerify && phone.isVerify();

                        LoanAuthModel taobao = new LoanAuthModel();
                        taobao.setIcon(R.drawable.loan_auth_ic_taobao);
                        taobao.setName(R.string.loan_auth_taobao);
                        setLoanAuthModel(loanAuth.getTaobaoState(), taobao, false);

                        LoanAuthModel sesame = new LoanAuthModel();
                        sesame.setIcon(R.drawable.loan_auth_ic_sesame);
                        sesame.setName(R.string.loan_auth_sesame);
                        setLoanAuthModel(loanAuth.getSesameState(), sesame, false);
                        isVerify = isVerify && sesame.isVerify();

                        list.add(card);
                        list.add(face);
                        list.add(person);
                        list.add(phone);
                        list.add(taobao);
                        list.add(sesame);

                        mView.setList(list);
                        if (isVerify) {
                            mView.setNextEnable();
                        }
                        mView.dismissProgressDialog();
                    }
                }, new ThrowableConsumer(mView));
    }

    @Override
    public void selectLoanAuthModel(LoanAuthModel model) {
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
        }
    }
}
