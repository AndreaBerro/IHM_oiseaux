package com.example.projetoiseaux.ui.map;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.projetoiseaux.MainActivity;
import com.example.projetoiseaux.R;
import com.example.projetoiseaux.Bird.Bird;
import com.example.projetoiseaux.ui.SearchResult.ListBird;
import com.example.projetoiseaux.Bird.UploadBird;
import com.example.projetoiseaux.ui.share.Client.Client;

import org.osmdroid.util.GeoPoint;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.example.projetoiseaux.ui.map.IGPSActivity.REQUEST_PERMISSION_GPS;

public class GPSUtils {
    private MapFragment mapFragment;
    private FragmentActivity fragmentActivity;
    private Location currentLocation;

    private Context context;
    private boolean changed = false;


    public GPSUtils(Context context, FragmentActivity fragmentActivity, MapFragment mapFragment) {
        this.mapFragment = mapFragment;
        this.fragmentActivity = fragmentActivity;
        this.context = context;
        setListener();
    }

    private void setListener() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //设置一个 GPS 状态监听器
            LocationListener listener = new LocationListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override // 地址改变的时候，这个函数会被调用
                public void onLocationChanged(@NonNull Location location) {
                    currentLocation = location; // currentLocation会被改变
                    mapFragment.refocus();
                    // todo : Map Fragment move Camera
//                  igpsActivity.moveCamera();

                    List<UploadBird> birdList = mapFragment.getListNearBird();
                    ArrayList<String> birdNames = new ArrayList<>();
                    String nearestBird = "";
                    double minDistance = 1000;
                    for(UploadBird bird : birdList){
                        double distance = distanceGeoPoints(getCurrentPosition(), bird.getGeoPoint());
                        if(distance < 1000){
                            if(context != null) {
                                birdNames.add(bird.getName());
                                changed = changed || (birdNames.contains(bird.getName()));

                                if(distance <= minDistance){
                                    minDistance = distance;
                                    nearestBird = bird.getName();
                                }
                            }
                        }
                    }
                    if(!nearestBird.equals("") && minDistance < 100){
                        Bird bird = new ListBird(new ArrayList<>(Collections.singletonList(nearestBird))).get(0);
                        nearBirdCheck(bird);
                    }
                    if (birdNames.size() > 0 && changed){
                        ListBird birds = new ListBird(birdNames);
                        nearBirdsNotification(birds);
                        }


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
        if(currentLocation == null) return null;
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

    String getPlaceName(int latitude, int longitude){
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1); // max result == 1 //仅需要一个结果
            return addresses.get(0).getLocality();
        } catch (IOException e){
            return null;
        }
    }

    public double distanceGeoPoints (GeoPoint geoPoint01, GeoPoint geoPoint02) {
        double lat1 = geoPoint01.getLatitude();
        double lng1 = geoPoint01.getLongitude();
        double lat2 = geoPoint02.getLatitude();
        double lng2 = geoPoint02.getLongitude();
        double earthRadius = 3958.75;
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;

        int meterConversion = 1609;
        double distance = dist * meterConversion;

        System.out.println(distance);
        return distance;
    }

    private RemoteViews setNotificationSliderData(ArrayList<Bird> birdList){
        RemoteViews expandedView = new RemoteViews(context.getPackageName(), R.layout.notification_slider_layout);

        for(Bird bird : birdList){
            RemoteViews viewFlipperImage = new RemoteViews(context.getPackageName(), R.layout.notification_slider_component);
            Bitmap remotePicture = BitmapFactory.decodeResource(context.getResources(), bird.getPicture());
            viewFlipperImage.setImageViewBitmap(R.id.imageView, remotePicture);
            viewFlipperImage.setTextViewText(R.id.textViewContent, bird.getName());

            expandedView.addView(R.id.viewFlipper, viewFlipperImage);
        }

        return expandedView;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void nearBirdsNotification(ArrayList<Bird> birds) {

        int size = birds.size();
        int index = 0;
        if (size > 0) {
            RemoteViews flipper = setNotificationSliderData(birds);
            Bitmap artwork = BitmapFactory.decodeResource(context.getResources(), birds.get(index).getPicture());

            Notification notification = new Notification.Builder(context, MainActivity.MEDIUM_CHANNEL)
                    .setSmallIcon(R.drawable.bird_icon)
                    .setShowWhen(true)
                    .setContentTitle("Bird proximity")
                    .setContentText("There seems to be " + size + " bird" + (size > 1 ? "s" : "")
                            + " near your position :")
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setStyle(new Notification.DecoratedCustomViewStyle())
                    .setCustomBigContentView(flipper)
                    .setLargeIcon(artwork)
                    .build();

            NotificationManagerCompat.from(context).notify(3, notification);

            StringBuilder encounterString = new StringBuilder("Bird Encounter (" + size + " around) :\n");
            for(Bird bird : birds) encounterString.append("- ").append(bird.getName()).append("\n");

            com.example.projetoiseaux.ui.notifications.Notification notificationObject =
                    new com.example.projetoiseaux.ui.notifications.Notification(
                            "Bird proximity",
                            encounterString.toString(),
                            1,
                            LocalDate.now().toString(),
                            new SimpleDateFormat("HH:mm:ss", Locale.FRENCH).format(new Date()));
            Client client = new Client();
            client.startNetThread();
            try {
                URL url = new URL("http://192.168.56.1:9428/api/notifications");
                Client.uploadingCallRecords(notificationObject.toJSONObject(), url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            changed = false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void nearBirdCheck(Bird bird){
        Notification notification = new Notification.Builder(context, MainActivity.MEDIUM_CHANNEL)
                .setSmallIcon(R.drawable.bird_icon)
                .setShowWhen(true)
                .setContentTitle("Bird in sight")
                .setContentText("Have you seen a " + bird.getName() + " around you ?")
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        NotificationManagerCompat.from(context).notify(4, notification);

        com.example.projetoiseaux.ui.notifications.Notification notificationObject =
                new com.example.projetoiseaux.ui.notifications.Notification(
                        "Bird in sight",
                        "Bird Seen : " + bird.getName(),
                        0,
                        LocalDate.now().toString(),
                        new SimpleDateFormat("HH:mm:ss", Locale.FRENCH).format(new Date()));
        Client client = new Client();
        client.startNetThread();
        try {
            URL url = new URL("http://192.168.56.1:9428/api/notifications");
            Client.uploadingCallRecords(notificationObject.toJSONObject(), url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}