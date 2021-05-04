package com.example.projetoiseaux.ui.share;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.projetoiseaux.R;

public class ShareDialog extends Dialog {
    public ShareDialog(Context context) {
        super(context, R.style.dialog);
        setOwnerActivity((Activity) context);
        // getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(R.layout.coupons_dialog_layout);

    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);
    }
}
