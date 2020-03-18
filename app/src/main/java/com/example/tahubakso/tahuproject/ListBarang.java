package com.example.tahubakso.tahuproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tahubakso.R;
import com.example.tahubakso.adapter.adapter_list_item_barang;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ListBarang extends AppCompatActivity {
    private FloatingActionButton fab;
    int totalbayar=0;
    ArrayList<DetailNotaModel> datalistbarang;
    RecyclerView rvdatapembelian;
    private adapter_list_item_barang adapter;
    TextView txttotalbayar;
    DecimalFormat kursIndonesia;
    DecimalFormatSymbols formatRp;
    Button btnsave;
    Bundle extras;
    String kodenota;
    String namapembeli;
    RealmHelperHeaderNota realmHelper;
    RealmHelperDetailNota realmHelperdetail;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_barang);

        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);
        realmHelper = new RealmHelperHeaderNota(realm);
        realmHelperdetail = new RealmHelperDetailNota(realm);

        extras = getIntent().getExtras();
        if (extras != null) {
            kodenota = extras.getString("kodenota");
            namapembeli = extras.getString("namapembeli");
            // and get whatever type user account id is
        }

        kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        datalistbarang=new ArrayList<>();
        txttotalbayar=findViewById(R.id.txttotalbayar);
        btnsave=findViewById(R.id.btnsave);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("makananheader", "onClick: "+kodenota+" , "+namapembeli);
                HeaderNotaModel headerNotaModel = new HeaderNotaModel();
                headerNotaModel.setCodenota(kodenota);
                headerNotaModel.setNamacustomer(namapembeli);
                realmHelper.saveheader(headerNotaModel);
                for (int i = 0; i <datalistbarang.size() ; i++) {
                    //save realm2
                    DetailNotaModel detailNotaModel = new DetailNotaModel();
                    detailNotaModel.setCodenota(datalistbarang.get(i).getCodenota());
                    detailNotaModel.setJumlahbarang(datalistbarang.get(i).getJumlahbarang());
                    detailNotaModel.setHargabarang(datalistbarang.get(i).getHargabarang());
                    detailNotaModel.setSubtotal(datalistbarang.get(i).getSubtotal());
                    detailNotaModel.setNamabarang(datalistbarang.get(i).getNamabarang());
                    realmHelperdetail.savedetail(detailNotaModel);
                    Log.d("makanandetail", "onClick: "+datalistbarang.get(i).getCodenota());
                    Log.d("makanandetail", "onClick: "+datalistbarang.get(i).getNamabarang());
                    Log.d("makanandetail", "onClick: "+datalistbarang.get(i).getJumlahbarang());
                    Log.d("makanandetail", "onClick: "+datalistbarang.get(i).getHargabarang());
                    Log.d("makanandetail", "onClick: "+datalistbarang.get(i).getSubtotal());

                }
                Date date = new Date();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                //mengurai ke string
                String header= kodenota+";"+df.format(date)+";"+namapembeli;
                String datanya="";
                for (int i = 0; i <datalistbarang.size() ; i++) {
                    DetailNotaModel data = datalistbarang.get(i);
                    datanya+=kodenota+";"+data.getNamabarang()+";"+data.getJumlahbarang()+";"+data.getHargabarang()+";"+data.getSubtotal()+"#";
                }
                Log.d("header", "onClick: "+header);
                Log.d("detail nota", "onClick: "+datanya);
                //yang dikirim header dan datanya pakai FAN

                finish();
            }
        });
        rvdatapembelian=findViewById(R.id.rvdatapembelian);
        adapter = new adapter_list_item_barang(getApplicationContext(), datalistbarang, new adapter_list_item_barang.CustgroupListener() {
            @Override
            public void onClickListener(int position) {
//                Intent in =new Intent(getApplicationContext(), DeleteOrEdit.class);
//                in.putExtra("pos",position);
//                in.putExtra("namaBarang",datalistbarang.get(position).getNamabarang());
//                in.putExtra("kodeBarang",datalistbarang.get(position).getId());
//                in.putExtra("hargabarang",datalistbarang.get(position).getHargabarang());
//                startActivityForResult(in,101);
            }

            @Override
            public void onInfoClickListener(int position) {

            }
        });
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListBarang.this, OrderRecord.class);
                startActivityForResult(i, 100);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            String namaBarang = data.getStringExtra("namaBarang");
//            String kodeBarang = data.getStringExtra("kodeBarang");
            String jumlah = data.getStringExtra("jumlah");
            String hargabarang = data.getStringExtra("hargabarang");
            DetailNotaModel barang =new DetailNotaModel();
            totalbayar+=(Integer.parseInt(jumlah)*Integer.parseInt(hargabarang));
            Log.d("bayaran", "onActivityResult: "+totalbayar);
            barang.setCodenota(kodenota);
            barang.setNamabarang(namaBarang);
            barang.setJumlahbarang(jumlah);
            barang.setHargabarang(hargabarang);
            barang.setSubtotal(Integer.parseInt(jumlah)*Integer.parseInt(hargabarang)+"");
            datalistbarang.add(barang);
            rvdatapembelian.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            rvdatapembelian.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            txttotalbayar.setText(kursIndonesia.format(totalbayar));
        }
    }
}