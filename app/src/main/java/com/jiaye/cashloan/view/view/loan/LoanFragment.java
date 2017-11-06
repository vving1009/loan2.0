package com.jiaye.cashloan.view.view.loan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.home.Product;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.view.auth.AuthActivity;

/**
 * LoanFragment
 *
 * @author 贾博瑄
 */

public class LoanFragment extends BaseFragment implements LoanContract.View {

    private LoanContract.Presenter mPresenter;

    /**
     * 产品编号
     *
     * @return LoanFragment
     */
    public static LoanFragment newInstance() {
        Bundle args = new Bundle();
        LoanFragment fragment = new LoanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.loan_fragment, container, false);
        root.findViewById(R.id.btn_loan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loan();
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.subscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(LoanContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void startAuthView() {
        Intent intent = new Intent(getContext(), AuthActivity.class);
        startActivity(intent);
    }

    @Override
    public void queryProduct() {
        mPresenter.queryProduct();
    }

    @Override
    public void requestProduct() {
        mPresenter.requestProduct();
    }

    @Override
    public void setProduct(Product product) {
        // TODO: 2017/11/6 动态显示
    }

    @Override
    public void startLoanAuthView() {
        Intent intent = new Intent(getActivity(), LoanAuthActivity.class);
        getActivity().startActivity(intent);
    }
}
