package com.freedom.boja.keranjang;

import java.util.Comparator;

/**
 * Created by united on 08/06/2017.
 */

public class Data_keranjang {
    String nama_order;
    String jumlah_order;
    String code_produk;
    String note;
    String harga;
    String total;
    String fkitchen_id;
    String invoice;

    public String getId_paket() {
        return id_paket;
    }

    public void setId_paket(String id_paket) {
        this.id_paket = id_paket;
    }

    String id_paket;


    public String getFcombo_attribute() {
        return fcombo_attribute;
    }

    public void setFcombo_attribute(String fcombo_attribute) {
        this.fcombo_attribute = fcombo_attribute;
    }

    String fcombo_attribute;


    public void setnama_order(String nama_order) {
        this.nama_order = nama_order;
    }
    public String getnama_order() {
        return nama_order;
    }



    public void setjumlah_order(String jumlah_order) {this.jumlah_order = jumlah_order;}
    public String getjumlah_order() {
        return jumlah_order;
    }

    public void setcode_produk(String code_produk) {
        this.code_produk = code_produk;
    }

    public String getcode_produk() {
        return code_produk;
    }

    public void setnote(String note) {
        this.note = note;
    }

    public String getnote() {
        return note;
    }

    public void setharga(String harga) {
        this.harga = harga;
    }

    public String getharga() {
        return harga;
    }

    public void settotal(String total) {
        this.total = total;
    }

    public String gettotal() {
        return total;
    }


    public void setfkitchen_id(String fkitchen_id) {
        this.fkitchen_id = fkitchen_id;
    }

    public String getfkitchen_id() {
        return fkitchen_id;
    }
    public static Comparator<Data_keranjang> StuNameComparator = new Comparator<Data_keranjang>() {




        @Override
        public int compare(Data_keranjang s1, Data_keranjang s2) {
            String StudentName1 = s1.getnama_order().toUpperCase();
            String StudentName2 = s2.getnama_order().toUpperCase();

            //ascending order
            return StudentName1.compareTo(StudentName2);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};


}
