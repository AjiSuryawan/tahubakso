package com.example.tahubakso.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class GuruModel extends RealmObject {


    private Integer idguru;
    @PrimaryKey
    private String codeguru;
    private String nama;

    public Integer getIdguru() {
        return idguru;
    }

    public void setIdguru(Integer idguru) {
        this.idguru = idguru;
    }

    public String getCodeguru() {
        return codeguru;
    }

    public void setCodeguru(String codeguru) {
        this.codeguru = codeguru;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
