package com.jiaye.cashloan.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.jiaye.cashloan.R;

import java.util.Random;

/**
 * VerificationCodeView
 *
 * @author 贾博瑄
 */

// TODO: 2017/11/1 来源github,有待优化.
public class VerificationCodeView extends View {

    private final String TAG = getClass().getSimpleName();

    private static final String CHARS = "abcdefghijklmnpqrstuvwxyzABCDEFGHIJKLMNRQRSTUVWXYZ";

    private final int[] colors = new int[]{Color.BLACK, Color.BLUE, Color.RED, Color.RED, Color.GRAY, Color.CYAN, Color.argb(188, 100, 188, 123)};

    //验证码模式0代码存数字，1代表纯字母模式，2代码混合貌似，数字或字母随机，3代表纯中文模式（暂无）
    public static final int NUMBER = 0, STRING = 1, MIX = 2, CHINESE = 3;


    //可设置信息区
    private boolean pointInterfere = true;//是否开启点干扰

    private boolean lineInterfere = true;//是否开启线干扰

    private int lineInterfereQuantity = 3;//干扰线的数量，为双数。

    private int pointInterfereQuantity = 100;//干扰点的数量

    private int codeQuantity = 4;//验证码数量，默认为4个，最多6个。

    private int codeMode = NUMBER;//验证码模式，默认为数字模式

    private boolean matchCase = false;//是否区分大小写，如果不区分则返回小写字符。

    //

    private Paint codePaint;//用来画验证码

    private Paint bgPaint;//背景画笔

    private boolean canDraw = true;//是否绘制，解决过度重绘问题

    private int w, h;//控件宽高

    private int minW = 400, minH = 200;

    private int textSize = 80;

    private Point interferPoint;//干扰点实例

    //private Point[] points=new Point[100];

    private String verificationCode = null;//验证码，不管什么模式都输出为string。

    private Random random = new Random();

    public void setPointInterfere(boolean pointInterfere) {
        this.pointInterfere = pointInterfere;
    }

    public void setLineInterfere(boolean lineInterfere) {
        this.lineInterfere = lineInterfere;
    }

    public void setLineInterfereQuantity(int lineInterfereQuantity) {
        this.lineInterfereQuantity = lineInterfereQuantity;
    }

    public void setPointInterfereQuantity(int pointInterfereQuantity) {
        this.pointInterfereQuantity = pointInterfereQuantity;
    }

    public void setMatchCase(boolean matchCase) {
        this.matchCase = matchCase;
    }

    public String getVerificationCode() {
        if (matchCase) {
            return verificationCode.substring(0, codeQuantity).toLowerCase();
        }
        StringBuffer strings = new StringBuffer();
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

    public void setCodeMode(int codeMode) {
        this.codeMode = codeMode;
    }

    public void setCodeQuantity(int quantity) {
        if (quantity < 4 || quantity > 6) {
            return;
        }
        codeQuantity = quantity;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
        invalidate();
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VerificationCodeView);
        codeQuantity = typedArray.getInteger(R.styleable.VerificationCodeView_codeQuantity, 4);
        codeMode = typedArray.getInteger(R.styleable.VerificationCodeView_codeMode, MIX);
        matchCase = typedArray.getBoolean(R.styleable.VerificationCodeView_matchCase, true);
        typedArray.recycle();

        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Paint.Style.FILL);
        //bgPaint.setStrokeWidth(5);

        codePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        codePaint.setTextSize(textSize);
        codePaint.setColor(Color.BLACK);
        codePaint.setStyle(Paint.Style.FILL);
        //codePaint.setTextAlign(Paint.Align.CENTER);

        makeCode();

    }

    public VerificationCodeView(Context context) {
        super(context);
        initView(context, null);
    }

    public VerificationCodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //  super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int nw = getDefaultSize(widthSize, widthMode);
        int nh = getDefaultSize(heightSize, heightMode);
        nw = nw == 0 ? minW : nw;
        nh = nh == 0 ? minH : nh;
        setMeasuredDimension(nw, nh);

    }

    public static int getDefaultSize(int size, int mode) {
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
                // canDraw=true;
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

        //canvas.drawColor(Color.BLUE);
        //canvas.drawCircle(0,0,100,codePaint);
        //canvas.drawText(verificationCode,0,codeQuantity,100,100,codePaint);
        drawCode(canvas);

        drawInterfere(canvas);

    }

    //绘制干扰
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
            interferPoint = randomPoint();
            canvas.drawCircle(interferPoint.x, interferPoint.y, random.nextInt(3) + 1, bgPaint);
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

    private void dealTextSize() {
        if (w / codeQuantity > h) {
            textSize = h / 2;
        } else {
            textSize = w / (codeQuantity);
        }
        codePaint.setTextSize(textSize);
    }

    private int randomColor() {
        int i = (int) (Math.random() * colors.length);
        return colors[i];
    }

    private Point randomPoint() {
        return new Point(random.nextInt(w), random.nextInt(h));
    }

    private String makeCode() {
        String result = "";
        switch (codeMode) {
            case NUMBER:
                for (int i = 0; i < 6; i++) {
                    result += singleNumber();
                }
                break;
            case STRING:
                for (int i = 0; i < 6; i++) {
                    result += singleChar();
                }
                break;
            case MIX:
                for (int i = 0; i < 6; i++) {
                    result += mix();
                }
                break;
            case CHINESE:
                break;
            default:
                break;
        }
        verificationCode = result;
        Log.e(TAG, "code:" + verificationCode);
        return result;
    }

    private String singleNumber() {
        int m = (int) (Math.random() * 10);
        return m + "";
    }

    private String singleChar() {

        return CHARS.charAt((int) (Math.random() * 50)) + "";
    }

    private String mix() {
        if (Math.random() < 0.5) {
            return singleNumber();
        }
        return singleChar();
    }
}
