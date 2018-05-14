package com.jiaye.cashloan.view.view.my.certificate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.data.my.certificate.source.CertificateRepository;
import com.jiaye.cashloan.view.view.my.MyActivity;

/**
 * CertificateFragment
 *
 * @author 贾博瑄
 */

public class CertificateFragment extends BaseFragment implements CertificateContract.View {

    private CertificateContract.Presenter mPresenter;

    private TextView mTextPhone;

    private TextView mTextIdCard;

    private TextView mTextInfo;

    private TextView mTextOperator;

    private TextView mTextTaoBao;

    public static CertificateFragment newInstance() {
        Bundle args = new Bundle();
        CertificateFragment fragment = new CertificateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.certificate_fragment, container, false);
        mTextPhone = view.findViewById(R.id.text_phone);
        mTextIdCard = view.findViewById(R.id.text_id_card);
        mTextInfo = view.findViewById(R.id.text_info);
        mTextOperator = view.findViewById(R.id.text_operator);
        mTextTaoBao = view.findViewById(R.id.text_taobao);
        view.findViewById(R.id.layout_id_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.ocr();
            }
        });
        view.findViewById(R.id.layout_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.info();
            }
        });
        view.findViewById(R.id.layout_operator).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.phone();
            }
        });
        mPresenter = new CertificatePresenter(this, new CertificateRepository());
        mPresenter.subscribe();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPhone(String text) {
        mTextPhone.setText(text);
    }

    @Override
    public void setOCRStatus(String text) {
        mTextIdCard.setText(text);
    }

    @Override
    public void setInfoStatus(String text) {
        mTextInfo.setText(text);
    }

    @Override
    public void setPhoneStatus(String text) {
        mTextOperator.setText(text);
    }

    @Override
    public void setTaoBaoStatus(String text) {
        mTextTaoBao.setText(text);
    }

    @Override
    public void showIdCardView() {
        Intent intent = new Intent(getActivity(), MyActivity.class);
        intent.putExtra("view", "certificate_id_card");
        getActivity().startActivity(intent);
    }

    @Override
    public void showInfoView() {
        Intent intent = new Intent(getActivity(), MyActivity.class);
        intent.putExtra("view", "certificate_info");
        getActivity().startActivity(intent);
    }

    @Override
    public void showOperatorView() {
        Intent intent = new Intent(getActivity(), MyActivity.class);
        intent.putExtra("view", "certificate_operator");
        getActivity().startActivity(intent);
    }
}
