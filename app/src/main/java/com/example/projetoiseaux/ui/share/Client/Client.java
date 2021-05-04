package com.example.projetoiseaux.ui.share.Client;

import android.accounts.NetworkErrorException;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.NetworkOnMainThreadException;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Client {
    private final int HANDLER_MSG_TELL_RECV = 0x124;
    public static final int SUCCESS = 1;

    @SuppressLint("HandlerLeak")
    Handler handler01 = new Handler(){
        public void handleMessage(Message msg){
            //接受到服务器信息时执行
            Log.d("Client", "reciveMsg from server" + msg.getData());
        }
    };

    public Client(){}

    public void startNetThread() {
        new Thread() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("192.168.56.1", 6667);
                    InputStream is = socket.getInputStream();
                    byte[] bytes = new byte[1024];
                    int n =is.read(bytes);
                    while(true) {
                        Message msg = handler01.obtainMessage(HANDLER_MSG_TELL_RECV, new String(bytes, 0, n));
                        msg.sendToTarget();
                        n =is.read(bytes);
                    }
                } catch (Exception e) {
                }
            }
        }.start();
    }

    public static void getShareInfo(Callback callback)
    {
        OkHttpClient client = new OkHttpClient();
        Request request  =new Request.Builder().url("http://192.168.56.1:9428/api/share").build();
        client.newCall(request).enqueue(callback);
    }

    public static void getImageRes(String imageName, Callback callback){
        //1.创建一个okhttpclient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.创建Request.Builder对象，设置参数，请求方式如果是Get，就不用设置，默认就是Get
        URL path = null;
        try {
            path = new URL("http://192.168.56.1:9428/api/share/upload/" + imageName);
            Log.d("mylog", path.getPath());
        } catch (MalformedURLException e){
            e.printStackTrace();
            Log.e("mylog", "URL not work");
            return;
        }
        Request request = new Request.Builder()
                .url(path)
                .build();
        //3.创建一个Call对象，参数是request对象，发送请求
        Call call = okHttpClient.newCall(request);
        //4.异步请求，请求加入调度
        call.enqueue(callback);
    }


    //1.创建对应的MediaType
    private static final MediaType MEDIA_TYPE_JPEG = MediaType.parse("image/jpeg");
    private static String mImageType = "multipart/form-data";
    private static final OkHttpClient client = new OkHttpClient();

    public static void uploadImage(String userName, File file){
        new Thread() {
            @Override
            public void run() {
                try {
                    //2.创建RequestBody
                    RequestBody fileBody = RequestBody.create(MEDIA_TYPE_JPEG, file);
                    //RequestBody tagBody = RequestBody.create(null, "tag");

                    //3.构建MultipartBody
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("file", file.getName(), fileBody)
                            // .addFormDataPart("userTag", userName, tagBody)
//                            .addFormDataPart("testKey", userName)  // 这种方式不行， 使用上一行，null传递 // 上一行会生成不必要的文件。。。
                            .build();
                    //4.构建请求
                    Request request = new Request.Builder()
                            .url("http://192.168.56.1:9428/api/share/image")
                            .post(requestBody)
                            .build();
                    Log.d("mylog", "Begin to upload image" + file.getName());
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        Log.i("TAG Upload Succeed：", string + "");
                    } else {
                        Log.e("TAG Upload Failed：", response.message());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("TAG Upload Failed：", "");
                }
            }
        }.start();
    }
}
