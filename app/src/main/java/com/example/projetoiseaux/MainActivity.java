package com.example.projetoiseaux;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projetoiseaux.ui.share.Client.IUploadActivity;
import com.example.projetoiseaux.Bird.UploadBird;
import com.example.projetoiseaux.ui.map.IGPSActivity;
import com.example.projetoiseaux.ui.map.MapFragment;
import com.example.projetoiseaux.ui.share.Client.Client;
import com.example.projetoiseaux.ui.share.NewShare.Camera.IPictureActivity;
import com.example.projetoiseaux.ui.share.NewShare.ShareFragment;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

import static com.example.projetoiseaux.ui.share.NewShare.location.ILocation.LATITUDE;
import static com.example.projetoiseaux.ui.share.NewShare.location.ILocation.LONGITUDE;
import static com.example.projetoiseaux.ui.share.NewShare.location.ILocation.SELECT_LOCATION;

public class MainActivity extends AppCompatActivity implements IPictureActivity, IGPSActivity, IUploadActivity {
    private Bitmap picture;
    private ShareFragment shareFragment;
    private MapFragment mapFragment;
    private ImageView imageView;
    List<UploadBird> uploadList;
    String filter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide the ActionBar
        getSupportActionBar().hide();

        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_map, R.id.navigation_search, R.id.navigation_share , R.id.navigation_discovery, R.id.navigation_me)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        // Send the registration token
        sendTokenToServer();
        Intent intentReceiver = getIntent();
        filter = intentReceiver.getStringExtra("name");

        uploadList = new ArrayList<UploadBird>();
        uploadList.add(new UploadBird("Eurasian blue tit",new GeoPoint(43.615554,7.071800),new Date(), null));
        uploadList.add(new UploadBird("Eurasian blue tit",new GeoPoint(43.619554,7.076800),new Date(), null));
        uploadList.add(new UploadBird("House sparrow",new GeoPoint(43.612554,7.079800),new Date(), null));
    }

    private void sendTokenToServer() {
        Task<String> token = FirebaseMessaging.getInstance().getToken();
        Client.sendToken(token);
        Log.d("mylog", "Get token ---> " + token.getResult());
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case REQUEST_CAMERA:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast toast = Toast.makeText(getApplicationContext(), "Camera autorisation granted", Toast.LENGTH_LONG);
                    toast.show();
                    shareFragment.takePicture();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Camera autorisation Not granted", Toast.LENGTH_LONG);
                    toast.show();
                }
            } break;
            case  REQUEST_PERMISSION_GPS:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast toast = Toast.makeText(getApplicationContext(), "GPS authorisation granted", Toast.LENGTH_LONG);
                    toast.show();
                    Log.d("gps", "GPS authorisation granted");
                    mapFragment.refocus();
                    //todo get the current position set current position.
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "GPS autorisation Not granted", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }
    }

    /**
     *  callback from stratActivity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("mylog", "on picture result " + resultCode + "  " + requestCode);
        switch (requestCode) {
            case REQUEST_CAMERA:{
                Log.d("mylog", "on picture result " + resultCode + "  " + requestCode);
                if(resultCode == RESULT_OK) {
                    Log.d("mylog", "on picture result ok " + data.getExtras());
                    shareFragment.onTakePictureSuccess();
                } else if (resultCode == RESULT_CANCELED) {
                    Log.d("mylog", "on picture result cancle ");
                    Toast toast = Toast.makeText(getApplicationContext(), "Picture cancle", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Log.d("mylog", "on picture result faille ");
                    Toast toast = Toast.makeText(getApplicationContext(), "Action faille", Toast.LENGTH_LONG);
                    toast.show();
                }
            } break;
            case SELECT_LOCATION:{
                if(resultCode == RESULT_OK) {
                    //Log.d("mylog", "get data: " + data.getDoubleExtra(LATITUDE, 0) +  data.getDoubleExtra(LONGITUDE, 0));
                    shareFragment.setLocation( data.getDoubleExtra(LATITUDE, 0), data.getDoubleExtra(LONGITUDE, 0));
                } else if (resultCode == RESULT_CANCELED) {
                    Log.d("mylog", "on addr result cancle ");
                    Toast toast = Toast.makeText(getApplicationContext(), "addr cancle", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Log.d("mylog", "on addr result faille ");
                    Toast toast = Toast.makeText(getApplicationContext(), "Action faille", Toast.LENGTH_LONG);
                    toast.show();
                }
            } break;
            case REQUEST_IMAGE:{
                if(resultCode == RESULT_OK){
                    List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    shareFragment.onTakeMultiPictureSuccess(path);
                    Log.d("mylog", path.toString());
                }
            } break;

            default: break;
        }
    }

    @Override
    public void update(ShareFragment shareFragment) {
        Log.d("ihmdemo_MainActivity","update()");
        this.shareFragment = shareFragment;
    }

    @Override
    public void updateGps(MapFragment mapFragment) {
        this.mapFragment = mapFragment;
    }

    @Override
    public void moveCamera() {
        //todo ... maybe useless...
    }

    public ShareFragment getShareFragment() {
        return shareFragment;
    }

    @Override
    public void setUploadList(List<UploadBird> uploadList) {
        Log.d("mylog", "in Main ___>" + uploadList.get(0).getGeoPoint());
        this.uploadList = uploadList;
        Log.d("mylog", "in Main ___ after>" + this.uploadList.get(0).getGeoPoint());
    }

    @Override
    public void resetFilter() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    @Override
    public String getFilter() {
        return filter;
    }

    @Override
    public List<UploadBird> getUploadList() {
        if(filter != null && filter != "") {
            List<UploadBird> filteredList = new ArrayList<>();
            for(UploadBird bird : uploadList) {
                if(bird.getName().equals(filter)) {
                    filteredList.add(bird);
                }
            }
            return filteredList;
        } else {
            return uploadList;
        }

    }
}


// there is no MeFragement and FavrateFragment