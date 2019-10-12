package com.example.rusmart.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rusmart.R;

public class DeleteOrEdit extends AppCompatActivity {
    int userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_or_edit);

        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            userName = extras.getInt("pos");
            // and get whatever type user account id is
        }

        Button btnedt=findViewById(R.id.btnedit);
        btnedt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("aksi","edit");
                setResult(RESULT_OK,intent);
                finish();
            }
        });Button btndelete=findViewById(R.id.btndelete);
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("aksi","delete");
                intent.putExtra("pos",userName+"");
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
