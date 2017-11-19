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
import com.jiaye.cashloan.view.view.my.MyActivity;

/**
 * CertificateFragment
 *
 * @author 贾博瑄
 */

public class CertificateFragment extends BaseFragment implements CertificateContract.View {

    private CertificateContract.Presenter mPresenter;

    private TextView mTextBank;

    private TextView mTextIdCard;

    private TextView mTextInfo;

    private TextView mTextOperator;

    private TextView mTextTaoBao;

    private TextView mTextSesame;

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
        mTextBank = view.findViewById(R.id.text_bank);
        mTextIdCard = view.findViewById(R.id.text_id_card);
        mTextInfo = view.findViewById(R.id.text_info);
        mTextOperator = view.findViewById(R.id.text_operator);
        mTextTaoBao = view.findViewById(R.id.text_taobao);
        mTextSesame = view.findViewById(R.id.text_sesame);
        view.findViewById(R.id.layout_bank).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBankView();
            }
        });
        view.findViewById(R.id.layout_id_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIdCardView();
            }
        });
        view.findViewById(R.id.layout_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfoView();
            }
        });
        view.findViewById(R.id.layout_operator).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOperatorView();
            }
        });
        view.findViewById(R.id.layout_taobao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTaoBaoView();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setPresenter(CertificateContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showBankView() {
        Intent intent = new Intent(getActivity(), MyActivity.class);
        intent.putExtra("view","certificate_bank");
        getActivity().startActivity(intent);
    }

    @Override
    public void showIdCardView() {
        Intent intent = new Intent(getActivity(), MyActivity.class);
        intent.putExtra("view","certificate_id_card");
        getActivity().startActivity(intent);
    }

    @Override
    public void showInfoView() {
        Intent intent = new Intent(getActivity(), MyActivity.class);
        intent.putExtra("view","certificate_info");
        getActivity().startActivity(intent);
    }

    @Override
    public void showOperatorView() {
        Intent intent = new Intent(getActivity(), MyActivity.class);
        intent.putExtra("view","certificate_operator");
        getActivity().startActivity(intent);
    }

    @Override
    public void showTaoBaoView() {
        Intent intent = new Intent(getActivity(), MyActivity.class);
        intent.putExtra("view","certificate_taobao");
        getActivity().startActivity(intent);
    }
}
