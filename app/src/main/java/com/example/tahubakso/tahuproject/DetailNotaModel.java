package com.example.tahubakso.tahuproject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DetailNotaModel extends RealmObject {

    @PrimaryKey
    private Integer iddata;
    private String codenota;
    private String namabarang;
    private String hargabarang;
    private String jumlahbarang;
    private String subtotal;

    public String getHargabarang() {
        return hargabarang;
    }

    public void setHargabarang(String hargabarang) {
        this.hargabarang = hargabarang;
    }

    public String getJumlahbarang() {
        return jumlahbarang;
    }

    public void setJumlahbarang(String jumlahbarang) {
        this.jumlahbarang = jumlahbarang;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }



    public Integer getIddata() {
        return iddata;
    }

    public void setIddata(Integer iddata) {
        this.iddata = iddata;
    }

    public String getCodenota() {
        return codenota;
    }

    public void setCodenota(String codenota) {
        this.codenota = codenota;
    }

    public String getNamabarang() {
        return namabarang;
    }

    public void setNamabarang(String namabarang) {
        this.namabarang = namabarang;
    }
}
