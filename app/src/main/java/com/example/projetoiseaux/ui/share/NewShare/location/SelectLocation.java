package com.example.projetoiseaux.ui.share.NewShare.location;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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

import com.example.projetoiseaux.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SelectLocation extends AppCompatActivity {
    List<Address> addresses = new ArrayList<Address>();
    List<String> stringList = new ArrayList<String>();

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
        //获取ListView对象，通过调用setAdapter方法为ListView设置Adapter设置适配器
        ListView list_test = (ListView) findViewById(R.id.location_list);
        list_test.setAdapter(adapter);

        list_test.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0,View arg1,int arg2,long arg3){
                Toast.makeText(getApplicationContext(), arg2 + " " + arg3, Toast.LENGTH_SHORT).show();
                Intent data = new Intent();
                Log.d("mylog", addresses.get(arg2).getLongitude() + " " + addresses.get(arg2).getLatitude());
                data.putExtra(ILocation.LATITUDE, addresses.get(arg2).getLatitude());
                data.putExtra(ILocation.LONGITUDE, addresses.get(arg2).getLongitude());
                setResult(RESULT_OK, data);
                finish();
                //arg0表示点击发生的所在的AdapterView，arg1是在AdapterView中被点击的view，arg2表示adapter中view的位置（position），arg3表示被点击的item的行id
                // TODO: 2021/4/29 set location when user select a place 
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
                    adapter.addAll(stringList);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), cityName, Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        findViewById(R.id.search_location_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = ((EditText)findViewById(R.id.input_location)).getText().toString();
                addresses = getCityLocation(cityName);
                stringList = addrs2str(addresses);
                adapter.clear();
                adapter.addAll(stringList);
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), cityName, Toast.LENGTH_SHORT).show();
            }
        });
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