package com.example.projetoiseaux.Bird;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projetoiseaux.R;
import com.example.projetoiseaux.ui.SearchResult.IListener;
import com.example.projetoiseaux.ui.SearchResult.ListBird;

public class BridAdapter extends BaseAdapter {
    private IListener listener;
    private ListBird birds;
    //Le contexte dans lequel est présent notre adapter
    private Context context;
    //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    private LayoutInflater mInflater;
    public BridAdapter(Context context, ListBird birds) {
        this.context = context;
        this.birds = birds;
        mInflater = LayoutInflater.from(this.context);
    }

    public int getCount() { return birds.size(); }
    public Object getItem(int position) { return birds.get(position); }
    public long getItemId(int position) { return position; }

    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutItem;

        //(1) : Réutilisation des layouts
        layoutItem = (LinearLayout) (convertView == null ? mInflater.inflate(R.layout.bird_layout, parent, false) : convertView);


        //(2) : Récupération des TextView de notre layout
        TextView tvName = layoutItem.findViewById(R.id.birdName);
        ImageView pizzaPicture = layoutItem.findViewById(R.id.birdPicture);

        //(3) : Renseignement des valeurs
        tvName.setText(birds.get(position).getName());
        pizzaPicture.setImageResource(birds.get(position).getPicture());

        layoutItem.setOnClickListener( click -> {
            listener.onClickBird(birds.get(position));
        });
        //On retourne l'item créé.
        return layoutItem;
    }

    //abonnement pour click sur le nom...
    public void addListener(IListener listener) {
        this.listener = listener;
    }
}
