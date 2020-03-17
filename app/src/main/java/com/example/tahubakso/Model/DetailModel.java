package com.example.tahubakso.Model;

import io.realm.RealmObject;

public class DetailModel extends RealmObject {
    private String kodenota;
    private String kodebarang;
    private Integer jumlah;
    private Integer subtotal;

    public String getKodenota() {
        return kodenota;
    }

    public void setKodenota(String kodenota) {
        this.kodenota = kodenota;
    }

    public String getKodebarang() {
        return kodebarang;
    }

    public void setKodebarang(String kodebarang) {
        this.kodebarang = kodebarang;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }
}
