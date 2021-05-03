package com.example.projetoiseaux.ui;

import org.osmdroid.util.GeoPoint;

import java.util.Date;
import java.util.List;

public class UploadBird {
    private String name;
    private GeoPoint geoPoint;
    private Date date;
    private List<String> picture;

    public UploadBird(String name, GeoPoint geoPoint, Date date, List<String> picture){
        this.name = name;
        this.geoPoint = geoPoint;
        this.date = date;
        this.picture = picture;
    }

    public UploadBird(String name, double x, double y, Date date, List<String> picture){
        this.name = name;
        this.geoPoint = new GeoPoint(x,y);
        this.date = date;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public List<String> getPicture() {
        return picture;
    }

    public GeoPoint getGeoPoint() {return geoPoint;}

    public Date getDate() { return date; }
}
