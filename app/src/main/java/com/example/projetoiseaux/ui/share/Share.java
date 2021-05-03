package com.example.projetoiseaux.ui.share;

import android.util.Log;

import com.example.projetoiseaux.ui.share.Client.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Share {
    private final String userName;
    private final String date;
    private final String desc;
    private final double latitude;
    private final double longitude;
    private final List<String> pictureName;

    public Share(String userName, String date, String desc, double latitude, double longitude, List<String> pictureName){
        this.userName = userName;
        this.date = date;
        this.desc = desc;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pictureName = pictureName;
    }

    // todo date not data...
    public Share(JSONObject jsonObject) throws JSONException {
        this.userName = jsonObject.getString("UserName");
        this.date = jsonObject.getString("data");
        this.desc = jsonObject.getString("description");
        this.latitude = jsonObject.getDouble("latitude");
        this.longitude = jsonObject.getDouble("longitude");

        JSONArray pictures = jsonObject.getJSONArray("imageId");
        pictureName = new ArrayList<String>();
        for (int i = 0; i < pictures.length(); i++) { //提取出family中的所有
            System.out.println("currentFamily:" + (String) pictures.get(i));
            pictureName.add((String) pictures.get(i));
        }
    }

    public JSONObject getJsonObj(){
        return JsonUtil.toJsonObj(userName, date, desc, latitude, longitude, pictureName);
    }

    public String getUserName() {
        return userName;
    }

    public String getDesc() {
        return desc;
    }

    public String getDate() {
        return date;
    }

    public List<String> getPictureName() {
        return pictureName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
