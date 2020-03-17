package com.example.tahubakso.tahuproject;

import android.util.Log;

import com.example.tahubakso.Model.GuruModel;
import com.example.tahubakso.Model.ModelBarang;
import com.example.tahubakso.Model.ModelNota;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelperHeaderNota {

    Realm realm;

    public RealmHelperHeaderNota(Realm realm){
        this.realm = realm;
    }

    // untuk menyimpan data
    public void saveheader(final HeaderNotaModel headerNotaModel){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null){
                    Log.e("Created", "Database was created");
                    Number currentIdNum = realm.where(HeaderNotaModel.class).max("iddata");
                    int nextId;
                    if (currentIdNum == null){
                        nextId = 1;
                    }else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    headerNotaModel.setIddata(nextId);
                    HeaderNotaModel model = realm.copyToRealmOrUpdate(headerNotaModel);
                }else{
                    Log.e("ppppp", "execute: Database not Exist");
                }
            }
        });
    }

    // untuk memanggil semua data
    public List<HeaderNotaModel> getAllMahasiswa(){
        RealmResults<HeaderNotaModel> results = realm.where(HeaderNotaModel.class).findAll();
        return results;
    }

    // untuk meng-update data
    public void update(final Integer idguru, final String codeguru, final String nama){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                GuruModel model = realm.where(GuruModel.class)
                        .equalTo("idguru", idguru)
                        .findFirst();
                model.setCodeguru(codeguru);
                model.setNama(nama);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e("pppp", "onSuccess: Update Successfully");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
            }
        });
    }
    public void savenota(final ModelNota savenota){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null){
                    Log.e("Created", "Database was created");
                    Number currentIdNum = realm.where(ModelNota.class).max("id");
                    int nextId;
                    if (currentIdNum == null){
                        nextId = 1;
                    }else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    savenota.setId(nextId);
                    ModelNota model = realm.copyToRealm(savenota);
                }else{
                    Log.e("ppppp", "execute: Database not Exist");
                }
            }
        });
    }
    public RealmResults<ModelNota> getAllNota(){
        RealmResults<ModelNota> results = realm.where(ModelNota.class).findAll();
        return results;
    }
    public void savedetail(final ModelBarang detailModel){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null){
                    Log.e("Created", "Database was created");
                    Number currentIdNum = realm.where(ModelBarang.class).max("id2");
                    int nextId;
                    if (currentIdNum == null){
                        nextId = 1;
                    }else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    detailModel.setId2(nextId);
                    ModelBarang model = realm.copyToRealm(detailModel);
                }else{
                    Log.e("ppppp", "execute: Database not Exist");
                }
            }
        });
    }
    public RealmResults<ModelBarang> getAllDetail(){
        RealmResults<ModelBarang> results = realm.where(ModelBarang.class).findAll();
        return results;
    }
}
