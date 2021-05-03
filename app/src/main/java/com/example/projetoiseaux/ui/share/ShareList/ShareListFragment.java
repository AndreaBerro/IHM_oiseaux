package com.example.projetoiseaux.ui.share.ShareList;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.projetoiseaux.MainActivity;
import com.example.projetoiseaux.R;
import com.example.projetoiseaux.ui.IUploadActivity;
import com.example.projetoiseaux.ui.UploadBird;
import com.example.projetoiseaux.ui.share.Client.Client;
import com.example.projetoiseaux.ui.share.Client.JsonUtil;
import com.example.projetoiseaux.ui.share.Share;
import com.example.projetoiseaux.ui.share.NewShare.ShareFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 */
public class ShareListFragment extends Fragment {
    private ShareListAdapter shareListAdapter;
    private JSONArray shareData;
    private List<Share> listShare;
    private List<UploadBird> listUpload;

    private IUploadActivity mainActivity;

    public ShareListFragment() {}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            mainActivity = (IUploadActivity) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement IShareActivity");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_share_list, container, false);

        shareData = new JSONArray();
        listShare = new ArrayList<Share>();
        shareListAdapter = new ShareListAdapter(getContext(), listShare);
        ListView listView = rootView.findViewById(R.id.shareList);
        listView.setAdapter(shareListAdapter);
        initFab(rootView);
        initFlush(rootView);
        flushData();

        return rootView;
    }

    private void initFlush(View rootView) {
        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.refreshLayout);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("mylog", "on Refresh");
                flushData();
                refreshLayout.setScrollBarFadeDuration(50);
                refreshLayout.setRefreshing(false);
            }
        });
    }

    public static Date toDate(String date){
        String format = "yyyy-MM-dd-hh-mm";
        DateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void flushData(){
        Client.getShareInfo(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("mylog", "Get Data Failed");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                shareData = JsonUtil.parseJsonWithJsonObject(response);
                listShare = new ArrayList<Share>();
                listUpload = new ArrayList<UploadBird>();

                for(int i=0;i<shareData.length();i++){
                    try {
                        Share share = new Share(shareData.getJSONObject(i));
                        listShare.add(share);
                        listUpload.add(new UploadBird(share.getDesc(),
                                share.getLatitude(),
                                share.getLongitude(),
                                toDate(share.getDate()),
                                share.getPictureName()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("mylog", " before refresh " + listUpload.get(0).getDate().toString());

                // 应该换进主线程中， 否则会报错，也无法更改
                ((MainActivity) getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        shareListAdapter.refresh(listShare);
                        shareListAdapter.notifyDataSetChanged();
                        mainActivity.setUploadList(listUpload);
                        Log.d("mylog", "In shareList--->" + listUpload.get(0));
                        // todo :  Add share point to A list UploadBird and give it to Main
                        //此时已在主线程中，可以更新UI了
                    }
                });


                Log.d("mylog", " after refresh " + shareData.toString());
            }
        });
    }

    private void initFab(View rootView) {
        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.nav_host_fragment, new ShareFragment(),"NewShare");
                fragmentTransaction.commit();
            }
        });
    }


}