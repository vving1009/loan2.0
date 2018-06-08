package com.jiaye.cashloan.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;

/**
 * LoanEditText
 *
 * @author 贾博瑄
 */

@SuppressWarnings("unused")
public class LoanEditText extends RelativeLayout {

    public interface OnClickVerificationCode {

        void onClickVerificationCode();
    }

    private OnClickVerificationCode mOnClickVerificationCode;

    private EditText mEditText;

    private TextView mTextVerification;

    private VerificationCodeView mImgVerification;

    private ImageView mImg;

    private TextView mTextError;

    private CountDownTimer countDownTimer;

    public LoanEditText(Context context) {
        this(context, null);
    }

    public LoanEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoanEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDetachedFromWindow() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        super.onDetachedFromWindow();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (mEditText != null) {
            mEditText.setEnabled(enabled);
        }
        if (mTextVerification != null) {
            mTextVerification.setEnabled(enabled);
        }
        if (mImgVerification != null) {
            mImgVerification.setEnabled(enabled);
        }
        if (mImg != null) {
            mImg.setEnabled(enabled);
        }
        if (mTextError != null) {
            mTextError.setEnabled(enabled);
        }
    }

    public void setVerificationBtnEnabled(boolean enabled) {
        if (mTextVerification != null) {
            mTextVerification.setEnabled(enabled);
            if (mTextVerification.isEnabled()) {
                mTextVerification.setTextColor(getResources().getColor(R.color.send_sms_btn_text_enable));
            } else {
                mTextVerification.setTextColor(getResources().getColor(R.color.send_sms_btn_text_disable));
            }
        }
    }

    public void setOnClickVerificationCode(OnClickVerificationCode OnClickCaptchaListener) {
        mOnClickVerificationCode = OnClickCaptchaListener;
    }

    public void addTextChangedListener(TextWatcher watcher) {
        mEditText.addTextChangedListener(watcher);
    }

    public Editable getText() {
        return mEditText.getText();
    }

    public void setText(String text) {
        mEditText.setText(text);
    }

    public String getCode() {
        if (mImgVerification != null) {
            return mImgVerification.getVerificationCode();
        } else {
            return "";
        }
    }

    public void setCode(Bitmap bitmap) {
        if (mImg != null) {
            mImg.setImageBitmap(bitmap);
        }
    }

    public void startCountDown() {
        if (countDownTimer == null) {
            countDownTimer = new CountDownTimer(60 * 1000, 1) {

                @Override
                public void onTick(long millisUntilFinished) {
                    mTextVerification.setEnabled(false);
                    mTextVerification.setText(String.format(getResources().getString(R.string.send_count_down), String.valueOf(millisUntilFinished / 1000)));
                    mTextVerification.setTextColor(getResources().getColor(R.color.send_sms_btn_text_disable));
                }

                @Override
                public void onFinish() {
                    mTextVerification.setEnabled(true);
                    mTextVerification.setText(getResources().getString(R.string.send_again));
                    mTextVerification.setTextColor(getResources().getColor(R.color.send_sms_btn_text_enable));
                }
            };
        }
        countDownTimer.start();
    }

    public void setError(String error) {
        mTextError.setText(error);
    }

    @SuppressLint("RtlHardcoded")
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        float density = getResources().getDisplayMetrics().density;
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoanEditText, defStyleAttr, 0);
        int inputType = typedArray.getInt(R.styleable.LoanEditText_android_inputType, EditorInfo.TYPE_CLASS_TEXT);
        String hint = typedArray.getString(R.styleable.LoanEditText_android_hint);
        int maxLength = typedArray.getInt(R.styleable.LoanEditText_android_maxLength, -1);
        int editHeight = typedArray.getDimensionPixelOffset(R.styleable.LoanEditText_editHeight, (int) (28 * density));
        int errorHeight = typedArray.getDimensionPixelOffset(R.styleable.LoanEditText_errorHeight, LayoutParams.WRAP_CONTENT);
        Drawable icon = typedArray.getDrawable(R.styleable.LoanEditText_icon);
        int iconMarginLeft = typedArray.getLayoutDimension(R.styleable.LoanEditText_iconMarginLeft, 0);
        int inputMarginLeft = typedArray.getLayoutDimension(R.styleable.LoanEditText_inputMarginLeft, (int) (34 * density));
        boolean isEnableVerification = typedArray.getBoolean(R.styleable.LoanEditText_enable_verification, false);
        int verificationType = typedArray.getInt(R.styleable.LoanEditText_verification_type, 0);
        boolean isRequestForce = typedArray.getBoolean(R.styleable.LoanEditText_request_force, false);
        int underLineLength = typedArray.getLayoutDimension(R.styleable.LoanEditText_underlineLength, LayoutParams.MATCH_PARENT);
        int underLineMarginLeft = typedArray.getLayoutDimension(R.styleable.LoanEditText_underlineMarginLeft, (int) (34 * density));
        int underLineColor = typedArray.getColor(R.styleable.LoanEditText_underLineColor, getResources().getColor(R.color.color_blue));
        int textSize = typedArray.getDimensionPixelOffset(R.styleable.LoanEditText_textSize, sp2px(12));

        /*外层布局*/
        RelativeLayout editLayout = new RelativeLayout(context);
        editLayout.setId(R.id.loan_edit_layout);
        addView(editLayout);
        editLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, editHeight));

        /*左侧按钮布局*/
        ImageView imageView = new ImageView(context);
        imageView.setImageDrawable(icon);
        editLayout.addView(imageView);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(CENTER_VERTICAL);
        layoutParams.setMargins(iconMarginLeft, 0, 0, 0);
        imageView.setLayoutParams(layoutParams);

        /*输入框布局*/
        mEditText = new EditText(context);
        mEditText.setInputType(inputType);
        mEditText.setHint(hint);
        if (maxLength >= 0) {
            mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        } else {
            mEditText.setFilters(new InputFilter[0]);
        }
        mEditText.setMaxLines(1);
        mEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mEditText.setTextColor(Color.parseColor("#282828"));
        mEditText.setHintTextColor(Color.parseColor("#989898"));
        mEditText.setBackgroundResource(0);
        mEditText.setPadding(0, 0, 0, 0);
        editLayout.addView(mEditText);
        LayoutParams lInput = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lInput.addRule(ALIGN_PARENT_LEFT);
        lInput.addRule(CENTER_VERTICAL);
        lInput.setMargins(inputMarginLeft, 0, 0, 0);
        mEditText.setLayoutParams(lInput);

        /*验证码*/
        if (isEnableVerification) {
            switch (verificationType) {
                case 0:
                    mTextVerification = new TextView(context);
                    mTextVerification.setTextSize(9);
                    mTextVerification.setTextColor(getResources().getColor(R.color.send_sms_btn_text_enable));
                    mTextVerification.setText(getResources().getString(R.string.send_verification_code));
                    mTextVerification.setGravity(Gravity.CENTER);
                    mTextVerification.setBackgroundDrawable(getResources().getDrawable(R.drawable.loan_btn_verification));
                    mTextVerification.setOnClickListener(v -> {
                        if (mOnClickVerificationCode != null) {
                            mOnClickVerificationCode.onClickVerificationCode();
                        }
                    });
                    editLayout.addView(mTextVerification);
                    LayoutParams lText = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    lText.addRule(ALIGN_PARENT_RIGHT);
                    lText.addRule(ALIGN_PARENT_TOP);
                    mTextVerification.setLayoutParams(lText);
                    break;
                case 1:// 自生成图形验证码
                    mImgVerification = new VerificationCodeView(context);
                    editLayout.addView(mImgVerification);
                    LayoutParams lImg = new LayoutParams((int) (70 * density), (int) (30 * density));
                    lImg.addRule(ALIGN_PARENT_RIGHT);
                    lImg.addRule(CENTER_VERTICAL);
                    mImgVerification.setLayoutParams(lImg);
                    break;
                case 2:// 外部传入图形验证码
                    mImg = new ImageView(context);
                    editLayout.addView(mImg);
                    LayoutParams lOutImg = new LayoutParams((int) (70 * density), (int) (30 * density));
                    lOutImg.addRule(ALIGN_PARENT_RIGHT);
                    lOutImg.addRule(CENTER_VERTICAL);
                    mImg.setLayoutParams(lOutImg);
                    mImg.setOnClickListener(v -> {
                        if (mOnClickVerificationCode != null) {
                            mOnClickVerificationCode.onClickVerificationCode();
                        }
                    });
                    break;
            }
        }

        /*直线*/
        View line = new View(context);
        line.setBackgroundColor(underLineColor);
        editLayout.addView(line);
        LayoutParams lLine = new LayoutParams(underLineLength, (int) (1 * density));
        lLine.addRule(ALIGN_PARENT_BOTTOM);
        lLine.setMargins(underLineMarginLeft, 0, 0, 0);
        line.setLayoutParams(lLine);

        /*错误提示*/
        mTextError = new TextView(context);
        mTextError.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mTextError.setTextSize(8);
        mTextError.setTextColor(getResources().getColor(R.color.color_red));
        addView(mTextError);
        LayoutParams lError = new LayoutParams(LayoutParams.WRAP_CONTENT, errorHeight);
        lError.addRule(ALIGN_PARENT_RIGHT);
        lError.addRule(BELOW, R.id.loan_edit_layout);
        mTextError.setLayoutParams(lError);
        mTextError.setVisibility(GONE);

        if (isRequestForce) {
            mEditText.requestFocus();
        }
    }

    public int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, getResources().getDisplayMetrics());
    }

}
