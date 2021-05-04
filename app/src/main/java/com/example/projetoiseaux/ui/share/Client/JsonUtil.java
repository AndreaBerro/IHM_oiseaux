package com.example.projetoiseaux.ui.share.Client;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Response;

public class JsonUtil {

    public static JSONObject toJsonObject(String key, String str){
        String json = "{'json':[{\"" + key + "\": \""  + str + "\"}]}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("String转JSONArray: "+jsonObject);
        return jsonObject;
    }

    public static JSONObject getShareJsonObj(String userName, String data, String description, double latitude, double longitude, List<String> images) {
        // todo : user = getDevice Id
        String json2 ="{'json':[{" +
                "'UserName': \"" + userName + "\"," +
                "'data': \"" + data + "\"," +
                "'latitude': " + latitude + ',' +
                "'longitude': " + longitude + ',' +
                "'imageId': " + images + ',' +
                "'description': \"" + description +
                "\"}]}";
        JSONObject jsonObject = null;
        try {
            Log.d("mylog", "Create JsonObject : " + json2);
            jsonObject = new JSONObject(json2);
            jsonObject.put("Images", images); // 传输时只有get（0）。。。
            Log.d("mylog", "Create JsonObject : " + jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONArray parseJsonWithJsonObject(Response response) throws IOException {
        String responseData = response.body().string();
        JSONArray jsonArray = null;
        try{
            jsonArray = new JSONArray(responseData);
            Log.d("mylog", jsonArray.toString());
            return jsonArray;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
}
