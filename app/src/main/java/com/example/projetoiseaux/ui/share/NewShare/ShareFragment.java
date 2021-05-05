package com.example.projetoiseaux.ui.share.NewShare;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.projetoiseaux.BuildConfig;
import com.example.projetoiseaux.R;
import com.example.projetoiseaux.ui.share.Client.UploadIntentService;
import com.example.projetoiseaux.ui.share.NewShare.Camera.PictureFileUtils;
import com.example.projetoiseaux.ui.share.NewShare.Camera.IPictureActivity;
import com.example.projetoiseaux.ui.share.Client.Client;
import com.example.projetoiseaux.ui.share.NewShare.GridPictures.GridViewAdapter;
import com.example.projetoiseaux.ui.share.NewShare.GridPictures.ListSharePicture;
import com.example.projetoiseaux.ui.share.NewShare.GridPictures.MyGridView;
import com.example.projetoiseaux.ui.share.Share;
import com.example.projetoiseaux.ui.share.NewShare.location.SelectLocation;
import com.example.projetoiseaux.ui.share.NewShare.time.DatePickerFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import me.nereo.multi_image_selector.MultiImageSelector;

import static com.example.projetoiseaux.ui.share.NewShare.Camera.IPictureActivity.REQUEST_IMAGE;
import static com.example.projetoiseaux.ui.share.NewShare.location.ILocation.SELECT_LOCATION;

public class ShareFragment extends Fragment {
    private View rootView;

    private PictureFileUtils pictureFileUtils;
    private File photoFile;

    private MyGridView grid_photo;

    private GridViewAdapter gridViewAdapter;

    private IPictureActivity pictureActivity;

    private String date;
    private Address address;

    private Share share;



    public ShareFragment(){
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Create callback to parent activity
        try {
            Log.d("mylog", "create share");
            pictureActivity = (IPictureActivity) getActivity();

            pictureFileUtils = new PictureFileUtils(pictureActivity);
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement IGPSActivity");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_share, container, false);
        this.rootView = rootView;

        ListSharePicture listSharePicture = new ListSharePicture();
        gridViewAdapter = new GridViewAdapter(getContext(), listSharePicture);

        grid_photo = rootView.findViewById(R.id.gridView);
        grid_photo.setAdapter(gridViewAdapter);
        grid_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(gridViewAdapter.getItemId(position) == ListSharePicture.BUTTON_ADD){
                    addPicture();
                }
            }
        });

        grid_photo.setLongClickable(true);
        grid_photo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("mylog", "long click");
                gridViewAdapter.deletePicture(position);
                return false;
            }
        });


        rootView.findViewById(R.id.share_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SelectLocation.class);
                Toast.makeText(getContext(), "choose a location", Toast.LENGTH_SHORT).show();
                getActivity().startActivityForResult(intent, SELECT_LOCATION);
            }
        });

        rootView.findViewById(R.id.share_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
                Toast.makeText(getContext(), "choose a time", Toast.LENGTH_SHORT).show();
            }
        });

        initPublicButton(rootView);
        initBackButton(rootView);

        ((IPictureActivity)getActivity()).update(this);
        return rootView;
    }

    private void initBackButton(View rootView) {
        rootView.findViewById(R.id.share_return).setOnClickListener(click -> {
            finishShare();
        });
    }

    private void finishShare() {

        Log.d("mylog", "Will finish");
        FragmentManager fragmentManager= getFragmentManager();
        Fragment fragment=fragmentManager.findFragmentByTag("NewShare");
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }

    // todo check non null
    //      Send also the image fileName
    private void initPublicButton(View rootView) {
        Client c = new Client();
        rootView.findViewById(R.id.public_share).setOnClickListener(click -> {
            String desc = ((EditText)rootView.findViewById(R.id.share_desc)).getText().toString();
            c.startNetThread();

            if (notcompleted(desc)) return;


            share = new Share("Ben", date, desc, address.getLatitude(), address.getLongitude(), gridViewAdapter.getListPictureName());
            try {
                URL url = new URL("http://192.168.56.1:9428/api/share");
                Client.uploadingCallRecords(share.getJsonObj(), url);
                List<File> imageList = gridViewAdapter.getListPictureFile();
                for (File image: imageList) {
                    if (image.getPath().equals("Null")) break;
                    Client.uploadImage("UserTestImg", image);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            finishShare();
        });
    }

    private boolean notcompleted(String desc) {
        if (desc == null){
            Toast.makeText(getContext(), "Please write some thing", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (address == null){
            Toast.makeText(getContext(), "Please choose an address", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (date == null) {
            Toast.makeText(getContext(), "Please choose a date", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void addPicture() {
//        takePicture();
        //使用BottomSheetDialog方式实现底部弹窗
        BottomSheetDialog bottomSheet = new BottomSheetDialog(getContext());//实例化BottomSheetDialog
        bottomSheet.setCancelable(true);//设置点击外部是否可以取消
        View bottomSheetView = getLayoutInflater().inflate(R.layout.media_or_camera_dialog_layout, null);
        bottomSheet.setContentView(bottomSheetView);//设置对框框中的布局

        bottomSheetView.findViewById(R.id.cancel).setOnClickListener(click -> bottomSheet.cancel());
        bottomSheetView.findViewById(R.id.open_from_camera).setOnClickListener(click -> {
            takePicture();
            bottomSheet.cancel();
        });
        bottomSheetView.findViewById(R.id.open_album).setOnClickListener(click -> {
            openAlbum();
            bottomSheet.cancel();
        });

        //bottomSheetView.findViewById(R.id.open_from_camera).setOnClickListener(click -> bottomSheet.closeOptionsMenu());
        bottomSheet.show();//显示弹窗
    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    private void openAlbum() {
        MultiImageSelector.create(getContext())
                .count(10 - gridViewAdapter.getCount())  // max photo
                .showCamera(false)
                .start(getActivity(), REQUEST_IMAGE);
    }

    public void takePicture(){
        if(ContextCompat.checkSelfPermission( getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions( getActivity(),
                    new String[] { Manifest.permission.CAMERA},
                    IPictureActivity.REQUEST_CAMERA);
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                // Create the File where the photo should go
                photoFile = null;
                try {
                    // todo check permission stroage
                    photoFile = pictureFileUtils.createImageFile();
                } catch (IOException ex) {

                    Log.e("mylog", "create file error");
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    takePictureIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri u = FileProvider.getUriForFile(requireContext(),  //  todo ???
                            BuildConfig.APPLICATION_ID + ".provider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, u);
                    //Fragmeent中使用getActivity().startActivityForResult() 避免请求号被更改
                    //the requestCode is changed by the Activity that owns the Fragment
                    Log.d("mylog", "start take picture intent");
                    getActivity().startActivityForResult(takePictureIntent, IPictureActivity.REQUEST_CAMERA);
                }
            }
        }
    }

    public void uploadService(JSONObject json, URL url) {
        Intent intent = new Intent(getContext(), UploadIntentService.class);
        intent.putExtra("json",json.toString());
        intent.putExtra("url", url);
        getContext().startService(intent);
    }

    public void onTakePictureSuccess(){
        Log.d("mylog", "In ShareFragment : take picture success in : " + photoFile.getAbsolutePath());
        gridViewAdapter.addPicture(photoFile.getAbsolutePath());
    }

    public void onTakeMultiPictureSuccess(List<String> path){
        gridViewAdapter.addListPicture(path);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void addDate(String date) {
        this.date += date;
    }

    public Address getAddress() {
        return address;
    }

    public void setLocation(double latitude, double longitude) {
        Address address = getPlaceName(latitude, longitude);
        this.address = address;
        ((TextView)getActivity().findViewById(R.id.location_text)).setText(address.getAddressLine(0));
    }

    Address getPlaceName(double latitude, double longitude){
        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1); // max result == 1 //仅需要一个结果
            Log.d("mylog", latitude + " " + longitude + " " + addresses);
            return addresses.get(0);
        } catch (IOException e){
            return null;
        }
    }
}