package com.example.tahubakso.tahuproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.tahubakso.R;

import org.json.JSONException;
import org.json.JSONObject;

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

        Button btndownload = (Button) findViewById(R.id.btndownloadData);
        btndownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hit API, trus di responnya save ke db local. lihat catat pembelian line 344
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("SessionId", "SesionId_190630");
                    jsonObject.put("Data", "");
                    jsonObject.put("Crud", "r");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AndroidNetworking.post("http://www.ecollectcpu.com:88/api/product/poststaging/")
                        .addHeaders("Content-Type","application/json")
                        .addHeaders("Accept","application/json")
                        .addHeaders("Authorization","Basic V0FZSFlhV0EzZlhTTU83anVJZzJmZz09OlF3NUNNWld4TlQwRUNDRmZhK2g4MmVjSWcvREFEeFM3")
                        .addJSONObjectBody(jsonObject)
                        .setTag("test")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // kalau sukses masukin ke Realm
                                Log.d("hasiljson", "onResponse: "+response.toString());
                            }
                            @Override
                            public void onError(ANError error) {
                                // handle error
                                Log.d("errorku", "onError errorCode : " + error.getErrorCode());
                                Log.d("errorku", "onError errorBody : " + error.getErrorBody());
                                Log.d("errorku", "onError errorDetail : " + error.getErrorDetail());
                            }
                        });
            }
        });

        Button btnsearchdata = (Button)findViewById(R.id.btnsearchdata);
        btnsearchdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchData.class));
            }
        });
    }
}
