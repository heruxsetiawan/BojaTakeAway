package com.freedom.boja.menu;

public class data_menu {
    private String title;
    private String spek;
    private String harga;
    private String gambar;
    private String qty;
    private String note;
    private String code_produk;



    public data_menu(String code_produk,String title, String spek, String harga, String gambar,String qty,String note) {
        this.code_produk = code_produk;
        this.title = title;
        this.spek = spek;
        this.harga = harga;
        this.gambar = gambar;
        this.qty = qty;
        this.note = note;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getspek() {
        return spek;
    }

    public void setspek(String spek) {
        this.spek = spek;
    }

    public String getharga() {
        return harga;
    }

    public void setharga(String harga) {
        this.harga = harga;
    }
    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCode_produk() {
        return code_produk;
    }

    public void setCode_produk(String code_produk) {
        this.code_produk = code_produk;
    }


}
