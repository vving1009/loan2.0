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

    protected TextView mText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.base_function_fragment, container, false);
        root.findViewById(R.id.img_back).setOnClickListener(v -> onClickBack());
        mText = root.findViewById(R.id.text);
        mText.setOnClickListener(v -> onClickText());
        TextView textTitle = root.findViewById(R.id.text_title);
        textTitle.setText(getTitleId());
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
     * 获取标题资源Id
     *
     * @return resId
     */
    protected abstract int getTitleId();

    /**
     * onCreateFunctionView
     *
     * @return View
     */
    protected abstract View onCreateFunctionView(LayoutInflater inflater, FrameLayout frameLayout);

    /**
     * 点击文字
     */
    public void onClickText() {

    }
}
