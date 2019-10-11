package com.example.rusmart.Model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rusmart.UI.HomeDashboard;
import com.example.rusmart.UI.Login;

public class ControlLogin extends AppCompatActivity {

    private SharedPreferences mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLogin = getSharedPreferences("login", Context.MODE_PRIVATE);

        Log.d("username", "onCreate: " + mLogin.getString("username", "missing"));

        if (mLogin.getString("username", "").equalsIgnoreCase("")
                || mLogin.getString("username", "") == null
                || mLogin.getString("username", "").isEmpty()){
            Intent intent = new Intent(ControlLogin.this, Login.class);
            startActivity(intent);
            finish();

        }else {

            Intent intent = new Intent(ControlLogin.this, HomeDashboard.class);
            startActivity(intent);
            finish();

        }

    }
}
