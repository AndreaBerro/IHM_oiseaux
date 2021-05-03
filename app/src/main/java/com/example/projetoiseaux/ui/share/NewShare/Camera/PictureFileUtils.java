package com.example.projetoiseaux.ui.share.NewShare.Camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


// todo : mettre dans le main floder
public class PictureFileUtils {
    String currentPhotoPath;
    IPictureActivity activity;

    public PictureFileUtils(IPictureActivity activity){
        this.activity = activity;
    }

    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        Log.d("mylog", "create file " + imageFile.getAbsolutePath());
        return imageFile;
    }

    public static Bitmap getPictureBitmap(String path){
        try {
            File photoFile = getPictureFile(path);
            return BitmapFactory.decodeStream(new FileInputStream(photoFile));
        } catch (FileNotFoundException e ){
            e.printStackTrace();
        }
        return null;
    }

    public static File getPictureFile(String path){
        // Log.d("mylog", "load image in: " + path);
        return new File(path);
    }

}

