package com.jiaye.cashloan.view.calculator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.BaseFunctionFragment;
import com.jiaye.cashloan.view.calculator.source.CalculatorRepository;

/**
 * CalculatorFragment
 *
 * @author 贾博瑄
 */

public class CalculatorFragment extends BaseFunctionFragment implements CalculatorContract.View {

    private CalculatorContract.Presenter mPresenter;

    private RadioGroup mRadioGroup;

    public static CalculatorFragment newInstance() {
        Bundle args = new Bundle();
        CalculatorFragment fragment = new CalculatorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getTitleId() {
        return R.string.cal_title;
    }

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        View root = inflater.inflate(R.layout.calculator_fragment, frameLayout, true);
        mRadioGroup = root.findViewById(R.id.loan_type);
        mRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.credit_button:
                    break;
                case R.id.car_button:
                    break;
            }
        });
        mPresenter = new CalculatorPresenter(this, new CalculatorRepository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }
}
