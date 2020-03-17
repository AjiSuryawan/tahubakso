package com.example.tahubakso.tahuproject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tahubakso.R;

public class OrderRecord extends AppCompatActivity {
//    Button btnnamabarang;
    Button btnsaveku;
    EditText txtnamabarang;
    private ProgressDialog progressBar;
    String foto="";
    EditText jumlah;
//    ArrayList<ModelBarang> datalist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catatrecord);
//        datalist=new ArrayList<>();

        Toolbar toolbar = findViewById(R.id.toolbarpopup);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Catatan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        btnnamabarang=(Button)findViewById(R.id.btnnamabarang);
        jumlah=(EditText) findViewById(R.id.txtjumlah);
        btnsaveku=(Button)findViewById(R.id.btnsaveku);
        txtnamabarang=(EditText) findViewById(R.id.txtnamabarang);
//        btnnamabarang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                txtnamabarang.setText("");
//                foto="true";
//                Intent i = new Intent(OrderRecord.this,QRBarcode.class);
//                startActivityForResult(i, 1);
//            }
//
//        });

        btnsaveku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case DialogInterface.BUTTON_POSITIVE:

                Intent intent= new Intent();
                intent.putExtra("namaBarang",txtnamabarang.getText().toString());
                intent.putExtra("kodeBarang","kode01");
                intent.putExtra("jumlah",jumlah.getText().toString());
                intent.putExtra("hargabarang","10000");
                setResult(RESULT_OK,intent);
                finish();


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


    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1) {
//            if (resultCode == OrderRecord.RESULT_OK) {
//                datalist=new ArrayList<>();
//                System.out.println("lala1");
//                String result = data.getStringExtra("text");
//                Log.d("terserah", "onActivityResult: "+result);
//
//                progressBar = new ProgressDialog(OrderRecord.this);
//
//                progressBar.setMessage("Please wait");
//                progressBar.show();
//                progressBar.setCancelable(false);
//                AndroidNetworking.post(baseURL.baseurl+"rusmart/api/getbarang.php")
//
//                        .addBodyParameter("kodebarang",result)
//                        .setTag("test")
//                        .setPriority(Priority.MEDIUM)
//                        .build()
//                        .getAsJSONObject(new JSONObjectRequestListener() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                try {
//                                    System.out.println("lala2");
//                                    Log.d("hasil", "onResponse: "+response.toString());
//                                    JSONArray result = response.getJSONArray("result");
//                                    if(result.length()>0){
//                                        for (int i = 0; i <result.length() ; i++) {
//                                            ModelBarang model = new ModelBarang();
//                                            JSONObject json = result.getJSONObject(i);
//                                            model.setId(json.getString("kodebarang"));
//                                            model.setNamabarang(json.getString("namabarang"));
//                                            model.setHargabarang(Integer.parseInt(json.getString("hargabarang")));
//                                            datalist.add(model);
//                                        }
//                                        txtnamabarang.setText(datalist.get(0).getNamabarang());
//                                        if (progressBar.isShowing()){
//                                            progressBar.dismiss();
//                                        }
//                                    }else{
//                                        Toast.makeText(getApplicationContext(),"data tidak ditemukan",Toast.LENGTH_LONG).show();
//                                        if (progressBar.isShowing()){
//                                            progressBar.dismiss();
//                                        }
//                                    }
//                                } catch (JSONException e) {
//                                    System.out.println("lala3");
//                                    e.printStackTrace();
//                                }
//                            }
//
//                            @Override
//                            public void onError(ANError anError) {
//                                System.out.println("lala4");
//                                Log.d("errorku", "onError: "+anError.getErrorCode());
//                                Log.d("errorku", "onError: "+anError.getErrorBody());
//                                Log.d("errorku", "onError: "+anError.getErrorDetail());
//
//                            }
//                        });
//
//            }
//            if (resultCode == OrderRecord.RESULT_CANCELED) {
//                //Write your code if there's no result
//            }
//        }
//
//    }
}
