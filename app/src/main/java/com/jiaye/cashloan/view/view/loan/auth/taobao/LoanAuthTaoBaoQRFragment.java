package com.jiaye.cashloan.view.view.loan.auth.taobao;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.data.loan.auth.source.taobao.LoanAuthTaoBaoQRRepository;

/**
 * LoanAuthTaoBaoQRFragment
 *
 * @author 贾博瑄
 */

public class LoanAuthTaoBaoQRFragment extends BaseFragment implements LoanAuthTaoBaoQRContract.View {

    private LoanAuthTaoBaoQRContract.Presenter mPresenter;

    public static LoanAuthTaoBaoQRFragment newInstance() {
        Bundle args = new Bundle();
        LoanAuthTaoBaoQRFragment fragment = new LoanAuthTaoBaoQRFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ImageView mImgQRCode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.loan_auth_taobao_qrcode_fragment, container, false);
        mImgQRCode = root.findViewById(R.id.img_qrcode);
        mPresenter = new LoanAuthTaoBaoQRPresenter(this, new LoanAuthTaoBaoQRRepository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void setImg(Bitmap bitmap) {
        mImgQRCode.setImageBitmap(bitmap);
    }

    @Override
    public void result() {
        getActivity().finish();
    }

    public void request() {
        mPresenter.request();
    }
}
