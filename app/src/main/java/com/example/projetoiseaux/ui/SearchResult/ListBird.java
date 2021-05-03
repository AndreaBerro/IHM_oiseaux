package com.example.projetoiseaux.ui.SearchResult;

import com.example.projetoiseaux.R;
import com.example.projetoiseaux.ui.Bird;

import java.util.ArrayList;

public class ListBird extends ArrayList<Bird> {
    public ListBird(){
        add(new Bird("A", R.drawable.bird1));
        add(new Bird("B", R.drawable.bird2));
        add(new Bird("C", R.drawable.bird3));
        add(new Bird("D", R.drawable.bird4));
    }
}
