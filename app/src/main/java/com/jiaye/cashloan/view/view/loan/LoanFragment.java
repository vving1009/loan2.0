package com.jiaye.cashloan.view.view.loan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.http.data.loan.DefaultProduct;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.data.loan.source.LoanRepository;
import com.jiaye.cashloan.view.view.auth.AuthActivity;

/**
 * LoanFragment
 *
 * @author 贾博瑄
 */

public class LoanFragment extends BaseFragment implements LoanContract.View {

    private LoanContract.Presenter mPresenter;

    private TextView mTextTitle;

    private TextView mTextAmount;

    private TextView mTextDeadline;

    private Button mBtnLoan;

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
        mTextTitle = root.findViewById(R.id.text_title);
        mTextAmount = root.findViewById(R.id.text_amount);
        mTextDeadline = root.findViewById(R.id.text_deadline);
        mBtnLoan = root.findViewById(R.id.btn_loan);
        mBtnLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loan();
            }
        });
        mPresenter = new LoanPresenter(this, new LoanRepository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
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
    public void cleanProduct() {
        mTextTitle.setText("");
        mTextAmount.setText("");
        mTextDeadline.setText("");
        mBtnLoan.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setDefaultProduct(DefaultProduct defaultProduct) {
        mTextTitle.setText(defaultProduct.getName());
        mTextAmount.setText(defaultProduct.getAmount());
        mTextDeadline.setText(String.format(getString(R.string.loan_deadline_title), defaultProduct.getDeadline()));
        mBtnLoan.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoanAuthView() {
        Intent intent = new Intent(getActivity(), LoanAuthActivity.class);
        getActivity().startActivity(intent);
    }


}
