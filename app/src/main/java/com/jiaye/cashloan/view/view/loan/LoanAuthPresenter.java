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

    public LoanAuthPresenter(LoanAuthContract.View view, LoanAuthDataSource dataSource) {
        mView = view;
        mView.setPresenter(this);
        mDataSource = dataSource;
    }

    @Override
    public void requestLoanAuth() {
        mDataSource.requestLoanAuth()
                .compose(new ViewTransformer<LoanAuth>(mView))
                .subscribe(new Consumer<LoanAuth>() {
                    @Override
                    public void accept(LoanAuth loanAuth) throws Exception {
                        List<LoanAuthModel> list = new ArrayList<>();

                        LoanAuthModel card = new LoanAuthModel();
                        card.setIcon(R.drawable.loan_auth_ic_card);
                        card.setName(R.string.loan_auth_ocr);
                        setLoanAuthModel(loanAuth, card, false);

                        LoanAuthModel face = new LoanAuthModel();
                        face.setIcon(R.drawable.loan_auth_ic_face);
                        face.setName(R.string.loan_auth_face);
                        setLoanAuthModel(loanAuth, face, false);

                        LoanAuthModel person = new LoanAuthModel();
                        person.setIcon(R.drawable.loan_auth_ic_person);
                        person.setName(R.string.loan_auth_info);
                        setLoanAuthModel(loanAuth, person, true);

                        LoanAuthModel phone = new LoanAuthModel();
                        phone.setIcon(R.drawable.loan_auth_ic_phone);
                        phone.setName(R.string.loan_auth_phone);
                        setLoanAuthModel(loanAuth, phone, false);

                        LoanAuthModel taobao = new LoanAuthModel();
                        taobao.setIcon(R.drawable.loan_auth_ic_taobao);
                        taobao.setName(R.string.loan_auth_taobao);
                        setLoanAuthModel(loanAuth, taobao, false);

                        LoanAuthModel sesame = new LoanAuthModel();
                        sesame.setIcon(R.drawable.loan_auth_ic_sesame);
                        sesame.setName(R.string.loan_auth_sesame);
                        setLoanAuthModel(loanAuth, sesame, false);

                        list.add(card);
                        list.add(face);
                        list.add(person);
                        list.add(phone);
                        list.add(taobao);
                        list.add(sesame);

                        mView.setList(list);
                    }
                }, new ThrowableConsumer(mView));
    }

    @Override
    public void selectLoanAuthModel(LoanAuthModel model) {
        if (!model.isVerify() || model.isCanModify()) {
            switch (model.getName()) {
                case R.string.loan_auth_ocr:
                    mView.startLoanAuthOCRView();
                    break;
                case R.string.loan_auth_face:
                    mView.startLoanAuthFaceView();
                    break;
                case R.string.loan_auth_info:
                    mView.startLoanAuthInfoView();
                    break;
                case R.string.loan_auth_phone:
                    mView.startLoanAuthPhoneView();
                    break;
                case R.string.loan_auth_taobao:
                    mView.startLoanAuthTaoBaoView();
                    break;
                case R.string.loan_auth_sesame:
                    mView.startLoanAuthSesameView();
                    break;
            }
        }
    }

    private void setLoanAuthModel(LoanAuth loanAuth, LoanAuthModel model, boolean canModify) {
        if (TextUtils.isEmpty(loanAuth.getCardState()) || loanAuth.getCardState().equals("0")) {
            model.setIcState(R.drawable.ic_angle_blue);
            model.setIcBackground(R.drawable.loan_auth_ic_bg_gray);
            model.setColor(R.color.color_gray);
            model.setBackground(R.drawable.loan_auth_bg_gray);
            model.setVerify(false);
            model.setCanModify(true);
        } else if (loanAuth.getCardState().equals("1")) {
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
