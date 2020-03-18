package com.example.tahubakso.tahuproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tahubakso.R;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class SearchData extends AppCompatActivity {

    EditText txtcari;
    Button btncari;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_data);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);
        txtcari=(EditText)findViewById(R.id.txtcari);
        btncari=(Button)findViewById(R.id.btncari);
        btncari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmResults<HeaderNotaModel> results = realm.where(HeaderNotaModel.class)
                        .contains("namacustomer",txtcari.getText().toString(), Case.INSENSITIVE).findAll();
                Log.d("jumlah search", "onClick: "+results.size());
            }
        });
    }















}
