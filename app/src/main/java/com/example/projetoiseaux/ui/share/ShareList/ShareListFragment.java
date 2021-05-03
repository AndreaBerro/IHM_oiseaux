package com.example.projetoiseaux.ui.share.ShareList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projetoiseaux.MainActivity;
import com.example.projetoiseaux.R;
import com.example.projetoiseaux.ui.share.Client.Client;
import com.example.projetoiseaux.ui.share.Client.JsonUtil;
import com.example.projetoiseaux.ui.share.Share;
import com.example.projetoiseaux.ui.share.ShareFragment;
import com.example.projetoiseaux.ui.share.ShareList.ShareListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.projetoiseaux.ui.share.Client.JsonUtil.parseJsonWithJsonObject;

/**
 */
public class ShareListFragment extends Fragment {
    private ShareListAdapter shareListAdapter;
    private JSONArray shareData;
    private List<Share> listShare;

    public ShareListFragment() {}

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
                for(int i=0;i<shareData.length();i++){
                    try {
                        listShare.add(new Share(shareData.getJSONObject(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("mylog", " before refresh " + listShare.toString());

                // 应该换进主线程中， 否则会报错，也无法更改
                ((MainActivity) getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        shareListAdapter.refresh(listShare);
                        shareListAdapter.notifyDataSetChanged();
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