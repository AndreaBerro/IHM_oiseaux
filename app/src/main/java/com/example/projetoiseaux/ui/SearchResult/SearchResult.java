package com.example.projetoiseaux.ui.SearchResult;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.projetoiseaux.R;
import com.example.projetoiseaux.ui.Bird;
import com.example.projetoiseaux.ui.search.IBridInfo;

public class SearchResult extends AppCompatActivity
        implements IListener, IBridInfo {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intentReceiver = getIntent();
        String name = intentReceiver.getStringExtra("name");
        String color = intentReceiver.getStringExtra("color");
        int size = intentReceiver.getIntExtra("size", 0);

        ListBird birds = new ListBird(name, color, size);

        BridAdapter adapter = new BridAdapter(getApplicationContext(), birds);
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