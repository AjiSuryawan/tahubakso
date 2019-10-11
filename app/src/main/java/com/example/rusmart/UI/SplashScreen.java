package com.example.rusmart.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rusmart.Model.ControlLogin;
import com.example.rusmart.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int x = 2000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                Intent intent = new Intent(SplashScreen.this, ControlLogin.class);
                startActivity(intent);
                finish();

            }
        }, x);

    }
}
