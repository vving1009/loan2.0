package com.jiaye.cashloan.view.view.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.utils.GlideImageLoader;
import com.jiaye.cashloan.view.BaseFragment;
import com.jiaye.cashloan.view.view.loan.LoanActivity;
import com.youth.banner.Banner;

import java.util.ArrayList;

/**
 * HomeFragment
 *
 * @author 贾博瑄
 */

public class HomeFragment extends BaseFragment {

    private Banner mBanner;

    private static final int[] TAB_IMAGES = {R.drawable.home_tab_price, R.drawable.home_tab_credit, R.drawable.home_tab_loan};

    private static final int[] TAB_TITLE = {R.string.home_price, R.string.home_credit, R.string.home_loan};

    private static final int[] IMAGES = {R.drawable.home_ic_price, R.drawable.home_ic_credit, R.drawable.home_ic_loan};

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_fragment, container, false);
        /*Banner*/
        mBanner = root.findViewById(R.id.banner);
        ArrayList<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.bannar1);
        imageList.add(R.drawable.bannar2);
        imageList.add(R.drawable.bannar3);
        imageList.add(R.drawable.bannar4);
        imageList.add(R.drawable.bannar5);
        mBanner.setImages(imageList);
        mBanner.setDelayTime(4000);
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.start();
        /*Product*/
        View view1 = root.findViewById(R.id.include_1);
        TextView textTitle1 = view1.findViewById(R.id.text_title);
        ImageView imageView1 = view1.findViewById(R.id.img_product);
        textTitle1.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(TAB_IMAGES[0]), null, null, null);
        textTitle1.setText(getString(TAB_TITLE[0]));
        imageView1.setImageDrawable(getResources().getDrawable(IMAGES[0]));
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWishView();
            }
        });

        View view2 = root.findViewById(R.id.include_2);
        TextView textTitle2 = view2.findViewById(R.id.text_title);
        ImageView imageView2 = view2.findViewById(R.id.img_product);
        textTitle2.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(TAB_IMAGES[1]), null, null, null);
        textTitle2.setText(getString(TAB_TITLE[1]));
        imageView2.setImageDrawable(getResources().getDrawable(IMAGES[1]));

        View view3 = root.findViewById(R.id.include_3);
        TextView textTitle3 = view3.findViewById(R.id.text_title);
        ImageView imageView3 = view3.findViewById(R.id.img_product);
        textTitle3.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(TAB_IMAGES[2]), null, null, null);
        textTitle3.setText(getString(TAB_TITLE[2]));
        imageView3.setImageDrawable(getResources().getDrawable(IMAGES[2]));
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();
    }

    private void showWishView() {
        startActivity(new Intent(getActivity(), LoanActivity.class));
    }
}
