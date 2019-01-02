package com.freedom.boja.aplication;

import android.app.Application;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by united on 17-Jan-18.
 */


public class AplicationsMrk extends Application {

    String id_outlet;
    String outlet;

    public String id_outlet() {
        return id_outlet;
    }

    public void setid(String id_outlet) {
        this.id_outlet = id_outlet;
    }
    public String getOutlet() {
        return outlet;
    }
    public void setOutlet(String outlet) {
        this.outlet = outlet;
    }



    public String getid() {
        return id_outlet;
    }

    String set_marker="0";
    public LatLng getCoordinat() {
        return coordinat;
    }

    public void setCoordinat(LatLng coordinat) {
        this.coordinat = coordinat;
    }

    LatLng coordinat=null;

    public double getLang() {
        return lang;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }

    double lang;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    double lat;

}
