package com.freedom.boja.outlet;

public class data_outlet {
    private String title;
    private String alamat;
    private String jam;
    private String gambar;
    private String id_outlet;




    public data_outlet(String id_outlet,String title, String alamat, String jam,String gambar) {
        this.title = title;
        this.alamat = alamat;
        this.jam = jam;
        this.gambar = gambar;
        this.id_outlet = id_outlet;
    }
    public String getTitleid_outlet() {
        return id_outlet;
    }

    public void setid_outlet(String name) {
        this.id_outlet = id_outlet;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }


}
