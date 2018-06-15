package com.jiaye.cashloan.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.jiaye.cashloan.R;

/**
 * StepView
 *
 * @author 贾博瑄
 */
public class StepView extends View {

    private Paint mPaintTop;

    private Paint mPaintMid;

    private Paint mPaintBottom;

    private int mType;

    private boolean mSelect;

    private boolean mReady;

    private Bitmap mBitmapSelect;

    private Bitmap mBitmapUnSelect;

    public StepView(Context context) {
        super(context);
        init(context, null);
    }

    public StepView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float density = getResources().getDisplayMetrics().density;
        if (mSelect) {
            mPaintTop.setColor(getResources().getColor(R.color.color_blue));
            mPaintBottom.setColor(getResources().getColor(R.color.color_blue));
            switch (mType) {
                case 0:
                    canvas.drawBitmap(mBitmapSelect, 0, 13 * density, mPaintMid);
                    canvas.drawRect(10 * density, 36 * density, 13 * density, 44 * density, mPaintBottom);
                    break;
                case 1:
                    canvas.drawRect(10 * density, 0, 13 * density, 13 * density, mPaintTop);
                    canvas.drawBitmap(mBitmapSelect, 0, 13 * density, mPaintMid);
                    canvas.drawRect(10 * density, 36 * density, 13 * density, 44 * density, mPaintBottom);
                    break;
                case 2:
                    canvas.drawRect(10 * density, 0, 13 * density, 13 * density, mPaintTop);
                    canvas.drawBitmap(mBitmapSelect, 0, 13 * density, mPaintMid);
                    break;
            }
        } else {
            mPaintTop.setColor(getResources().getColor(R.color.color_blue_hole));
            mPaintBottom.setColor(getResources().getColor(R.color.color_blue_hole));
            switch (mType) {
                case 0:
                    canvas.drawBitmap(mBitmapUnSelect, 0, 13 * density, mPaintMid);
                    canvas.drawRect(10 * density, 36 * density, 13 * density, 44 * density, mPaintBottom);
                    break;
                case 1:
                    if (mReady) {
                        mPaintTop.setColor(getResources().getColor(R.color.color_blue));
                    }
                    canvas.drawRect(10 * density, 0, 13 * density, 13 * density, mPaintTop);
                    canvas.drawBitmap(mBitmapUnSelect, 0, 13 * density, mPaintMid);
                    canvas.drawRect(10 * density, 36 * density, 13 * density, 44 * density, mPaintBottom);
                    break;
                case 2:
                    if (mReady) {
                        mPaintTop.setColor(getResources().getColor(R.color.color_blue));
                    }
                    canvas.drawRect(10 * density, 0, 13 * density, 13 * density, mPaintTop);
                    canvas.drawBitmap(mBitmapUnSelect, 0, 13 * density, mPaintMid);
                    break;
            }
        }
    }

    public void setType(int type) {
        mType = type;
    }

    public void setSelect(boolean select) {
        mSelect = select;
    }

    public void setReady(boolean ready) {
        mReady = ready;
    }

    private void init(Context context, AttributeSet attrs) {
        mPaintTop = new Paint();
        mPaintMid = new Paint();
        mPaintBottom = new Paint();
        mBitmapSelect = BitmapFactory.decodeResource(getResources(), R.drawable.certification_ic_step_select);
        mBitmapUnSelect = BitmapFactory.decodeResource(getResources(), R.drawable.certification_ic_step_un_select);
    }
}
