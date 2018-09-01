package com.jiaye.cashloan.view.car;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFunctionFragment;
import com.jiaye.cashloan.view.step1.Step1Fragment;

public class MilesFragment extends BaseFunctionFragment {

    private EditText mEditInput;

    public static MilesFragment newInstance() {
        Bundle args = new Bundle();
        MilesFragment fragment = new MilesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout) {
        View rootView = inflater.inflate(R.layout.car_miles_fragment, frameLayout, true);
        mEditInput = rootView.findViewById(R.id.edit_input);
        rootView.findViewById(R.id.btn_submit).setOnClickListener(v -> {
            Activity activity = getActivity();
            if (activity != null) {
                hideShowInput(activity);
                Intent intent = new Intent();
                intent.putExtra(Step1Fragment.EXTRA_MILES, mEditInput.getText().toString());
                getActivity().setResult(Step1Fragment.REQUEST_CODE_CAR_MILES, intent);
                getActivity().finish();
            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected int getTitleId() {
        return R.string.car_miles_title;
    }

    private void hideShowInput(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditInput.getWindowToken(), 0);
    }
}