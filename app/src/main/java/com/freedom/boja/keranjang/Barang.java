package com.freedom.boja.keranjang;

import java.io.Serializable;

public class Barang implements Serializable {

    /* private String nama;
     private String merk;
     private String harga;
     private String key;*/
    private String key;
    private String invoice;
    private String status;



    public String getNama() {
        return invoice;
    }

    public void setNama(String nama) {
        this.invoice = nama;
    }

    public String getStatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }



    @Override
    public String toString() {
        return " " + invoice + "\n" +
                " " + status;
    }

    public Barang(String invoice2, String status2) {
        invoice = invoice2;
        status = status2;

    }
}