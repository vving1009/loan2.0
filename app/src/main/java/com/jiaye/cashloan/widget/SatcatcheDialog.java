package com.jiaye.cashloan.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jiaye.cashloan.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class SatcatcheDialog extends BaseDialog {

    private Context mContext;
    private int mButtonNum;
    private String mTitle;
    private String mMessage;
    private boolean mEnableEditText;
    private String mPositiveButtonText, mNegativeButtonText;
    private DialogInterface.OnClickListener mPositiveButtonListener, mNegativeButtonListener;
    private OnDismissListener mOnDismissListener;
    private TextView mTitleText;
    private TextView mMessageText;
    private EditText mEditText;
    private ProgressBar mProgressBar;
    private LinearLayout mButtonArea;
    private Button mPositiveBtn, mNegativeBtn;

    private SatcatcheDialog(Context context, @StyleRes int themeResId, int buttonNum, String title,
                            String message, String positiveButtonText, String negativeButtonText,
                            DialogInterface.OnClickListener positiveButtonListener,
                            DialogInterface.OnClickListener negativeButtonListener,
                            OnDismissListener onDismissListener, boolean enableEditText) {
        super(context, themeResId);
        mContext = context;
        mButtonNum = buttonNum;
        mTitle = title;
        mMessage = message;
        mEnableEditText = enableEditText;
        mPositiveButtonText = positiveButtonText;
        mNegativeButtonText = negativeButtonText;
        mPositiveButtonListener = positiveButtonListener;
        mNegativeButtonListener = negativeButtonListener;
        mOnDismissListener = onDismissListener;
        init();
    }

    private void init() {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.satcatche_dialog, null, false);
        mTitleText = rootView.findViewById(R.id.title);
        mMessageText = rootView.findViewById(R.id.message);
        mEditText = rootView.findViewById(R.id.edit_sms);
        mButtonArea = rootView.findViewById(R.id.button_area);
        mNegativeBtn = rootView.findViewById(R.id.negative);
        mPositiveBtn = rootView.findViewById(R.id.positive);
        mProgressBar = rootView.findViewById(R.id.progress_bar);

        if (!TextUtils.isEmpty(mTitle)) {
            mTitleText.setText(mTitle);
        }
        if (!TextUtils.isEmpty(mMessage)) {
            mMessageText.setText(mMessage);
        }
        if (mEnableEditText) {
            mEditText.setVisibility(VISIBLE);
        }
        if (!TextUtils.isEmpty(mPositiveButtonText)) {
            mPositiveBtn.setText(mPositiveButtonText);
        }
        if (!TextUtils.isEmpty(mNegativeButtonText)) {
            mNegativeBtn.setText(mNegativeButtonText);
        }
        mPositiveBtn.setOnClickListener(v -> {
            if (mPositiveButtonListener != null) {
                mPositiveButtonListener.onClick(SatcatcheDialog.this, Dialog.BUTTON_POSITIVE);
            }
            dismiss();
        });
        mNegativeBtn.setOnClickListener(v -> {
            if (mNegativeButtonListener != null) {
                mNegativeButtonListener.onClick(SatcatcheDialog.this, Dialog.BUTTON_NEGATIVE);
            }
            dismiss();
        });

        switch (mButtonNum) {
            case 0:
                mButtonArea.setVisibility(INVISIBLE);
                setCancelable(true);
                setCanceledOnTouchOutside(true);
                break;
            case 1:
                mPositiveBtn.setVisibility(VISIBLE);
                mNegativeBtn.setVisibility(GONE);
                break;
            case 2:
                mPositiveBtn.setVisibility(VISIBLE);
                mNegativeBtn.setVisibility(VISIBLE);
                break;
        }
        setOnDismissListener(mOnDismissListener);
        setContentView(rootView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            mMessageText.setText(message);
        }
    }
    
    public String getInputText() {
        return mEditText.getText().toString();
    }

    @IntDef({VISIBLE, INVISIBLE, GONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {}

    public void setProgressVisibility(@Visibility int visibility) {
        mProgressBar.setVisibility(visibility);
    }

    public void setPositiveBtnVisibility(@Visibility int visibility) {
        mPositiveBtn.setVisibility(visibility);
    }

    public void setNegativeBtnVisibility(@Visibility int visibility) {
        mNegativeBtn.setVisibility(visibility);
    }

    public void setAllBtnVisibility(@Visibility int visibility) {
        mButtonArea.setVisibility(visibility);
    }

    public void setProgress(int progress) {
        mProgressBar.setProgress(progress);
    }

    public static class Builder {

        private Context mContext;
        private int mThemeResId;
        private int mButtonNum;  //下方按钮数量max = 2
        private String mTitle;   //dialog标题
        private String mMessage;  //dialog信息
        private boolean mEnableEditText;  //dialog含有输入框
        private String mPositiveButtonText, mNegativeButtonText;
        private DialogInterface.OnClickListener mPositiveButtonListener, mNegativeButtonListener;
        private DialogInterface.OnDismissListener mOnDismissListener;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setTheme(@StyleRes int themeResId) {
            mThemeResId = themeResId;
            return this;
        }

        public Builder setButtonNumber(int num) {
            mButtonNum = num;
            return this;
        }

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setMessage(String message) {
            mMessage = message;
            return this;
        }

        public Builder setEnableEditText(boolean enableEditText) {
            mEnableEditText = enableEditText;
            return this;
        }

        public Builder setPositiveButton(String text, DialogInterface.OnClickListener listener) {
            mPositiveButtonText = text;
            mPositiveButtonListener = listener;
            mButtonNum = 1;
            return this;
        }

        public Builder setNegativeButton(String text, DialogInterface.OnClickListener listener) {
            mNegativeButtonText = text;
            mNegativeButtonListener = listener;
            mButtonNum = 2;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener listener) {
            mOnDismissListener = listener;
            return this;
        }

        public SatcatcheDialog build() {
            return new SatcatcheDialog(mContext, mThemeResId, mButtonNum, mTitle, mMessage,
                    mPositiveButtonText, mNegativeButtonText, mPositiveButtonListener,
                    mNegativeButtonListener, mOnDismissListener, mEnableEditText);
        }
    }
}