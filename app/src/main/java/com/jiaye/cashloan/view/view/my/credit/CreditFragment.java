package com.jiaye.cashloan.view.view.my.credit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.data.my.credit.CreditBalanceRequest;
import com.jiaye.cashloan.view.data.my.credit.CreditCashRequest;
import com.jiaye.cashloan.view.data.my.credit.CreditPasswordRequest;
import com.jiaye.cashloan.view.data.my.credit.source.CreditRepository;

/**
 * CreditFragment
 *
 * @author 贾博瑄
 */

public class CreditFragment extends BaseFragment implements CreditContract.View {

    private CreditContract.Presenter mPresenter;

    public static CreditFragment newInstance() {
        Bundle args = new Bundle();
        CreditFragment fragment = new CreditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.credit_fragment, container, false);
        view.findViewById(R.id.layout_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.password();
            }
        });
        view.findViewById(R.id.layout_cash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.cash();
            }
        });
        view.findViewById(R.id.layout_balance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.balance();
            }
        });
        mPresenter = new CreditPresenter(this, new CreditRepository());
        mPresenter.subscribe();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void showPasswordView(CreditPasswordRequest request) {
        Intent intent = new Intent(getActivity(), CreditActivity.class);
        intent.putExtra("type","password");
        intent.putExtra("password", request);
        startActivity(intent);
    }

    @Override
    public void showCashView(CreditCashRequest request) {
        Intent intent = new Intent(getActivity(), CreditActivity.class);
        intent.putExtra("type","cash");
        intent.putExtra("cash", request);
        startActivity(intent);
    }

    @Override
    public void showBalanceView(CreditBalanceRequest request) {
        Intent intent = new Intent(getActivity(), CreditActivity.class);
        intent.putExtra("type","balance");
        intent.putExtra("balance", request);
        startActivity(intent);
    }
}
