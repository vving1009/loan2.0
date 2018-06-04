package com.jiaye.cashloan.widget;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

public class ScrollOffsetTransformer implements ViewPager.PageTransformer {
    /**
     * position参数指明给定页面相对于屏幕中心的位置。它是一个动态属性，会随着页面的滚动而改变。
     * 当一个页面（page)填充整个屏幕时，positoin值为0；
     * 当一个页面（page)刚刚离开屏幕右(左）侧时，position值为1（-1）；
     * 当两个页面分别滚动到一半时，其中一个页面是-0.5，另一个页面是0.5。
     * 基于屏幕上页面的位置，通过诸如setAlpha()、setTranslationX()或setScaleY()方法来设置页面的属性，创建自定义的滑动动画。
     */

    private static final String TAG = "ScrollOffsetTransformer";
    private static final float MIN_SCALE = 0.8325f;
    private static final float MIN_ALPHA = 1.0f;

    @Override
    public void transformPage(@NonNull View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        if (Math.abs(position) <= 1) {//a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
            // Modify the default slide transition to shrink the page as well
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            /*float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                view.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }*/

            // Scale the page down (between MIN_SCALE and 1)
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

            // Fade the page relative to its size.
            /*view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
                    / (1 - MIN_SCALE) * (1 - MIN_ALPHA));*/
            view.setAlpha(Math.max(MIN_ALPHA, 1 - Math.abs(position)));
        }
        if (Math.abs(position) == 2) {
            view.setScaleX(MIN_SCALE);
            view.setScaleY(MIN_SCALE);
            view.setAlpha(MIN_ALPHA);
        }

        int width = view.getResources().getDisplayMetrics().widthPixels;
        float density = view.getResources().getDisplayMetrics().density;
        view.setTranslationX(-((width - 300 * density) / 2 - 22) * density * position);
    }
}
