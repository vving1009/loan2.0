package com.jiaye.cashloan.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;

public class LetterIndexView extends LinearLayout {

    private static final String TAG = "LetterIndexView";

    private char[] letters = new char[26];

    private Context mContext;

    private OnLetterSelectListener mOnLetterSelectListener;

    public LetterIndexView(Context context) {
        super(context);
        mContext = context;
        initLetters();
        initView();
    }

    public LetterIndexView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initLetters();
        initView();
    }

    public LetterIndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initLetters();
        initView();
    }

    private void initLetters() {
        for (int i = 0; i < 26; i++) {
            letters[i] = (char) (i + 'A');
        }
    }

    private void initView() {
        setOrientation(VERTICAL);
        setBackgroundColor(getResources().getColor(android.R.color.transparent));
        bringToFront();
        for (char letter : letters) {
            TextView text = new TextView(mContext);
            LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            params.weight = 1.0f;
            text.setLayoutParams(params);
            text.setGravity(Gravity.CENTER);
            text.setTextSize(TypedValue.COMPLEX_UNIT_PX, sp2px(9));
            text.setTextColor(getResources().getColor(R.color.color_black));
            text.setText(String.valueOf(letter));
            addView(text);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        Log.d(TAG, "onTouchEvent: "+y);
        int index = (int) (y / getLetterHeight()) + 1;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                if (index >= 0 && index < 26) {
                    mOnLetterSelectListener.onClick(String.valueOf(letters[index]));
                }
                break;
        }
        return true;
    }

    private int getLetterHeight() {
        return getHeight() / 26;
    }

    public int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, getResources().getDisplayMetrics());
    }

    public interface OnLetterSelectListener {
        void onClick(String letter);
    }

    public void setOnLetterSelectListener(OnLetterSelectListener listener) {
        mOnLetterSelectListener = listener;
    }
}
