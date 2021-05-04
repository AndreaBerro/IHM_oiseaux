package com.example.projetoiseaux.ui.share.Client;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

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
     *
     */
    public UploadIntentService() {
        super("uploadIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        MediaType JSON = MediaType.parse("application/json;charset=utf-8");

        //申明给服务端传递一个json串
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        //json为String类型的json数据
        String data = "";
        try {
            JSONObject js = new JSONObject( intent.getStringExtra("json") );
            data = js.getJSONArray("json").getString(0);
            Log.d("mylog", "data json " + data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(JSON, data);
        //创建一个请求对象
        Request request = new Request.Builder()
                .addHeader("Authorization", "")//身份验证的Token
                .url("http://192.168.56.1:9428/api/share")//上传接口
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
