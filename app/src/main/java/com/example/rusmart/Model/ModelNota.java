package com.example.rusmart.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ModelNota extends RealmObject {

    @PrimaryKey
    private int id;
    private String kodenota;
    private String tanggalnota;
    private Integer totalbayar;
    private Integer jumlahuang;
    private Integer potonganharga;
    private Integer kembalian;
    private String kodeguru;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKodenota() {
        return kodenota;
    }

    public void setKodenota(String kodenota) {
        this.kodenota = kodenota;
    }

    public String getTanggalnota() {
        return tanggalnota;
    }

    public void setTanggalnota(String tanggalnota) {
        this.tanggalnota = tanggalnota;
    }

    public Integer getTotalbayar() {
        return totalbayar;
    }

    public void setTotalbayar(Integer totalbayar) {
        this.totalbayar = totalbayar;
    }

    public Integer getJumlahuang() {
        return jumlahuang;
    }

    public void setJumlahuang(Integer jumlahuang) {
        this.jumlahuang = jumlahuang;
    }

    public Integer getPotonganharga() {
        return potonganharga;
    }

    public void setPotonganharga(Integer potonganharga) {
        this.potonganharga = potonganharga;
    }

    public Integer getKembalian() {
        return kembalian;
    }

    public void setKembalian(Integer kembalian) {
        this.kembalian = kembalian;
    }

    public String getKodeguru() {
        return kodeguru;
    }

    public void setKodeguru(String kodeguru) {
        this.kodeguru = kodeguru;
    }
}
