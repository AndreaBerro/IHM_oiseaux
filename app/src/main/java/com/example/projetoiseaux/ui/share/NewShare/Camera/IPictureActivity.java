package com.example.projetoiseaux.ui.share.NewShare.Camera;

import com.example.projetoiseaux.ui.share.NewShare.ShareFragment;

public interface IPictureActivity {
    public static final int REQUEST_CAMERA = 100;
    public static final int REQUEST_IMAGE = 101;
    void update(ShareFragment shareFragment);
    ShareFragment getShareFragment();
}
