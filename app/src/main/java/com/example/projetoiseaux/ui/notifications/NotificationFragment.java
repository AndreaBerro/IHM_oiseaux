package com.example.projetoiseaux.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.projetoiseaux.MainActivity;
import com.example.projetoiseaux.PermissionState.StateUtils;
import com.example.projetoiseaux.R;
import com.example.projetoiseaux.ui.share.Client.Client;
import com.example.projetoiseaux.ui.share.Client.JsonUtil;
import com.example.projetoiseaux.ui.share.NewShare.ShareFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NotificationFragment extends Fragment {
    private NotificationAdapter notificationAdapter;
    private JSONArray notificationData;
    private List<Notification> listNotification;


    public NotificationFragment() {}

    @Override
    public void onStart() {
        super.onStart();
        flushData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification_list, container, false);

        if(!StateUtils.checkInternetState(getContext())){
            Toast.makeText(getContext(), "No internet connection", Toast.LENGTH_LONG).show();
        }

        notificationData = new JSONArray();
        listNotification = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(getContext(), listNotification);
        ListView listView = rootView.findViewById(R.id.notificationList);
        initFlush(rootView);
        listView.setAdapter(notificationAdapter);
        flushData();


        return rootView;
    }

    private void initFlush(View rootView) {
        SwipeRefreshLayout refreshLayout = rootView.findViewById(R.id.refreshLayout);

        refreshLayout.setOnRefreshListener(() -> {
            flushData();
            refreshLayout.setScrollBarFadeDuration(50);
            refreshLayout.setRefreshing(false);
        });
    }

    private void flushData(){
        Log.d("mylog", "flush data");
        if(!StateUtils.checkInternetState(getContext())){
            Toast.makeText(getContext(), "No internet connection", Toast.LENGTH_SHORT).show();
        }
        Client.getNotificationInfo(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("mylog", "Get Notification Data Failed");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                notificationData = JsonUtil.parseJsonWithJsonObject(response);
                listNotification = new ArrayList<>();

                for(int i = 0; i< notificationData.length(); i++){
                    try {
                        Notification notification = new Notification(notificationData.getJSONObject(i));
                        listNotification.add(notification);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if(listNotification.size() > 0) {
                    ((MainActivity) getContext()).runOnUiThread(() -> {
                        notificationAdapter.refresh(listNotification);
                        notificationAdapter.notifyDataSetChanged();
                    });
                }
                Log.d("mylog", " after refresh " + notificationData.toString());
            }
        });
    }
}
