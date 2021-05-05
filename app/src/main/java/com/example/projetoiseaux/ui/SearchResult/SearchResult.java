package com.example.projetoiseaux.ui.SearchResult;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.projetoiseaux.MainActivity;
import com.example.projetoiseaux.R;
import com.example.projetoiseaux.ui.Bird;
import com.example.projetoiseaux.ui.search.IBridInfo;

public class SearchResult extends AppCompatActivity
        implements IBirdListener, IBridInfo {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intentReceiver = getIntent();
        String name = intentReceiver.getStringExtra("name");
        String color = intentReceiver.getStringExtra("color");
        int size = intentReceiver.getIntExtra("size", 0);

        ListBird birds = new ListBird(name, color, size);

        BirdAdapter adapter = new BirdAdapter(getApplicationContext(), birds);
        ListView listView = findViewById(R.id.listView);

        listView.setAdapter(adapter);
        adapter.addListener(this);
    }

    @Override
    public void onClickBird(Bird item) {
        ImageView image = new ImageView(this);
        image.setImageResource(item.getPicture());
        image.setAdjustViewBounds(true);
        image.setMaxWidth(100);
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle(item.getName());
        builder.setView(image);
        builder.setMessage(item.getDescription());
        builder.setNeutralButton("Back",null);
        builder.setNegativeButton("View on map", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = item.getName();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
        builder.show();
    }
}