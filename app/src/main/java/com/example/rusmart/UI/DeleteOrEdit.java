package com.example.rusmart.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.rusmart.R;

public class DeleteOrEdit extends AppCompatActivity {
    int posisi;
    String kodebarang;
    String namabarang;
    int hargabarang;
    EditText txtjumlahedit;
    TextView tvkodebarang;
    TextView tvnamabarang;

    Toolbar toolbardeloredt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_or_edit);

        tvkodebarang = findViewById(R.id.tvkodebarang);
        tvnamabarang = findViewById(R.id.tvnamabarang);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Delete or Edit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            posisi = extras.getInt("pos");
            kodebarang = extras.getString("kodeBarang");
            tvkodebarang.setText(kodebarang);
            namabarang = extras.getString("namaBarang");
            tvnamabarang.setText(namabarang);
            hargabarang = extras.getInt("hargabarang");
            // and get whatever type user account id is
        }
        txtjumlahedit=findViewById(R.id.txtjumlahedit);
        Button btnedt=findViewById(R.id.btnedit);
        btnedt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("pos", posisi +"");
                intent.putExtra("aksi","edit");
                intent.putExtra("kodeBarang",kodebarang);
                intent.putExtra("namaBarang",namabarang);
                intent.putExtra("hargabarang",hargabarang+"");
                intent.putExtra("jumlah",txtjumlahedit.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }
        });Button btndelete=findViewById(R.id.btndelete);
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteOrEdit.this);
                builder.setCancelable(false);
                builder.setMessage("are you sure wanna delete this item?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent();
                        intent.putExtra("aksi","delete");
                        intent.putExtra("pos", posisi +"");
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                //

            }
        });
    }

}
