package com.example.tahubakso.tahuproject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class HeaderNotaModel extends RealmObject {


    private Integer iddata;
    @PrimaryKey
    private String codenota;
    private String namacustomer;

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

    public String getNamacustomer() {
        return namacustomer;
    }

    public void setNamacustomer(String namacustomer) {
        this.namacustomer = namacustomer;
    }
}
