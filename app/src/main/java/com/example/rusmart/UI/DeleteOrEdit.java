package com.example.rusmart.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rusmart.R;

public class DeleteOrEdit extends AppCompatActivity {
    int posisi;
    String kodebarang;
    String namabarang;
    int hargabarang;
    EditText txtjumlahedit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_or_edit);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            posisi = extras.getInt("pos");
            kodebarang = extras.getString("kodeBarang");
            namabarang = extras.getString("namaBarang");
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
                Intent intent=new Intent();
                intent.putExtra("aksi","delete");
                intent.putExtra("pos", posisi +"");
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
