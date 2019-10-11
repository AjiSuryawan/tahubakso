package com.example.rusmart.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.rusmart.Model.ModelBarang;
import com.example.rusmart.Model.ModelGuru;
import com.example.rusmart.R;
import com.example.rusmart.adapter.adapter_list_item_barang;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class CatatPembelian extends AppCompatActivity {

    ImageView date;
    TextView show;
    Button btnsave;
    private FloatingActionButton fab;
    Spinner spinnerguru;
    private ProgressDialog progressBar;
    ArrayList<ModelGuru> datalist;
    ArrayList<ModelBarang> datalistbarang;
    RecyclerView rvdatapembelian;
    private adapter_list_item_barang adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_1);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Catat Tagihan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        datalist=new ArrayList<>();
        datalistbarang=new ArrayList<>();
        adapter = new adapter_list_item_barang(getApplicationContext(), datalistbarang);
        spinnerguru=findViewById(R.id.spinnerguru);
        rvdatapembelian=findViewById(R.id.rvdatapembelian);
        progressBar = new ProgressDialog(CatatPembelian.this);

        progressBar.setMessage("Please wait");
        progressBar.show();
        progressBar.setCancelable(false);
        AndroidNetworking.get(baseURL.baseurl+"rusmart/getguru.php")
                //.addBodyParameter("kodebarang",result)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println("lala2");
                            Log.d("hasil", "onResponse: "+response.toString());
                            JSONArray result = response.getJSONArray("result");
                            for (int i = 0; i <result.length() ; i++) {
                                ModelGuru model = new ModelGuru();
                                JSONObject json = result.getJSONObject(i);
                                model.setKodeGuru(json.getString("kodeGuru"));
                                model.setNamaGuru(json.getString("namaGuru"));
                                datalist.add(model);
                            }
                            String[] namaguru=new String[datalist.size()];
                            for (int i = 0; i <datalist.size() ; i++) {
                                namaguru[i]=datalist.get(i).getNamaGuru();
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                    CatatPembelian.this,
                                    android.R.layout.simple_spinner_item,
                                    namaguru
                            );
                            spinnerguru.setAdapter(adapter);
                            if (progressBar.isShowing()){
                                progressBar.dismiss();
                            }
                        } catch (JSONException e) {
                            if (progressBar.isShowing()){
                                progressBar.dismiss();
                            }
                            System.out.println("lala3");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (progressBar.isShowing()){
                            progressBar.dismiss();
                        }
                        System.out.println("lala4");
                        Log.d("errorku", "onError: "+anError.getErrorCode());
                        Log.d("errorku", "onError: "+anError.getErrorBody());
                        Log.d("errorku", "onError: "+anError.getErrorDetail());

                    }
                });


        date = findViewById(R.id.date);
        show = findViewById(R.id.show);
        btnsave = findViewById(R.id.btnsave);

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatatPembelian.this,HomeDashboard.class);
                startActivity(intent);
            }
        });

        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        CatatPembelian.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        month = month + 1;
                        String datef = day + "/" + month + "/" + year;
                        show.setText(datef);

                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        CatatPembelian.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        month = month + 1;
                        String datef = day + "/" + month + "/" + year;
                        show.setText(datef);

                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(CatatPembelian.this, PopUp.class);
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
            ModelBarang barang =new ModelBarang();
            barang.setId(kodeBarang);
            barang.setNamabarang(namaBarang);
            barang.setJumlah(Integer.parseInt(jumlah));
            datalistbarang.add(barang);
            rvdatapembelian.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            rvdatapembelian.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
