package com.example.projetoiseaux.ui;

import org.osmdroid.util.GeoPoint;

import java.util.Date;

public class UploadBird {
    private String name;
    private GeoPoint geoPoint;
    private Date date;
    private int picture;

    public UploadBird(String name, GeoPoint geoPoint, Date date, int picture){
        this.name = name;
        this.geoPoint = geoPoint;
        this.date = date;
        this.picture = picture;
    }

    public UploadBird(String name, double x, double y, Date date, int picture){
        this.name = name;
        this.geoPoint = new GeoPoint(x,y);
        this.date = date;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public int getPicture() {
        return picture;
    }

    public GeoPoint getGeoPoint() {return geoPoint;}

    public Date getDate() { return date; }
}
