package com.jiaye.cashloan.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;

public class LetterIndexView extends LinearLayout {

    private char[] letters = new char[26];

    private Context mContext;

    private OnLetterSelectListener mOnLetterSelectListener;

    public LetterIndexView(Context context) {
        super(context);
        mContext = context;
        initLetters();
    }

    public LetterIndexView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initLetters();
    }

    public LetterIndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initLetters();
    }

    private void initLetters() {
        for (int i = 0; i < 26; i++) {
            letters[i] = (char) (i + 'a');
        }
    }

    private void initView() {
        setOrientation(VERTICAL);
        setBackgroundColor(getResources().getColor(android.R.color.transparent));
        for (char letter : letters) {
            TextView text = new TextView(mContext);
            LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
            params.weight = 1.0f;
            params.gravity = Gravity.CENTER;
            text.setLayoutParams(params);
            text.setText(letter);
            text.setTextSize(TypedValue.COMPLEX_UNIT_PX, sp2px(9));
            text.setTextColor(getResources().getColor(R.color.color_black));
            addView(text);
        }
    }

    public int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, getResources().getDisplayMetrics());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                mOnLetterSelectListener.onClick(letters[(int) (y / getLetterHeight()) + 1]);
                break;
        }
        return true;
    }

    private int getLetterHeight() {
        return getHeight() / 26;
    }

    public interface OnLetterSelectListener {
        void onClick(char letter);
    }

    public void setOnLetterSelectListener(OnLetterSelectListener listener) {
        mOnLetterSelectListener = listener;
    }
}
