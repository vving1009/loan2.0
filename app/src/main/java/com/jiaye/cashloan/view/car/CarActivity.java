package com.jiaye.cashloan.view.car;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.jiaye.cashloan.R;
import com.jiaye.cashloan.view.BaseListFragment;
import com.jiaye.cashloan.view.car.brand.BrandFragment;
import com.jiaye.cashloan.view.car.city.CityFragment;
import com.jiaye.cashloan.view.car.model.ModelFragment;
import com.jiaye.cashloan.view.car.province.ProvinceFragment;
import com.jiaye.cashloan.view.car.series.SeriesFragment;
import com.jiaye.cashloan.view.car.year.YearFragment;
import com.jiaye.cashloan.view.step1.Step1Fragment;
import com.jiaye.cashloan.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class CarActivity extends AppCompatActivity {

    public static final String EXTRA_TYPE = "type";
    public static final int TYPE_BRAND = 101;
    public static final int TYPE_DATE = 102;
    public static final int TYPE_CITY = 103;

    private NoScrollViewPager mViewPager;

    private PagerAdapter mPagerAdapter;

    private BaseListFragment mCurrentFragment;

    private List<BaseListFragment> mFragments;

    private int currentPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_activity);

        mFragments = new ArrayList<>();
        int type = getIntent().getIntExtra(EXTRA_TYPE, 0);
        switch (type) {
            case TYPE_BRAND:
                mFragments.add(BrandFragment.newInstance());
                mFragments.add(SeriesFragment.newInstance());
                mFragments.add(ModelFragment.newInstance());
                break;
            case TYPE_DATE:
                mFragments.add(YearFragment.newInstance(getIntent().getStringExtra(Step1Fragment.EXTRA_MODEL_ID)));
                mFragments.add(MonthFragment.newInstance());
                break;
            case TYPE_CITY:
                mFragments.add(ProvinceFragment.newInstance());
                mFragments.add(CityFragment.newInstance());
                break;
        }
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setOffset(false);
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        /*ViewPagerScroller pagerScroller = new ViewPagerScroller(this);
        pagerScroller.setScrollDuration(600);
        pagerScroller.initViewPagerScroll(mViewPager);*/
        currentPosition = 0;
        mViewPager.setCurrentItem(currentPosition);
    }

    @Override
    public void onBackPressed() {
        if (mCurrentFragment != null && mCurrentFragment.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    public void goToNextFragment() {
        mViewPager.setCurrentItem(++currentPosition);
    }

    public boolean goToBackFragment() {
        if (currentPosition > 0) {
            mViewPager.setCurrentItem(--currentPosition);
            return true;
        } else {
            return false;
        }
    }

    public BaseListFragment getNextFragment() {
        return mFragments.get(currentPosition + 1);
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {

        PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            mCurrentFragment = (BaseListFragment) object;
            super.setPrimaryItem(container, position, object);
        }
    }
}
