package com.example.rusmart.Model;

public class ModelBarang {
    private String id,namabarang;
    private int jumlah=0;
    private int hargabarang=0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamabarang() {
        return namabarang;
    }

    public void setNamabarang(String namabarang) {
        this.namabarang = namabarang;
    }

    public ModelBarang() {

    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getHargabarang() {
        return hargabarang;
    }

    public void setHargabarang(int hargabarang) {
        this.hargabarang = hargabarang;
    }
}
