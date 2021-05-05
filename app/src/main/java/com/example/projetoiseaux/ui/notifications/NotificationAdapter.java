package com.example.projetoiseaux.ui.notifications;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.projetoiseaux.R;

import java.util.Collections;
import java.util.List;


public class NotificationAdapter extends BaseAdapter {
    private List<Notification> mData;
    private final Context mContext;

    public NotificationAdapter(Context mContext, List<Notification> mData) {
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
        NotificationAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new NotificationAdapter.ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_notification_item
                    , parent
                    , false);
            holder.title = convertView.findViewById(R.id.notification_title);
            holder.description = convertView.findViewById(R.id.notification_description);
            holder.date = convertView.findViewById(R.id.notification_date);
            holder.time = convertView.findViewById(R.id.notification_time);

//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                }
//            });

            convertView.setTag(holder);
        } else {
            holder = (NotificationAdapter.ViewHolder) convertView.getTag();
        }


        convertView.setBackgroundColor(Color.parseColor(mData.get(position).getType() == 0 ? "yellow" : "magenta"));
        holder.title.setText(mData.get(position).getTitle());
        holder.description.setText(mData.get(position).getDescription());
        holder.date.setText(mData.get(position).getDate());
        holder.time.setText(mData.get(position).getTime());
        return convertView;
    }

    public void refresh(List<Notification> listShare){
        Log.d("mylog", "refresh");
        this.mData = listShare;
        Collections.reverse(this.mData);
    }

    static class ViewHolder {
        private TextView title;
        private TextView description;
        private TextView date;
        private TextView time;
    }
}
