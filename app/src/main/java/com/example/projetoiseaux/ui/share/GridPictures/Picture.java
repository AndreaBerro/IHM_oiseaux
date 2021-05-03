package com.example.projetoiseaux.ui.share.GridPictures;


/**
 * modify by Fred on 09/02/2021.
 */
public class Picture {
    static private long nb_pizza = 1;

    private long id;
    private String path;

    public Picture(String path) {
        this.path = path;
        this.id = nb_pizza++;
    }

    public Picture(String path, long id) {
        this.path = path;
        this.id = id;
    }

    public String getPath() {
        return path;
    }
    public long getId() {
        return id;
    }
}
