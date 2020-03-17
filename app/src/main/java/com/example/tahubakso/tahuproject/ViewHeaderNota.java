package com.example.tahubakso.tahuproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tahubakso.R;
import com.example.tahubakso.adapter.adapter_list_item_barang;
import com.example.tahubakso.adapter.adapter_list_item_nota;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ViewHeaderNota extends AppCompatActivity {
    ArrayList<HeaderNotaModel> datalistNota;
    RecyclerView rvdatapembelian;
    private adapter_list_item_nota adapter;

    DecimalFormat kursIndonesia;
    DecimalFormatSymbols formatRp;
    RealmHelperHeaderNota realmHelper;
    RealmHelperDetailNota realmHelperdetail;
    Realm realm;


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

        kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        datalistNota=new ArrayList<>();
//        txttotalbayar=findViewById(R.id.txttotalbayar);

        rvdatapembelian=findViewById(R.id.rvdatapembelian);
        adapter = new adapter_list_item_nota(getApplicationContext(), datalistNota, new adapter_list_item_nota.CustgroupListener() {
            @Override
            public void onClickListener(int position) {
                String kodenota = datalistNota.get(position).getCodenota();
                Toast.makeText(ViewHeaderNota.this, ""+kodenota, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInfoClickListener(int position) {

            }
        });

        datalistNota.addAll(realmHelper.getAllMahasiswa());
        rvdatapembelian.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvdatapembelian.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//        txttotalbayar.setText(kursIndonesia.format(totalbayar));

    }
}
