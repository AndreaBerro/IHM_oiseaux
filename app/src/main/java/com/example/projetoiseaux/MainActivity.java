package com.example.projetoiseaux;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projetoiseaux.ui.map.IGPSActivity;
import com.example.projetoiseaux.ui.map.MapFragment;
import com.example.projetoiseaux.ui.share.IPictureActivity;
import com.example.projetoiseaux.ui.share.ShareFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements IPictureActivity, IGPSActivity {
    private Bitmap picture;
    private ShareFragment shareFragment;
    private MapFragment mapFragment;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shareFragment = (ShareFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_share);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_map, R.id.navigation_share , R.id.navigation_favorite, R.id.navigation_me)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
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
        switch (requestCode) {
            case REQUEST_CAMERA:{
                if(resultCode == RESULT_OK) {
                    Log.d("ihmdemo_MainActivity","onActivityResult()   ---> imageView="+imageView);
                    picture = (Bitmap) data.getExtras().get("data");
                    shareFragment.setImage(picture, imageView);
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(getApplicationContext(), "Picture Cancel", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Picture Action failed", Toast.LENGTH_LONG).show();
                }
            } break;
            default: break;
        }
    }

    @Override
    public void update(ImageView imageView, ShareFragment shareFragment) {
        Log.d("ihmdemo_MainActivity","update()");
        this.imageView = imageView;
        this.shareFragment=shareFragment;
    }

    @Override
    public void updateGps(MapFragment mapFragment) {
        this.mapFragment = mapFragment;
    }

    @Override
    public void moveCamera() {
        //todo ... maybe useless...
    }
}


// there is no MeFragement and FavrateFragment