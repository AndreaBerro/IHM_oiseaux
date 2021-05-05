package com.example.projetoiseaux.ui.share.Client;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadIntentService extends IntentService {


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public UploadIntentService() {
        super("UploadIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        MediaType JSON = MediaType.parse("application/json;charset=utf-8");

        OkHttpClient okHttpClient = new OkHttpClient();
        URL url = null;
        String data = "";
        try {
            JSONObject js = new JSONObject(intent.getStringExtra("json"));
            url = new URL(intent.getStringExtra("url"));

            if (js != null) {
                data = js.getJSONArray("json").getString(0);
                Log.d("mylog", "data json " + data);
            }
        } catch (JSONException | MalformedURLException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(JSON, data);

        Request request = new Request.Builder()
                .addHeader("Authorization", "")
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG Upload Failed：", e.toString() + "");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.i("TAG Upload Succeed：", string + "");
            }
        });
    }
}
