package com.jiaye.cashloan.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiaye.cashloan.R;

public class SatcatcheDialog extends BaseDialog {

    private Context mContext;
    private int mButtonNum;
    private String mTitle;
    private String mMessage;
    private boolean mEnableEditText;
    private String mPositiveButtonText, mNegativeButtonText;
    private DialogInterface.OnClickListener mPositiveButtonListener, mNegativeButtonListener;
    private OnDismissListener mOnDismissListener;
    private EditText mEditText;

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
    }

    private void init() {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.satcatche_dialog, null, false);
        TextView title = rootView.findViewById(R.id.title);
        TextView message = rootView.findViewById(R.id.message);
        mEditText = rootView.findViewById(R.id.edit_sms);
        LinearLayout buttonArea = rootView.findViewById(R.id.button_area);
        Button negativeBtn = rootView.findViewById(R.id.negative);
        Button positiveBtn = rootView.findViewById(R.id.positive);

        if (!TextUtils.isEmpty(mTitle)) {
            title.setText(mTitle);
        }
        if (!TextUtils.isEmpty(mMessage)) {
            message.setText(mMessage);
        }
        if (mEnableEditText) {
            mEditText.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(mPositiveButtonText)) {
            positiveBtn.setText(mPositiveButtonText);
        }
        if (!TextUtils.isEmpty(mNegativeButtonText)) {
            negativeBtn.setText(mNegativeButtonText);
        }
        positiveBtn.setOnClickListener(v -> {
            if (mPositiveButtonListener != null) {
                mPositiveButtonListener.onClick(SatcatcheDialog.this, Dialog.BUTTON_POSITIVE);
            }
            dismiss();
        });
        negativeBtn.setOnClickListener(v -> {
            if (mNegativeButtonListener != null) {
                mNegativeButtonListener.onClick(SatcatcheDialog.this, Dialog.BUTTON_NEGATIVE);
            }
            dismiss();
        });

        switch (mButtonNum) {
            case 0:
                buttonArea.setVisibility(View.INVISIBLE);
                setCancelable(true);
                setCanceledOnTouchOutside(true);
                break;
            case 1:
                positiveBtn.setVisibility(View.VISIBLE);
                negativeBtn.setVisibility(View.GONE);
                break;
            case 2:
                positiveBtn.setVisibility(View.VISIBLE);
                negativeBtn.setVisibility(View.VISIBLE);
                break;
        }
        setOnDismissListener(mOnDismissListener);
        setContentView(rootView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public String getInputText() {
        return mEditText.getText().toString();
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