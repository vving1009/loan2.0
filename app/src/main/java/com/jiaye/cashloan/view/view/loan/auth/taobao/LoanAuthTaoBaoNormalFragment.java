package com.jiaye.cashloan.view.view.loan.auth.taobao;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.data.loan.auth.source.taobao.LoanAuthTaoBaoNormalRepository;
import com.jiaye.cashloan.widget.LoanEditText;

/**
 * LoanAuthTaoBaoNormalFragment
 *
 * @author 贾博瑄
 */

public class LoanAuthTaoBaoNormalFragment extends BaseFragment implements LoanAuthTaoBaoNormalContract.View {

    private LoanAuthTaoBaoNormalContract.Presenter mPresenter;

    private LoanEditText mEditAccount;

    private LoanEditText mEditPassword;

    private LoanEditText mEditSMS;

    private LoanEditText mEditIMG;

    public static LoanAuthTaoBaoNormalFragment newInstance() {
        Bundle args = new Bundle();
        LoanAuthTaoBaoNormalFragment fragment = new LoanAuthTaoBaoNormalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.loan_auth_taobao_normal_fragment, container, false);
        mEditAccount = root.findViewById(R.id.edit_account);
        mEditPassword = root.findViewById(R.id.edit_password);
        mEditSMS = root.findViewById(R.id.edit_sms);
        mEditIMG = root.findViewById(R.id.edit_img);
        mEditSMS.setOnClickVerificationCode(new LoanEditText.OnClickVerificationCode() {
            @Override
            public void onClickVerificationCode() {
                mPresenter.requestSMS();
            }
        });
        mEditIMG.setOnClickVerificationCode(new LoanEditText.OnClickVerificationCode() {
            @Override
            public void onClickVerificationCode() {
                mPresenter.requestIMG();
            }
        });
        root.findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.submit();
            }
        });
        mPresenter = new LoanAuthTaoBaoNormalPresenter(this, new LoanAuthTaoBaoNormalRepository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void startCountDown() {
        mEditSMS.startCountDown();
    }

    @Override
    public void setImg(Bitmap bitmap) {
        mEditIMG.setCode(bitmap);
    }

    @Override
    public String getAccount() {
        return mEditAccount.getText().toString();
    }

    @Override
    public String getPassword() {
        return mEditPassword.getText().toString();
    }

    @Override
    public String getSMSCode() {
        return mEditSMS.getText().toString();
    }

    @Override
    public String getImgCode() {
        return mEditIMG.getText().toString();
    }

    @Override
    public void cleanImgVerificationCodeText() {
        mEditIMG.setText("");
    }

    @Override
    public void setImgVerificationCodeVisibility() {
        mEditIMG.setVisibility(View.VISIBLE);
    }

    @Override
    public void setImgVerificationCode(Bitmap bitmap) {
        mEditIMG.setCode(bitmap);
    }

    @Override
    public void cleanSmsVerificationCodeText() {
        mEditSMS.setText("");
    }

    @Override
    public void setSmsVerificationCodeVisibility() {
        mEditSMS.setVisibility(View.VISIBLE);
    }

    @Override
    public void result() {
        getActivity().finish();
    }
}
