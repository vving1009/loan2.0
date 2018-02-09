package com.jiaye.cashloan.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jiaye.cashloan.R;

import java.util.Random;

/**
 * VerificationCodeView
 *
 * @author 贾博瑄
 */

public class VerificationCodeView extends View {

    private static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final int[] colors = new int[]{Color.BLACK, Color.GRAY, Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA};

    //验证码模式0代码存数字，1代表纯字母模式，2代码混合貌似，数字或字母随机
    public static final int NUMBER = 0, STRING = 1, MIX = 2;

    //是否开启点干扰
    private boolean pointInterfere;

    //是否开启线干扰
    private boolean lineInterfere;

    //干扰线的数量
    private int lineInterfereQuantity;

    //干扰点的数量
    private int pointInterfereQuantity;

    //验证码数量,默认为4个,最多6个。
    private int codeQuantity;

    //验证码模式,默认为数字模式
    private int codeMode = NUMBER;

    //用来画验证码
    private Paint codePaint;

    //背景画笔
    private Paint bgPaint;

    //控件宽高
    private int w, h;

    private int textSize = 80;

    private String verificationCode;

    private Random random = new Random();

    public VerificationCodeView(Context context) {
        super(context);
        initView(context, null);
    }

    public VerificationCodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public String getVerificationCode() {
        StringBuilder strings = new StringBuilder();
        for (int i = 0; i < codeQuantity; i++) {
            char c = verificationCode.charAt(i);
            if (Character.isUpperCase(c)) {
                strings.append(Character.toLowerCase(c));
            } else {
                strings.append(c);
            }
        }
        return strings.toString().toLowerCase();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int nw = getSize(widthSize, widthMode);
        int nh = getSize(heightSize, heightMode);
        int minW = 400;
        nw = nw == 0 ? minW : nw;
        int minH = 200;
        nh = nh == 0 ? minH : nh;
        setMeasuredDimension(nw, nh);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
        dealTextSize();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                makeCode();
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawCode(canvas);
        drawInterfere(canvas);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VerificationCodeView);
        codeQuantity = typedArray.getInteger(R.styleable.VerificationCodeView_codeQuantity, 4);
        codeMode = typedArray.getInteger(R.styleable.VerificationCodeView_codeMode, MIX);
        pointInterfere = typedArray.getBoolean(R.styleable.VerificationCodeView_pointInterfere, true);
        pointInterfereQuantity = typedArray.getInt(R.styleable.VerificationCodeView_pointInterfereQuantity, 30);
        lineInterfere = typedArray.getBoolean(R.styleable.VerificationCodeView_lineInterfere, true);
        lineInterfereQuantity = typedArray.getInt(R.styleable.VerificationCodeView_lineInterfereQuantity, 4);
        typedArray.recycle();
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Paint.Style.FILL);
        codePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        codePaint.setTextSize(textSize);
        codePaint.setColor(Color.BLACK);
        codePaint.setStyle(Paint.Style.FILL);
        makeCode();
    }

    private void makeCode() {
        StringBuilder result = new StringBuilder();
        switch (codeMode) {
            case NUMBER:
                for (int i = 0; i < 6; i++) {
                    result.append(singleNumber());
                }
                break;
            case STRING:
                for (int i = 0; i < 6; i++) {
                    result.append(singleChar());
                }
                break;
            case MIX:
                for (int i = 0; i < 6; i++) {
                    result.append(mix());
                }
                break;
            default:
                break;
        }
        verificationCode = result.toString();
    }

    private String singleNumber() {
        int m = (int) (Math.random() * 10);
        return m + "";
    }

    private String singleChar() {
        return CHARS.charAt((int) (Math.random() * 52)) + "";
    }

    private String mix() {
        if (Math.random() < 0.5) {
            return singleNumber();
        }
        return singleChar();
    }

    private int getSize(int size, int mode) {
        int result = 0;
        switch (mode) {
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        return result;
    }

    private void dealTextSize() {
        if (w / codeQuantity > h) {
            textSize = h / 2;
        } else {
            textSize = w / (codeQuantity);
        }
        codePaint.setTextSize(textSize);
    }

    private void drawCode(Canvas canvas) {
        Paint.FontMetricsInt fontMetrics = codePaint.getFontMetricsInt();
        int baseline = (h - fontMetrics.bottom - fontMetrics.top) / 2;
        int m = (w - (codeQuantity) * textSize) / (codeQuantity + 2);
        for (int i = 0; i < codeQuantity; i++) {
            int r = random.nextInt(20);
            int x = (i + 1) * m + i * textSize;
            canvas.save();
            canvas.rotate((Math.random() < 0.5 ? r : -r), x, h / 2);
            codePaint.setColor(randomColor());
            canvas.drawText(verificationCode.charAt(i) + "", x, baseline, codePaint);
            canvas.restore();
        }
    }

    private void drawInterfere(Canvas canvas) {
        if (pointInterfere) {
            drawPointInterfere(canvas);
        }
        if (lineInterfere) {
            drawLineInterfere(canvas);
        }
    }

    private void drawPointInterfere(Canvas canvas) {
        for (int i = 0; i < pointInterfereQuantity; i++) {
            bgPaint.setColor(randomColor());
            Point interferePoint = randomPoint();
            canvas.drawCircle(interferePoint.x, interferePoint.y, random.nextInt(3) + 1, bgPaint);
        }
    }

    private void drawLineInterfere(Canvas canvas) {
        if (lineInterfereQuantity % 2 != 0) {
            lineInterfereQuantity++;
        }
        for (int i = 0; i < lineInterfereQuantity / 2; i++) {
            int m = random.nextInt(w);
            int m2 = random.nextInt(w);
            int n = random.nextInt(h);
            int n2 = random.nextInt(h);
            bgPaint.setStrokeWidth(random.nextInt(3) + 1);
            bgPaint.setColor(randomColor());
            canvas.drawLine(m, 0, m2, h, bgPaint);
            bgPaint.setColor(randomColor());
            bgPaint.setStrokeWidth(random.nextInt(3) + 1);
            canvas.drawLine(0, n, w, n2, bgPaint);
        }
        bgPaint.setStrokeWidth(1);
    }

    private int randomColor() {
        int i = (int) (Math.random() * colors.length);
        return colors[i];
    }

    private Point randomPoint() {
        return new Point(random.nextInt(w), random.nextInt(h));
    }
}
