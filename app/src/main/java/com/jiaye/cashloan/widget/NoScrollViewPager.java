package com.jiaye.cashloan.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * NoScrollViewPager
 *
 * @author 贾博瑄
 */

public class NoScrollViewPager extends ViewPager {

    // 配合ScrollOffsetTransformer使用
    private static final float MIN_SCALE = 0.8325f;

    private float xLeft;

    private float xRight;

    private boolean mIsOffset;

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NoScrollViewPager(Context context) {
        super(context);
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return super.onTouchEvent(arg0);
        //return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return mIsOffset && (arg0.getX() < xLeft || arg0.getX() > xRight);
    }

    public boolean isOffset() {
        return mIsOffset;
    }

    public void setOffset(boolean offset) {
        mIsOffset = offset;
    }

    private void init() {
        int width = getResources().getDisplayMetrics().widthPixels;
        float density = getResources().getDisplayMetrics().density;
        xLeft = (width - 300 * MIN_SCALE * density) / 2;
        xRight = xLeft + 300 * MIN_SCALE * density;
    }
}
