package com.example.rusmart.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.rusmart.Model.UserModel;
import com.example.rusmart.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    EditText txtusername;
    EditText txtpassword;
    Button btnlogin;
    private ProgressDialog progressBar;

    SharedPreferences mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final UserModel user = new UserModel();
        txtusername = (EditText) findViewById(R.id.txtusername);
        txtpassword = (EditText) findViewById(R.id.txtpassword);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //command here
                progressBar = new ProgressDialog(Login.this);
                progressBar.setMessage("Please wait");
                progressBar.show();
                progressBar.setCancelable(false);
                user.setUsername(txtusername.getText().toString());
                user.setPassword(txtusername.getText().toString());

                mLogin = getSharedPreferences("login", Context.MODE_PRIVATE);
                AndroidNetworking.post(baseURL.baseurl + "rusmart/login.php")
                        .addBodyParameter("username", txtusername.getText().toString())
                        .addBodyParameter("password", txtpassword.getText().toString())
                        .setTag("test")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("hasil", "onResponse: " + response);
                                try {
                                    JSONObject respon = response.getJSONObject("hasil");
                                    boolean sukses = respon.getBoolean("respon");
                                    if (sukses) {
                                        //simpan share pref, ke main menu

                                        SharedPreferences.Editor editor = mLogin.edit();
                                        editor.putString("username", txtusername.getText().toString());
                                        editor.putString("password", txtpassword.getText().toString());
                                        editor.apply();

                                        Toast.makeText(getApplicationContext(), "Login sukses", Toast.LENGTH_LONG).show();
                                        Intent in = new Intent(Login.this, HomeDashboard.class);
                                        startActivity(in);
                                        finish();
                                        if (progressBar.isShowing()) {
                                            progressBar.dismiss();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Login gagal", Toast.LENGTH_LONG).show();


                                    }
                                } catch (JSONException e) {
                                    if (progressBar.isShowing()) {
                                        progressBar.dismiss();
                                    }

                                }


                            }

                            @Override
                            public void onError(ANError anError) {
                                if (progressBar.isShowing()) {
                                    progressBar.dismiss();
                                }
                                System.out.println("lala4");
                                Log.d("errorku", "onError: " + anError.getErrorCode());
                                Log.d("errorku", "onError: " + anError.getErrorBody());
                                Log.d("errorku", "onError: " + anError.getErrorDetail());
                            }


                        });
            }

        });
    }

        @Override
        public void onBackPressed() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("Do you want to Exit?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
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
        }

}
