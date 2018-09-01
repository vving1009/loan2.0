package com.jiaye.cashloan.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;

/**
 * BaseFunctionFragment
 *
 * @author 贾博瑄
 */
public abstract class BaseFunctionFragment extends BaseFragment {

    private TextView textTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.base_function_fragment, container, false);
        root.findViewById(R.id.img_back).setOnClickListener(v -> onClickBack());
        textTitle = root.findViewById(R.id.text_title);
        textTitle.setText(getTitleId());
        TextView textFunction = root.findViewById(R.id.text_function);
        if (getFunctionId() != -1) {
            textFunction.setVisibility(View.VISIBLE);
            textFunction.setText(getFunctionId());
            textFunction.setOnClickListener(v -> onClickFunction());
        }
        FrameLayout frameLayout = root.findViewById(R.id.layout_function);
        onCreateFunctionView(inflater, frameLayout);
        return root;
    }

    /**
     * 点击返回
     */
    protected void onClickBack() {
        getActivity().finish();
    }

    /**
     * 设置标题
     */
    protected void setTitle(String text) {
        textTitle.setText(text);
    }

    /**
     * 获取标题资源Id
     *
     * @return resId
     */
    protected abstract int getTitleId();

    /**
     * 获取功能资源Id
     *
     * @return resId
     */
    protected int getFunctionId() {
        return -1;
    }

    /**
     * 点击功能键回调
     */
    protected void onClickFunction() {

    }

    /**
     * onCreateFunctionView
     *
     * @return View
     */
    protected abstract View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout);
}
