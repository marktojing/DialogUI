package com.csnt.dialoglib;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by sunrain
 * Created Date 2020/6/19 2:47 PM
 */
public class AlertDialog extends Dialog {
    private DialogParams mDialogParams;

    private AlertDialog() {
        mDialogParams = new DialogParams();
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.dialog_alert;
    }

    public static Builder with(AppCompatActivity activity) {
        return new Builder(activity);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView tvDialogCommonTitle = view.findViewById(R.id.tv_dialog_common_title);
        TextView tvDialogCommonContent = view.findViewById(R.id.tv_dialog_common_content);
        TextView tvDialogCommonPositive = view.findViewById(R.id.tv_dialog_common_positive);
        TextView tvDialogCommonNegative = view.findViewById(R.id.tv_dialog_common_negative);

        //填充数据
        tvDialogCommonTitle.setText(mDialogParams.title);
        tvDialogCommonContent.setText(mDialogParams.content);
        tvDialogCommonPositive.setText(mDialogParams.positive);
        tvDialogCommonNegative.setText(mDialogParams.negative);

        //监听
        tvDialogCommonPositive.setOnClickListener(this);
        tvDialogCommonNegative.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_dialog_common_positive) {
            dismiss();
            if (mDialogParams.onPositiveClickListener != null) {
                mDialogParams.onPositiveClickListener.onClick(v);
            }
        } else if (id == R.id.tv_dialog_common_negative) {
            dismiss();
            if (mDialogParams.onNegativeClickListener != null) {
                mDialogParams.onNegativeClickListener.onClick(v);
            }
        }
    }

    @Override
    protected boolean setCancelable() {
        return mDialogParams.isCancelable;
    }

    public class DialogParams {
        String title;
        String positive;
        String negative;
        String content;
        boolean isCancelable = true;
        View.OnClickListener onPositiveClickListener;
        View.OnClickListener onNegativeClickListener;
    }

    public static class Builder {
        AppCompatActivity activity;
        DialogParams P;
        AlertDialog alertDialog;

        public Builder(AppCompatActivity activity) {
            alertDialog = new AlertDialog();
            this.P = alertDialog.mDialogParams;
            this.activity = activity;
        }

        public Builder setTitle(String val) {
            P.title = val;
            return this;
        }

        public Builder setContent(String val) {
            P.content = val;
            return this;
        }

        public Builder setCancelable(boolean val) {
            P.isCancelable = val;
            return this;
        }

        public Builder setPositiveButton(String positive, View.OnClickListener onClickListener) {
            P.onPositiveClickListener = onClickListener;
            P.positive = positive;
            return this;
        }

        public Builder setNegativeButton(String negative, View.OnClickListener onClickListener) {
            P.onNegativeClickListener = onClickListener;
            P.negative = negative;
            return this;
        }

        public Builder setPositiveButton(@StringRes int positive, View.OnClickListener onClickListener) {
            P.onPositiveClickListener = onClickListener;
            P.positive = activity.getString(positive);
            return this;
        }

        public Builder setNegativeButton(@StringRes int negative, View.OnClickListener onClickListener) {
            P.onNegativeClickListener = onClickListener;
            P.negative = activity.getString(negative);
            return this;
        }

        public AlertDialog show() {
            alertDialog.show(activity);
            return alertDialog;
        }
    }
}
