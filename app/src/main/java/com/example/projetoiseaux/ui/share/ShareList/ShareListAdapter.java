package com.example.projetoiseaux.ui.share.ShareList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetoiseaux.MainActivity;
import com.example.projetoiseaux.R;
import com.example.projetoiseaux.ui.share.Client.Client;
import com.example.projetoiseaux.ui.share.Client.JsonUtil;
import com.example.projetoiseaux.ui.share.Share;


import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.projetoiseaux.ui.share.Client.Client.getImageRes;

public class ShareListAdapter extends BaseAdapter {
    private List<Share> mData;
    private final Context mContext;

    public ShareListAdapter(Context mContext, List<Share> mData) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            //创建组件页面(定义的一个网格的组件)
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_share_item
                    , parent
                    , false);
            holder.imageView = (ImageView) convertView.findViewById(R.id.share_item_picture);
            holder.title = (TextView) convertView.findViewById(R.id.share_item_titre);
            holder.description = (TextView) convertView.findViewById(R.id.share_item_desc);



            //将这一个网格的信息对象设置进去
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Log.d("mylog", "In share List Adapter: on get View");

        holder.imageView.setImageResource(R.drawable.ic_image_alt);
        holder.title.setText(mData.get(position).getUserName());
        holder.description.setText(mData.get(position).getDesc());
        if(mData.get(position).getPictureName().size() > 0) {
            getOnlingBitMap(mData.get(position).getPictureName().get(0), holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_image_alt);
        }

        return convertView;
    }

    private void getOnlingBitMap(String imageName, ImageView imageView) {
        getImageRes(imageName, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("mylog", "Get Data Failed");
            }
            @Override
            public void onResponse(Call call, Response response) {
                Log.d("mylog", " get image reponse ");
                try {
                    byte[] Picture_bt = response.body().bytes();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(Picture_bt, 0, Picture_bt.length);
                    ((MainActivity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                            //此时已在主线程中，可以更新UI了
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();

                }

                // 应该换进主线程中， 否则会报错，也无法更改

                Log.d("mylog", " after");
            }
        });
    }

    public void refresh(List<Share> listShare){
        Log.d("mylog", "refresh");
        this.mData = listShare;
        Collections.reverse(this.mData);
    }

    //记录这个网格的信息
    static class ViewHolder {
        private ImageView imageView;
        private TextView title;
        private TextView description;
    }
}
