package com.jiaye.cashloan.view.loancredit.startpage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFunctionFragment;
import com.jiaye.cashloan.view.FunctionActivity;
import com.jiaye.cashloan.view.loancredit.startpage.source.StartPageRepository;

public class StartPageFragment extends BaseFunctionFragment implements StartPageContract.View {

    private StartPageContract.Presenter mPresenter;

    public static StartPageFragment newInstance() {
        Bundle args = new Bundle();
        StartPageFragment fragment = new StartPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getTitleId() {
        return R.string.credit_loan_titile;
    }

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        View root = inflater.inflate(R.layout.credit_start_page_fragment, frameLayout, true);
        root.findViewById(R.id.btn_apply).setOnClickListener(v -> mPresenter.loan());
        mPresenter = new StartPagePresenter(this, new StartPageRepository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void showCertificationView() {
        FunctionActivity.function(getActivity(), "CreditCertification");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }
}
