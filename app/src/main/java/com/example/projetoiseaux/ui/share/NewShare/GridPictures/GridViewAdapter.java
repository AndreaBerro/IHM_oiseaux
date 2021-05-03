package com.example.projetoiseaux.ui.share.NewShare.GridPictures;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projetoiseaux.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.projetoiseaux.ui.share.NewShare.Camera.PictureFileUtils.getPictureBitmap;
import static com.example.projetoiseaux.ui.share.NewShare.Camera.PictureFileUtils.getPictureFile;
import static com.example.projetoiseaux.ui.share.NewShare.GridPictures.ListSharePicture.BUTTON_ADD;


//自定义的网格适配器
public class GridViewAdapter extends BaseAdapter {
    //数据
    private List<Picture> mData;
    private Context mContext;

    public GridViewAdapter(Context mContext, List<Picture> mData) {
        this.mData = mData;
        this.mContext = mContext;
    }

    //组件数量
    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }
    //获取一个网格的组件
    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mData.get(position).getId();
    }

    //创建每一个网格组件的布局
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            //创建组件页面(定义的一个网格的组件,租,)
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_grid_icon
                    , parent
                    , false);
            holder.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
            //将这一个网格的信息对象设置进去
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // todo 这里根据图像路径获取图像bitmap， 点击图像时，打开大图（根据路径加载原图）
        // holder.img_icon.setImageBitmap();
//        holder.img_icon.setImageResource(mData.get(position).getPicture());

        if (mData.get(position).getId() != BUTTON_ADD){
            Bitmap bitmap = getPictureBitmap(mData.get(position).getPath());
            if(bitmap != null) {
                holder.img_icon.setImageBitmap(bitmap);
                holder.img_icon.setRotation(90);
            } else {
                holder.img_icon.setImageResource(R.drawable.ic_image_alt);
            }
        } else {
            holder.img_icon.setImageResource(R.drawable.ic_plus_circle_fill);
        }
        return convertView;
    }

    public void deletePicture(int position){
        if(position != getCount()-1) {
            AlertDialog.Builder  builder = new AlertDialog.Builder(mContext);
            AlertDialog alert = builder
                    .setTitle("Delete")
                    .setMessage("Do you want to Delete this picture?")
                    .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(mContext, "Cancle", Toast.LENGTH_SHORT);
                        }
                    })
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mData.remove(position);
                            notifyDataSetChanged();
                        }
                    }).create();
            alert.show();
        }
    }

    /**
     * Add the picture path to the List of picture and refresh
     * @param absolutePath Picture Path in the mobile
     */
    public void addPicture(String absolutePath){
        mData.add(new Picture(absolutePath));
        notifyDataSetChanged();
    }

    public void addListPicture(List<String> absolutePaths){
        for (String absolutePath : absolutePaths){
            addPicture(absolutePath);
        }
    }

    int getPictureNum(){
        return mData.size() - 1;
    }

    public List<File> getListPictureFile(){
        List<File> pictureList = new ArrayList<File>();
        for(Picture picture: mData){
            if(picture.getId() != BUTTON_ADD){
                pictureList.add(getPictureFile(picture.getPath()));
            }
        }
        return pictureList;
    }

    public List<String> getListPictureName(){
        List<String> stringList = new ArrayList<String>();
        List<File> listPicture = getListPictureFile();
        for (File picture: listPicture){
            stringList.add(picture.getName());
        }
        return stringList;
    }


    //记录这个网格的信息
    static class ViewHolder {
        private ImageView img_icon;
    }

}