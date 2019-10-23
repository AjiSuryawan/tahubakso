package com.example.rusmart.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.rusmart.Model.GuruModel;
import com.example.rusmart.Model.ModelBarang;
import com.example.rusmart.R;
import com.example.rusmart.RealmHelper;
import com.example.rusmart.adapter.adapter_list_item_barang;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class CatatPembelian extends AppCompatActivity {
    String kodenota;
    ImageView date;
    TextView show;
    TextView txttotalbayar;
    Button btnsave;
    private FloatingActionButton fab;
    Spinner spinnerguru;
    private ProgressDialog progressBar;
    RealmList<GuruModel> datalist;
    ArrayList<ModelBarang> datalistbarang;
    RecyclerView rvdatapembelian;
    private adapter_list_item_barang adapter;
    int totalbayar=0;
    DecimalFormat kursIndonesia;
    DecimalFormatSymbols formatRp;
    Realm realm;
    RealmHelper realmHelper;
    String posisiguru;
    Date datesave;
    DateFormat df2;
    ImageView reload;

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        new MenuInflater(this).inflate(R.menu.menu, menu);
//        return (super.onCreateOptionsMenu(menu));
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        if (item.getItemId() == R.id.reload) {
//            if (!progressBar.isShowing()){
//                progressBar.show();
//                loadapi();
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catatpembelian);
        reload = findViewById(R.id.reload);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!progressBar.isShowing()){
                     progressBar.show();
                     loadapi();
                }
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Catat Tagihan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        datalist= new RealmList<>();
        datalistbarang=new ArrayList<>();
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);
        realmHelper = new RealmHelper(realm);
        adapter = new adapter_list_item_barang(getApplicationContext(), datalistbarang, new adapter_list_item_barang.CustgroupListener() {
            @Override
            public void onClickListener(int position) {
                Intent in =new Intent(getApplicationContext(),DeleteOrEdit.class);
                in.putExtra("pos",position);
                in.putExtra("namaBarang",datalistbarang.get(position).getNamabarang());
                in.putExtra("kodeBarang",datalistbarang.get(position).getId());
                in.putExtra("hargabarang",datalistbarang.get(position).getHargabarang());
                startActivityForResult(in,101);
            }

            @Override
            public void onInfoClickListener(int position) {

            }
        });
        spinnerguru=findViewById(R.id.spinnerguru);
        txttotalbayar=findViewById(R.id.txttotalbayar);
        rvdatapembelian=findViewById(R.id.rvdatapembelian);
        progressBar = new ProgressDialog(CatatPembelian.this);
        progressBar.setMessage("Please wait");
        progressBar.show();
        progressBar.setCancelable(false);
        datalist.clear();
        datalist.addAll(realmHelper.getAllMahasiswa());
        Log.d("jumlahnya ", "onCreate: "+datalist.size());
        if (datalist.size() == 0){
            loadapi();
        }else{
            datalist.clear();
            datalist.addAll(realmHelper.getAllMahasiswa());
            String[] namaguru=new String[datalist.size()];
            for (int i = 0; i <datalist.size() ; i++) {
                namaguru[i]=datalist.get(i).getNama();
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
        }

        date = findViewById(R.id.date);
        show = findViewById(R.id.show);
        btnsave = findViewById(R.id.btnsave);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case DialogInterface.BUTTON_POSITIVE:
                                progressBar.show();
                                progressBar.setCancelable(false);
                                int tmp= spinnerguru.getSelectedItemPosition();
                                posisiguru=datalist.get(tmp).getCodeguru();
                                datesave = new Date();
                                df2 = new SimpleDateFormat("ddMMyyyyhhmmss");
                                DateFormat df3 = new SimpleDateFormat("dd");
                                DateFormat df4 = new SimpleDateFormat("MM");
                                DateFormat df5 = new SimpleDateFormat("yyyy");
                                kodenota = "RUSMART" + df2.format(datesave);
                                AndroidNetworking.post(baseURL.baseurl+"rusmart/insertnota.php")
                                        .addBodyParameter("kodenota",kodenota)
                                        .addBodyParameter("tanggalnota",df3.format(datesave).toString())
                                        .addBodyParameter("bulannota",df4.format(datesave).toString())
                                        .addBodyParameter("tahunnota",df5.format(datesave).toString())
                                        .addBodyParameter("totalbayar",totalbayar+"")
                                        .addBodyParameter("jumlahuang","0")
                                        .addBodyParameter("potonganharga","0")
                                        .addBodyParameter("kembalian","0")
                                        .addBodyParameter("kodeguru",posisiguru)
                                        .setTag("test")
                                        .setPriority(Priority.MEDIUM)
                                        .build()
                                        .getAsJSONObject(new JSONObjectRequestListener() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    System.out.println("lala2");
                                                    Log.d("hasil", "onResponse: "+response.toString());
                                                    JSONObject result = response.getJSONObject("hasil");
                                                    boolean responku=result.getBoolean("respon");
                                                    if (responku){
                                                        Toast.makeText(getApplicationContext(),"sukses input",Toast.LENGTH_LONG).show();
                                                        //
                                                        for (int j = 0; j <datalistbarang.size() ; j++) {
                                                            AndroidNetworking.post(baseURL.baseurl+"rusmart/insertnotadetail.php")
                                                                    .addBodyParameter("kodenota", kodenota)
                                                                    .addBodyParameter("kodeBarang", datalistbarang.get(j).getId())
                                                                    .addBodyParameter("jumlah", datalistbarang.get(j).getJumlah()+"")
                                                                    .addBodyParameter("subtotal", (datalistbarang.get(j).getJumlah()*datalistbarang.get(j).getHargabarang())+"")
                                                                    .setTag("test")
                                                                    .setPriority(Priority.MEDIUM)
                                                                    .build()
                                                                    .getAsJSONObject(new JSONObjectRequestListener() {
                                                                        @Override
                                                                        public void onResponse(JSONObject response) {
                                                                            // do anything with response
                                                                        }
                                                                        @Override
                                                                        public void onError(ANError error) {
                                                                            // handle error
                                                                        }
                                                                    });
                                                        }
                                                        //
                                                        if (progressBar.isShowing()){
                                                            progressBar.dismiss();
                                                            finish();
                                                        }
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

                                break;
                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Apakah anda yakin ingin simpan data?").setPositiveButton("Ya", dialog)
                        .setNegativeButton("Tidak", dialog).show();
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
    private void loadapi() {
        AndroidNetworking.get(baseURL.baseurl+"rusmart/getguru.php")
                //.addBodyParameter("kodebarang",result)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            datalist.clear();
                            System.out.println("lala2");
                            Log.d("hasil", "onResponse: "+response.toString());
                            JSONArray result = response.getJSONArray("result");
                            for (int i = 0; i <result.length() ; i++) {
                                GuruModel model = new GuruModel();
                                JSONObject json = result.getJSONObject(i);
                                model.setCodeguru(json.getString("kodeGuru"));
                                model.setNama(json.getString("namaGuru"));
                                datalist.add(model);
                                realmHelper = new RealmHelper(realm);
                                realmHelper.save(model);
                            }

                            String[] namaguru=new String[datalist.size()];
                            for (int i = 0; i <datalist.size() ; i++) {
                                namaguru[i]=datalist.get(i).getNama();
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
        }else if (requestCode == 101 && resultCode == Activity.RESULT_OK){
            String aksi = data.getStringExtra("aksi");
            String posku=data.getStringExtra("pos");
            int tmpposku= Integer.parseInt(posku);
            if(aksi.equalsIgnoreCase("edit")){
                totalbayar-=(datalistbarang.get(tmpposku).getHargabarang()*datalistbarang.get(tmpposku).getJumlah());
                datalistbarang.remove(tmpposku);
                String namaBarang = data.getStringExtra("namaBarang");
                String kodeBarang = data.getStringExtra("kodeBarang");
                String jumlah = data.getStringExtra("jumlah");
                String hargabarang = data.getStringExtra("hargabarang");
                ModelBarang barang =new ModelBarang();
                totalbayar+=(Integer.parseInt(jumlah)*Integer.parseInt(hargabarang));
                txttotalbayar.setText(kursIndonesia.format(totalbayar));
                Log.d("bayaran", "onActivityResult: "+totalbayar);
                barang.setId(kodeBarang);
                barang.setNamabarang(namaBarang);
                barang.setJumlah(Integer.parseInt(jumlah));
                barang.setHargabarang(Integer.parseInt(hargabarang));
                datalistbarang.add(barang);
                adapter.notifyDataSetChanged();
            }else if (aksi.equalsIgnoreCase("delete")){
                totalbayar-=(datalistbarang.get(tmpposku).getHargabarang()*datalistbarang.get(tmpposku).getJumlah());
                txttotalbayar.setText(kursIndonesia.format(totalbayar));
                datalistbarang.remove(tmpposku);
                //update rv
                Log.d("jumlahskrg", "onActivityResult: "+datalistbarang.size());
                adapter.notifyDataSetChanged();
            }

        }
    }
}