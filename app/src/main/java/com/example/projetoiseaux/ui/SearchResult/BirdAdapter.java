package com.example.projetoiseaux.ui.SearchResult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projetoiseaux.R;

public class BirdAdapter extends BaseAdapter {
    private IBirdListener listener;
    private ListBird birds;
    //Le contexte dans lequel est présent notre adapter
    private Context context;
    //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    private LayoutInflater mInflater;
    public BirdAdapter(Context context, ListBird birds) {
        this.context = context;
        this.birds = birds;
        mInflater = LayoutInflater.from(this.context);
    }

    public int getCount() { return birds.size(); }
    public Object getItem(int position) { return birds.get(position); }
    public long getItemId(int position) { return position; }

    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem;

        layoutItem = (LinearLayout) (convertView == null ? mInflater.inflate(R.layout.bird_layout, parent, false) : convertView);

        TextView birdName = layoutItem.findViewById(R.id.birdName);
        ImageView birdPicture = layoutItem.findViewById(R.id.birdPicture);

        birdName.setText(birds.get(position).getName());
        birdPicture.setImageResource(birds.get(position).getPicture());

        layoutItem.setOnClickListener( click -> {
            listener.onClickBird(birds.get(position));
        });

        return layoutItem;
    }

    public void addListener(IBirdListener listener) {
        this.listener = listener;
    }
}
