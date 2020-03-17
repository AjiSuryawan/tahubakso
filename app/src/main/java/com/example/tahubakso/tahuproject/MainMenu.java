package com.example.tahubakso.tahuproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.tahubakso.R;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainMenu extends AppCompatActivity {
    Realm realm;
    RealmHelperHeaderNota realmHelper;
    RealmHelperDetailNota realmHelperdetail;
    ArrayList<HeaderNotaModel> listheader;
    ArrayList<DetailNotaModel> listdetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        listheader= new ArrayList<>();
        listdetail= new ArrayList<>();
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);
        realmHelper = new RealmHelperHeaderNota(realm);
        realmHelperdetail = new RealmHelperDetailNota(realm);
        Button btngetorder = (Button)findViewById(R.id.btngetorder);
        btngetorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewHeaderNota.class));
//                listheader.addAll(realmHelper.getAllMahasiswa());
//                for (int i = 0; i <listheader.size() ; i++) {
//                    Log.d("kode nota", "hasil: "+listheader.get(i).getCodenota());
//                    Log.d("kode nota", "hasil: "+listheader.get(i).getNamacustomer());
//                }
//                listdetail.addAll(realmHelperdetail.getAllMahasiswa());
//                for (int i = 0; i <listdetail.size() ; i++) {
//                    Log.d("detail", "onClick: "+listdetail.get(i).getCodenota());
//                    Log.d("detail", "onClick: "+listdetail.get(i).getNamabarang());
//                    Log.d("detail", "onClick: "+listdetail.get(i).getJumlahbarang());
//                    Log.d("detail", "onClick: "+listdetail.get(i).getHargabarang());
//                    Log.d("detail", "onClick: "+listdetail.get(i).getSubtotal());
//                }
            }
        });
        Button btnaddorder = (Button)findViewById(R.id.btnaddorder);
        btnaddorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddHeader.class));
            }
        });

    }
}
