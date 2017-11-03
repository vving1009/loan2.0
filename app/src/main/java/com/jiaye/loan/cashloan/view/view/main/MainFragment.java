package com.jiaye.loan.cashloan.view.view.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jiaye.loan.cashloan.R;
import com.jiaye.loan.cashloan.view.BaseFragment;
import com.jiaye.loan.cashloan.view.data.my.source.MyRepository;
import com.jiaye.loan.cashloan.view.view.home.HomeFragment;
import com.jiaye.loan.cashloan.view.view.home.HomePresenter;
import com.jiaye.loan.cashloan.view.view.loan.LoanFragment;
import com.jiaye.loan.cashloan.view.view.my.MyFragment;
import com.jiaye.loan.cashloan.view.view.my.MyPresenter;

/**
 * MainFragment
 *
 * @author 贾博瑄
 */

public class MainFragment extends BaseFragment {

    private ImageView mImgHome;

    private ImageView mImgLoan;

    private ImageView mImgMe;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_fragment, container, false);
        mImgHome = (ImageView) root.findViewById(R.id.img_home);
        mImgLoan = (ImageView) root.findViewById(R.id.img_loan);
        mImgMe = (ImageView) root.findViewById(R.id.img_me);
        root.findViewById(R.id.layout_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHomeView();
            }
        });
        root.findViewById(R.id.layout_loan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoanView();
            }
        });
        root.findViewById(R.id.layout_me).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMeView();
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startHomeView();
    }

    private void startHomeView() {
        mImgHome.setSelected(true);
        mImgLoan.setSelected(false);
        mImgMe.setSelected(false);
        HomeFragment fragment = HomeFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.layout_inside_content, fragment).commit();
        new HomePresenter(fragment, null);
    }

    private void startLoanView() {
        setLoanView(null);
    }

    private void startMeView() {
        mImgHome.setSelected(false);
        mImgLoan.setSelected(false);
        mImgMe.setSelected(true);
        MyFragment fragment = MyFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.layout_inside_content, fragment).commit();
        new MyPresenter(fragment, new MyRepository());
    }

    public void setLoanView(String id) {
        mImgHome.setSelected(false);
        mImgLoan.setSelected(true);
        mImgMe.setSelected(false);
        LoanFragment fragment = LoanFragment.newInstance(id);
        getFragmentManager().beginTransaction().replace(R.id.layout_inside_content, fragment).commit();
    }
}
