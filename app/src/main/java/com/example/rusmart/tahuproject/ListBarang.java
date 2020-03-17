package com.example.rusmart.tahuproject;

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

import com.example.rusmart.Model.ModelBarang;
import com.example.rusmart.R;
import com.example.rusmart.UI.CatatPembelian;
import com.example.rusmart.UI.DeleteOrEdit;
import com.example.rusmart.UI.PopUp;
import com.example.rusmart.adapter.adapter_list_item_barang;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class ListBarang extends AppCompatActivity {
    private FloatingActionButton fab;
    int totalbayar=0;
    ArrayList<ModelBarang> datalistbarang;
    RecyclerView rvdatapembelian;
    private adapter_list_item_barang adapter;
    TextView txttotalbayar;
    DecimalFormat kursIndonesia;
    DecimalFormatSymbols formatRp;
    Button btnsave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_barang);
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
            String kodeBarang = data.getStringExtra("kodeBarang");
            String jumlah = data.getStringExtra("jumlah");
            String hargabarang = data.getStringExtra("hargabarang");
            ModelBarang barang =new ModelBarang();
            totalbayar+=(Integer.parseInt(jumlah)*Integer.parseInt(hargabarang));
            Log.d("bayaran", "onActivityResult: "+totalbayar);
            barang.setId(kodeBarang);
            barang.setNamabarang(namaBarang);
            barang.setJumlah(Integer.parseInt(jumlah));
            barang.setHargabarang(Integer.parseInt(hargabarang));
            datalistbarang.add(barang);
            rvdatapembelian.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            rvdatapembelian.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            txttotalbayar.setText(kursIndonesia.format(totalbayar));
        }
    }
}
