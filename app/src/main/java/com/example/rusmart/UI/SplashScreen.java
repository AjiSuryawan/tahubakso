package com.example.rusmart.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rusmart.Model.ControlLogin;
import com.example.rusmart.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class SplashScreen extends AppCompatActivity {
    File root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        root = new File(Environment.getExternalStorageDirectory() + File.separator + "rusapp", "folderku");

        int x = 2000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                File file = new File(root, "rusapi" + ".txt");
                StringBuilder text = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = br.readLine()) != null) {
                        text.append(line);
                        text.append('\n');
                    }
                    br.close();
                    System.out.println("hasil test splash : " + text);
                    String[] tmparray = text.toString().split(Pattern.quote("#"));
                    baseURL.baseurl = tmparray[0].trim();
                } catch (IOException e) {
                    System.out.println("error baca : " + e.toString());
                    //You'll need to add proper error handling here
                }

                Intent intent = new Intent(SplashScreen.this, ControlLogin.class);
                startActivity(intent);
                finish();

            }
        }, x);

    }
}
