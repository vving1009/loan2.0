package com.jiaye.cashloan.view.view.launch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.view.main.MainActivity;

/**
 * LaunchFragment
 *
 * @author 贾博瑄
 */

public class LaunchFragment extends BaseFragment {

    public static LaunchFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        LaunchFragment fragment = new LaunchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ImageView mImg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.launch_fragment, container, false);
        mImg = root.findViewById(R.id.img);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switch (getArguments().getInt("position")) {
            case 0:
                mImg.setImageDrawable(getResources().getDrawable(R.drawable.launch_bg_1));
                break;
            case 1:
                mImg.setImageDrawable(getResources().getDrawable(R.drawable.launch_bg_2));
                break;
            case 2:
                mImg.setImageDrawable(getResources().getDrawable(R.drawable.launch_bg_3));
                break;
            case 3:
                mImg.setImageDrawable(getResources().getDrawable(R.drawable.launch_bg_4));
                mImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMainView();
                    }
                });
                break;
        }
    }

    private void showMainView() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();
    }
}
