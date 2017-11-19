package com.jiaye.cashloan.view.view.launch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.data.launch.LaunchRepository;
import com.jiaye.cashloan.view.view.guide.GuideActivity;
import com.jiaye.cashloan.view.view.main.MainActivity;

/**
 * LaunchFragment
 *
 * @author 贾博瑄
 */

public class LaunchFragment extends BaseFragment implements LaunchContract.View {

    private LaunchContract.Presenter mPresenter;

    public static LaunchFragment newInstance() {
        Bundle args = new Bundle();
        LaunchFragment fragment = new LaunchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.launch_fragment, container, false);
        mPresenter = new LaunchPresenter(this, new LaunchRepository());
        mPresenter.subscribe();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void showGuideView() {
        Intent intent = new Intent(getActivity(), GuideActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void showMainView() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();
    }
}
