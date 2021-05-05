package com.example.projetoiseaux.ui.share.NewShare.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetoiseaux.PermissionState.StateUtils;
import com.example.projetoiseaux.R;
import com.example.projetoiseaux.ui.map.GPSUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.projetoiseaux.ui.map.IGPSActivity.REQUEST_PERMISSION_GPS;


public class SelectLocation extends AppCompatActivity {
    List<Address> addresses = new ArrayList<Address>();
    List<String> stringList = new ArrayList<String>();
    Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);

        /**
         *  Todo :
         *  input textView
         *  confirm button
         *  list Adapter
         */

        //创建ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_expandable_list_item_1, stringList){
        };

        try {
            Log.d("mylog", "set current loc");
            adapter.add("Your Current Place:      " + getCurrentPlaceName());
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Get current location Failed", Toast.LENGTH_SHORT).show();
        }

        //获取ListView对象，通过调用setAdapter方法为ListView设置Adapter设置适配器
        ListView list_test = (ListView) findViewById(R.id.location_list);
        list_test.setAdapter(adapter);

        list_test.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0,View arg1,int arg2,long arg3){
                // Toast.makeText(getApplicationContext(), arg2 + " " + arg3, Toast.LENGTH_SHORT).show();
                Intent data = new Intent();

                if(arg2 == 0 && addresses.size() == 0){
                    data.putExtra(ILocation.LATITUDE, currentLocation.getLatitude());
                    data.putExtra(ILocation.LONGITUDE, currentLocation.getLongitude());
                } else {
                    Log.d("mylog", addresses.get(arg2).getLongitude() + " " + addresses.get(arg2).getLatitude());
                    data.putExtra(ILocation.LATITUDE, addresses.get(arg2).getLatitude());
                    data.putExtra(ILocation.LONGITUDE, addresses.get(arg2).getLongitude());
                }
                setResult(RESULT_OK, data);
                finish();
                //arg0表示点击发生的所在的AdapterView，arg1是在AdapterView中被点击的view，arg2表示adapter中view的位置（position），arg3表示被点击的item的行id
            }
        });


        ((EditText)findViewById(R.id.input_location)).setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String cityName = ((EditText)findViewById(R.id.input_location)).getText().toString();
                addresses = getCityLocation(cityName);

                if(addresses != null) {
                    stringList = addrs2str(addresses);
                    adapter.clear();
                    // get current location

                    adapter.addAll(stringList);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), cityName, Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        Activity activity = this;
        findViewById(R.id.search_location_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StateUtils.checkGpsState(activity))
                    Toast.makeText(getApplicationContext(), "GPS IS OFF", Toast.LENGTH_SHORT).show();
                String cityName = ((EditText)findViewById(R.id.input_location)).getText().toString();
                addresses = getCityLocation(cityName);
                stringList = addrs2str(addresses);
                adapter.clear();
                adapter.addAll(stringList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    String getCurrentPlaceName() throws Exception {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) (getSystemService(Context.LOCATION_SERVICE));

            currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Log.d("gps", "currentLocation --->" + currentLocation);

            // 用于检测GPS是否开启
            if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                Toast.makeText(getApplicationContext(), "GPS IS OFF", Toast.LENGTH_SHORT).show();
        }

        //通过坐标返回一个地址Object（找到的其中一个/）， getString
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        //仅需要一个结果
        List<Address> addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
        return addresses.get(0).getLocality();
    }

    List<String> addrs2str(List<Address> addresses){
        List<String> cityDesc = new ArrayList<String>();
        if (addresses == null){
            return cityDesc;
        }
        for (Address address : addresses){
            Log.d("gps", address.getAddressLine(0)+ "/// "+ address.getCountryName()+ "/// "+address.getFeatureName());
            cityDesc.add(address.getAddressLine(0) + " " + address.getSubAdminArea() + address.getFeatureName());
        }
        Log.d("gps", cityDesc.toString() + "abdhjwbia!!!!!");
        return cityDesc;
    }


    List<Address> getCityLocation(String cityName){
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addressList = null;
        try {
            addressList = geocoder.getFromLocationName(cityName, 5);
            Log.d("gps","\n----------------\nget " + cityName + " Location --->" + addressList.toString());
        } catch (IOException e){
            Log.e("gps", "get Location Failed");
        }
        return addressList;
    }
}