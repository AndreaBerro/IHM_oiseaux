package com.example.projetoiseaux.ui.SearchResult;

import com.example.projetoiseaux.R;
import com.example.projetoiseaux.ui.Bird;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.projetoiseaux.ui.Bird.FULL_LIST;
import static com.example.projetoiseaux.ui.Bird.sortedList;

public class ListBird extends ArrayList<Bird> {

    public ListBird(){
        addAll(FULL_LIST);
    }

    public ListBird(String name, String color, int size){
        System.out.println(sortedList);
        if(color != null && !color.equals("Any")){
            System.out.println("colored");
            ArrayList<ArrayList<Bird>> colorList = Objects.requireNonNull(sortedList.get(color));
            filterSize(this, colorList, size, name);
        } else {
            System.out.println("not colored");
            for (String c : sortedList.keySet()){
                ArrayList<ArrayList<Bird>> colorList = Objects.requireNonNull(sortedList.get(c));
                filterSize(this, colorList, size, name);
            }
        }
    }

    private void filterName(ArrayList<Bird> total, ArrayList<Bird> list, String name){
        if (!name.equals("")){
            System.out.println("named");
            total.addAll(list.stream()
                    .filter(b -> b.getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList()));
        } else {
            System.out.println("not named");
            total.addAll(list);
        }
    }

    private void filterSize(ArrayList<Bird> total, ArrayList<ArrayList<Bird>> list, int size, String name){
        if (size != 0){
            System.out.println("sized");
            ArrayList<Bird> sizeList = Objects.requireNonNull(list.get(size));
            filterName(total, sizeList, name);
        } else {
            System.out.println("not sized");
            for (int i = 0; i < list.size(); i++){
                ArrayList<Bird> sizeList = Objects.requireNonNull(list.get(i));
                filterName(total, sizeList, name);
            }
        }
    }
}
