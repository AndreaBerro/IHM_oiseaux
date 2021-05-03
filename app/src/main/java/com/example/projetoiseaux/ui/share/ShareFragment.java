package com.example.projetoiseaux.ui.share;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.projetoiseaux.R;

public class ShareFragment extends Fragment {
    ImageView imageView;
    private IPictureActivity pictureActivity;

    public ShareFragment(){}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_share, container, false);
        imageView = rootView.findViewById(R.id.birdPicture);
        Log.d("ihmdemo_ShareFragment","onCreateView()   -> imageView="+imageView);
        rootView.findViewById(R.id.takePictureButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission( getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions( getActivity(),
                            new String[] { Manifest.permission.CAMERA},
                            IPictureActivity.REQUEST_CAMERA);
                } else {
                    takePicture();
                    Log.d("mylog", "Have take picture permission");
                }
            }
        });
        ((IPictureActivity)getActivity()).update(imageView,this);

        return rootView;
    }
    

    public void takePicture(){
        Log.d("ihmdemo_ShareFragment","takePicture()   -> imageView="+imageView);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getActivity().startActivityForResult( intent, IPictureActivity.REQUEST_CAMERA);
    }

    public void setImage(Bitmap bitMap, ImageView imageView) {
        if (bitMap != null) {
            imageView.setImageBitmap(bitMap);
            Log.d("mylog", "success setImage()-> imageView="+imageView);
        } else {
            Log.d("mylog", "img null");
        }
    }
}