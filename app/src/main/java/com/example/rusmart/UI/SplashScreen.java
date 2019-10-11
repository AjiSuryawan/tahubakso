package com.example.rusmart.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

<<<<<<< HEAD:app/src/main/java/com/example/rusmart/UI/SplashScreen.java
import com.example.rusmart.R;

public class SplashScreen extends AppCompatActivity {
=======
import androidx.appcompat.app.AppCompatActivity;

import com.example.rusmart.Model.ControlLogin;

public class MainActivity extends AppCompatActivity {
>>>>>>> 92c821487245f06c1b1f311ee768b6025588ea0f:app/src/main/java/com/example/rusmart/MainActivity.java

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int x = 2000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

<<<<<<< HEAD:app/src/main/java/com/example/rusmart/UI/SplashScreen.java
                Intent i = new Intent(SplashScreen.this, Login.class);
                startActivity(i);
=======
                Intent intent = new Intent(MainActivity.this, ControlLogin.class);
                startActivity(intent);
>>>>>>> 92c821487245f06c1b1f311ee768b6025588ea0f:app/src/main/java/com/example/rusmart/MainActivity.java
                finish();

            }
        }, x);

    }
}
