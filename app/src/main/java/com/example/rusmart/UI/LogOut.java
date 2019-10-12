package com.example.rusmart.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.rusmart.Model.PrefManager;
import com.example.rusmart.R;
import com.facebook.login.LoginManager;

public class LogOut extends AppCompatActivity {

    Button btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_out);

        btnLogOut = findViewById(R.id.btnLogOut);

        SharedPreferences mLogin = getSharedPreferences("login", Context.MODE_PRIVATE);



        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences mLogin = getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mLogin.edit();
                editor.clear();
                editor.apply();


                Intent intent = new Intent(LogOut.this, SplashScreen.class);
                startActivity(intent);
                finish();


                PrefManager prefManager = new PrefManager(getApplicationContext());
                prefManager.setFirstTimeLaunch(true);
                finish();

            }
        });

    }
}
