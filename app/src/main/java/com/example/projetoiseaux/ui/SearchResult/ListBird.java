package com.example.projetoiseaux.ui.SearchResult;

import com.example.projetoiseaux.Bird.Bird;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.projetoiseaux.Bird.Bird.FULL_LIST;
import static com.example.projetoiseaux.Bird.Bird.sortedList;

public class ListBird extends ArrayList<Bird> {

    public ListBird(){
        addAll(FULL_LIST);
    }

    public ListBird(String name, String color, int size){
        System.out.println(sortedList);
        if(color != null && !color.equals("Any")){
            ArrayList<ArrayList<Bird>> colorList = Objects.requireNonNull(sortedList.get(color));
            filterSize(this, colorList, size, name);
        } else {
            for (String c : sortedList.keySet()){
                ArrayList<ArrayList<Bird>> colorList = Objects.requireNonNull(sortedList.get(c));
                filterSize(this, colorList, size, name);
            }
        }
    }

    private void filterName(ArrayList<Bird> total, ArrayList<Bird> list, String name){
        List<Bird> birds;
        if (!name.equals("")){
            birds = list.stream()
                    .filter(b -> b.getName().toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        } else {
            birds = list;
        }
        for(Bird bird : birds){
            if(!total.contains(bird)){
                total.add(bird);
            }
        }
    }

    private void filterSize(ArrayList<Bird> total, ArrayList<ArrayList<Bird>> list, int size, String name){
        if (size != 0){
            ArrayList<Bird> sizeList = Objects.requireNonNull(list.get(size));
            filterName(total, sizeList, name);
        } else {
            for (int i = 0; i < list.size(); i++){
                ArrayList<Bird> sizeList = Objects.requireNonNull(list.get(i));
                filterName(total, sizeList, name);
            }
        }
    }

    public ListBird(ArrayList<String> birdNames){
        for(String name : birdNames){
            filterName(this, FULL_LIST, name);
        }
    }
}
