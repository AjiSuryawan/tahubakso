package com.example.tahubakso.tahuproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.tahubakso.R;
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
    TextView txttotalbayar;
    DecimalFormat kursIndonesia;
    DecimalFormatSymbols formatRp;
    Bundle extras;
    String kodenota;
    String namapembeli;
    RealmHelperHeaderNota realmHelper;
    RealmHelperDetailNota realmHelperdetail;
    Realm realm;
    int totalbayar=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_header_nota);
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
        txttotalbayar=findViewById(R.id.txttotalbayar);

        datalistNota.addAll(realmHelper.getAllMahasiswa());
        rvdatapembelian.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvdatapembelian.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        txttotalbayar.setText(kursIndonesia.format(totalbayar));

    }
}
