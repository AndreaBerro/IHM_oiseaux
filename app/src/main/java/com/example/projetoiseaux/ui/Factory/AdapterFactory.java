package com.example.projetoiseaux.ui.Factory;

import android.content.Context;
import android.util.Log;
import android.widget.Adapter;


import com.example.projetoiseaux.ui.searchTool.AutoCompleteBirdAdapter;
import com.example.projetoiseaux.ui.searchTool.ColorAdapter;
import com.example.projetoiseaux.ui.share.NewShare.GridPictures.GridViewAdapter;
import com.example.projetoiseaux.ui.share.Share;
import com.example.projetoiseaux.ui.share.ShareList.ShareListAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdapterFactory {
    public static final int LIST_ADAPTER = 1;
    public static final int GRID_ADAPTER = 2;
    public static final int COLOR_ADAPTER = 3;
    public static final int AUTO_COMPLETE_ADAPTER = 4;


    public Adapter createAdapter(int type, Context context, List list) throws Throwable {
        Log.d("mylog", "Factory working");
        switch (type) {
            case LIST_ADAPTER:
                return new ShareListAdapter(context, list);
            case GRID_ADAPTER:
                return new GridViewAdapter(context, list);
            case COLOR_ADAPTER:
                return new ColorAdapter(context, (ArrayList<String>) list);
            case AUTO_COMPLETE_ADAPTER:
                return new AutoCompleteBirdAdapter(context, list);
            default: throw new Throwable();
        }
    }
}
