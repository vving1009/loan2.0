package com.jiaye.cashloan.view.view.guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.data.guide.GuideRepository;
import com.jiaye.cashloan.view.view.main.MainActivity;

/**
 * GuideFragment
 *
 * @author 贾博瑄
 */

public class GuideFragment extends BaseFragment implements GuideContract.View {

    private GuideContract.Presenter mPresenter;

    public static GuideFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        GuideFragment fragment = new GuideFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressWarnings("deprecation")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.guide_fragment, container, false);
        ImageView img = root.findViewById(R.id.img);
        switch (getArguments().getInt("position")) {
            case 0:
                img.setImageDrawable(getResources().getDrawable(R.drawable.launch_bg_1));
                break;
            case 1:
                img.setImageDrawable(getResources().getDrawable(R.drawable.launch_bg_2));
                break;
            case 2:
                img.setImageDrawable(getResources().getDrawable(R.drawable.launch_bg_3));
                break;
            case 3:
                img.setImageDrawable(getResources().getDrawable(R.drawable.launch_bg_4));
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.setNotNeedGuide();
                    }
                });
                break;
        }
        mPresenter = new GuidePresenter(this, new GuideRepository());
        mPresenter.subscribe();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void showMainView() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();
    }
}
