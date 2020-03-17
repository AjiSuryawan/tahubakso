package com.example.tahubakso.tahuproject;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tahubakso.R;
import com.example.tahubakso.adapter.adapter_list_item_barang;
import com.example.tahubakso.adapter.adapter_list_item_nota;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ViewDetailNota extends AppCompatActivity {
    ArrayList<DetailNotaModel> datalistNota;
    RecyclerView rvdatapembelian;
    private adapter_list_item_barang adapter;

    DecimalFormat kursIndonesia;
    DecimalFormatSymbols formatRp;
    RealmHelperHeaderNota realmHelper;
    RealmHelperDetailNota realmHelperdetail;
    Realm realm;
    String kodenota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_header_nota);
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Menu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);
        realmHelper = new RealmHelperHeaderNota(realm);
        realmHelperdetail = new RealmHelperDetailNota(realm);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            kodenota = extras.getString("kodenota");
            // and get whatever type user account id is
        }

        kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        datalistNota=new ArrayList<>();
//        txttotalbayar=findViewById(R.id.txttotalbayar);

        rvdatapembelian=findViewById(R.id.rvdatapembelian);
        adapter = new adapter_list_item_barang(getApplicationContext(), datalistNota, new adapter_list_item_barang.CustgroupListener() {
            @Override
            public void onClickListener(int position) {
//                String kodenota = datalistNota.get(position).getCodenota();
//                Toast.makeText(ViewDetailNota.this, ""+kodenota, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInfoClickListener(int position) {

            }
        });

        datalistNota.addAll(realmHelperdetail.getdetailnotabyid(kodenota));
        rvdatapembelian.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvdatapembelian.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//        txttotalbayar.setText(kursIndonesia.format(totalbayar));

    }
}
