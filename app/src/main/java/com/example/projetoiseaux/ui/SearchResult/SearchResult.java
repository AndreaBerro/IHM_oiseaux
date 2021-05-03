package com.example.projetoiseaux.ui.SearchResult;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.projetoiseaux.R;
import com.example.projetoiseaux.ui.Bird;
import com.example.projetoiseaux.ui.home.IBridInfo;

public class SearchResult extends AppCompatActivity
        implements IListener, IBridInfo {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intentReciver = getIntent();
        String inputInfo = intentReciver.getStringExtra(BRID_INFO);
        Log.d("mylog", inputInfo);

        ListBird birds = new ListBird();

        BirdsAdapter adapter = new BirdsAdapter(getApplicationContext(), birds);
        ListView listView = findViewById(R.id.listView);

        listView.setAdapter(adapter);
        adapter.addListener(this);
    }

    @Override
    public void onClickBird(Bird item) {
        Log.d("mylog", item.getName());
//        Intent intent = new Intent( getApplicationContext(), PizzaActivity.class);
//        intent.putExtra(PIZZA, (Parcelable)item);
//        startActivity(intent);
    }
}