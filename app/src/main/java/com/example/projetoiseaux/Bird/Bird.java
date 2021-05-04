package com.example.projetoiseaux.Bird;

import com.example.projetoiseaux.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class Bird {
    public static ArrayList<Bird> FULL_LIST = new ArrayList<>(
            Arrays.asList(
                    new Bird("Rouge-Gorge Familier", "Grey", 1,R.drawable.rouge_gorge_familier),
                    new Bird("Mesange Bleu", "Blue", 1, R.drawable.mesange),
                    new Bird("Merle Noir", "Black", 2, R.drawable.merle_noir),
                    new Bird("Moineau Domestique", "Maroon", 1, R.drawable.moineau_domestique),
                    new Bird("Pinson des Arbres", "Maroon", 1, R.drawable.pinson_des_arbres)
            )
    );

    public static ArrayList<String> COLORS;

    public static String[] SIZES;


    public static HashMap<String, ArrayList<ArrayList<Bird>>> sortedList;

    private final String name;
    private final String color;
    private final int size;
    private final int picture;

    public Bird(String name, String color, int size, int picture){
        this.name = name;
        this.color = color;
        this.size = size;
        this.picture = picture;
        sortList();
        Objects.requireNonNull(sortedList.get(color)).get(size).add(this);
    }

    public void sortList(){
        if (sortedList == null) {
            sortedList = new HashMap<>();
             COLORS = new ArrayList<>(
                    Arrays.asList("Any", "Red", "Blue", "Green", "Yellow", "Purple", "Cyan", "Magenta", "Maroon", "Grey", "Black", "White")
            );
            SIZES = new String[]{"Any", "Very Small", "Small", "Medium", "Big", "Very Big"};
            System.out.println(COLORS);
            System.out.println(Arrays.toString(SIZES));
            for (String color : COLORS) {
                ArrayList<ArrayList<Bird>> sizeLists = new ArrayList<>();
                for (int i = 0; i < SIZES.length; i++)
                    sizeLists.add(new ArrayList<>());
                sortedList.put(color, sizeLists);
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    public int getPicture() {
        return picture;
    }
}
