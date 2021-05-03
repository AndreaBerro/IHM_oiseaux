package com.example.projetoiseaux.ui.share.Camera;

import android.widget.ImageView;

import com.example.projetoiseaux.ui.share.ShareFragment;

public interface IPictureActivity {
    public static final int REQUEST_CAMERA = 100;
    public static final int REQUEST_IMAGE = 101;
    void update(ShareFragment shareFragment);
    ShareFragment getShareFragment();
}
