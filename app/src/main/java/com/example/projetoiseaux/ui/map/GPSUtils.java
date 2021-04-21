package com.example.projetoiseaux.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import org.osmdroid.util.GeoPoint;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.example.projetoiseaux.ui.map.IGPSActivity.REQUEST_PERMISSION_GPS;

public class GPSUtils {
    private MapFragment mapFragment;
    private FragmentActivity fragmentActivity;
    private Location currentLocation;

    private Context context;


    public GPSUtils(Context context, FragmentActivity fragmentActivity, MapFragment mapFragment) {
        this.mapFragment = mapFragment;
        this.fragmentActivity = fragmentActivity;
        this.context = context;
        setListener();

        Address a = getCityLocation("Xi'An");
        if(a != null){
            Log.d("gps", "Exemple------->" +
                    "Address " + a.getLocality() +
                    " Location " + a.getLatitude() + " " +
                    a.getLongitude());
        }

    }

    private void setListener() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //设置一个 GPS 状态监听器
            LocationListener listener = new LocationListener() {
                @Override // 地址改变的时候，这个函数会被调用
                public void onLocationChanged(@NonNull Location location) {
                    currentLocation = location; // currentLocation会被改变
                    mapFragment.refocus();
                    // todo : Map Fragment move Camera
//                  igpsActivity.moveCamera();
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override // 要是有新gps时 // 这里检测gps是否开启/关闭
                public void onProviderEnabled(@NonNull String provider) {
                    // imageGpsActivited.setImageResource(R.drawable.unlocked);
                }

                @Override
                public void onProviderDisabled(@NonNull String provider) {
                    // todo : informer user that the GPS is not work
                    //
                    // imageGpsActivited.setImageResource(R.drawable.locked);
                }
            };

            //设置系统Gps管理器通知自己的监听器
            LocationManager locationManager = (LocationManager) (fragmentActivity.getSystemService(Context.LOCATION_SERVICE));
            //设置为系统每五秒 或者每一米，就会唤醒自己的listener
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, listener);

            if(currentLocation == null) {
                currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }

            Log.d("gps", "currentLocation --->" + currentLocation);


            // 用于检测GPS是否开启
            // imageGpsActivited.setImageResource( locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ? R.drawable.unlocked : R.drawable.locked);
        } else {
            Log.d("gps", "Request Permission in GPS.class," + fragmentActivity);
            Log.d("gps", "in GPSFragment --> " + fragmentActivity.getParent() +"\n Context->>>" + context);
            ActivityCompat.requestPermissions(fragmentActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_GPS);
        }



    }

    boolean havePermissionGPS() {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    void requestPermissionGPS() {
        if (!havePermissionGPS()) {
            Log.d("gps", "Request Permission in GPS.class," + fragmentActivity);
            Log.d("gps", "in MapFragment --> " + fragmentActivity.getParent() +"\n Context->>>" + context);
            ActivityCompat.requestPermissions(fragmentActivity.getParent(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_GPS);
        }
    }

    GeoPoint getCurrentPosition(){
        return new GeoPoint(currentLocation.getLatitude(), currentLocation.getLongitude());
    }

    Location getCurrentLocation() {
        return currentLocation;
    }

    String getCurrentPlaceName() throws IOException {
        //通过坐标返回一个地址Object（找到的其中一个/）， getString
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        //仅需要一个结果
        List<Address> addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
        return addresses.get(0).getLocality();
        /// TODO: 2021/4/21


    }

    Address getCityLocation(String cityName){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addressList = null;
        try {
            addressList = geocoder.getFromLocationName(cityName, 5);
            Log.d("gps","\n----------------\nget " + cityName + " Location --->" + addressList.toString());
        } catch (IOException e){
            Log.e("gps", "get Location Failed");
        }
        return addressList.get(0);
    }

    String getPlaceName() throws IOException {
        //通过坐标返回一个地址Object（找到的其中一个/）， getString
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        //仅需要一个结果
        List<Address> addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
        return addresses.get(0).getLocality();


    }



//    void setPlaceName(String placeName){
//        placeNameTextView.setText(placeName);
//    }
}