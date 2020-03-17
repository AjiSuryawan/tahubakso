package com.example.rusmart.tahuproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rusmart.R;

public class AddHeader extends AppCompatActivity {

    EditText txtkodenota;
    EditText txtnamapembeli;
    Button btnnext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_header);
        txtkodenota = (EditText)findViewById(R.id.txtkodenota);
        txtnamapembeli = (EditText)findViewById(R.id.txtnamapembeli);
        btnnext = (Button)findViewById(R.id.btnnext);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ListBarang.class);
                in.putExtra("kodenota",txtkodenota.getText().toString());
                in.putExtra("namapembeli",txtnamapembeli.getText().toString());
                startActivity(in);
                finish();
            }
        });
    }
}
