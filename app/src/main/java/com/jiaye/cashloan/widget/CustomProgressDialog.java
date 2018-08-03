package com.jiaye.cashloan.widget;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jiaye.cashloan.R;
import com.satcatche.library.widget.BaseDialog;

public class CustomProgressDialog extends BaseDialog {

    private TextView textDialogMessage;

    public CustomProgressDialog(Context context) {
        super(context);
        View dialog = LayoutInflater.from(context).inflate(R.layout.custom_progress_dialog, null);
        textDialogMessage = dialog.findViewById(R.id.message);
        setContentView(dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void show(String message) {
        textDialogMessage.setText(message);
        super.show();
    }

    @Override
    public void show() {
        super.show();
        textDialogMessage.setVisibility(View.GONE);
    }
}
