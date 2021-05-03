package com.example.projetoiseaux.ui;

public class Bird {
    private String name;
    private int picture;

    public Bird(String name, int picture){
        this.name = name;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public int getPicture() {
        return picture;
    }
}
