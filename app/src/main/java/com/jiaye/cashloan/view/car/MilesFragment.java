package com.jiaye.cashloan.view.car;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
        mEditInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().startsWith("0")) {
                    mEditInput.setText(s.subSequence(1, s.length()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        rootView.findViewById(R.id.btn_submit).setOnClickListener(v -> {
            String input = mEditInput.getText().toString();
            if (!TextUtils.isEmpty(input)) {
                Activity activity = getActivity();
                if (activity != null) {
                    hideShowInput(activity);
                    Intent intent = new Intent();
                    intent.putExtra(Step1Fragment.EXTRA_MILES, input);
                    getActivity().setResult(Step1Fragment.REQUEST_CODE_CAR_MILES, intent);
                    getActivity().finish();
                }
            } else {
                showToast("请输入行驶里程");
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