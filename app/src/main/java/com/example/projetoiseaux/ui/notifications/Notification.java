package com.example.projetoiseaux.ui.notifications;

import android.util.Log;

import com.example.projetoiseaux.ui.share.Client.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Notification {

    private String title;
    private String description;
    private int type;
    private String date;
    private String time;

    public Notification(String title, String description, int type, String date, String time) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.date = date;
        this.time = time;
    }

    public Notification(JSONObject jsonObject) throws JSONException {
        this.title = jsonObject.getString("title");
        this.description = jsonObject.getString("description");
        this.type = jsonObject.getInt("type");
        this.date = jsonObject.getString("date");
        this.time = jsonObject.getString("time");
    }

    public JSONObject toJSONObject() {
        String json = "{'json':[{" +
                "'title': '" + title + "'," +
                "'description': '" + description + "'," +
                "'type': " + type + ',' +
                "'date': '" + date + "'," +
                "'time': '" + time + "'" +
                "}]}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            Log.d("mylog", "Create JsonObject : " + jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}

