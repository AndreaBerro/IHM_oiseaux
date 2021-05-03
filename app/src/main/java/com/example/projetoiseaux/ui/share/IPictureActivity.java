package com.example.projetoiseaux.ui.share;

import android.widget.ImageView;

public interface IPictureActivity {
    public static final int REQUEST_CAMERA = 100;
    void update(ImageView imageView, ShareFragment shareFragment);
}
