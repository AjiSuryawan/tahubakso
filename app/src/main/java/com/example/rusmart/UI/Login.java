package com.example.rusmart.UI;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.rusmart.Model.UserModel;
import com.example.rusmart.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    EditText txtusername;
    EditText txtpassword;
    Button btnlogin;
    private ProgressDialog progressBar;

    SharedPreferences mLogin;

    String fileName = "rusapi" + ".txt";//like 2016_01_12.txt
    File gpxfile;
    File root;


    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("weleh", "Permission is granted");
                return true;
            } else {

                Log.v("weleh", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("weleh", "Permission is granted");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v("weleh", "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission

            try {
                if (!root.exists()) {
                    root.mkdirs();
                    gpxfile = new File(root, fileName);

                    FileWriter writer = new FileWriter(gpxfile, false);
                    writer.append("http://192.168.6.60/");
                    writer.flush();
                    writer.close();
                }
            } catch (IOException e) {
                System.out.println("error tulis : " + e.toString());
            }


            //
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
            } catch (IOException e) {
                System.out.println("error baca : " + e.toString());
                //You'll need to add proper error handling here
            }
            System.out.println("hasil test login : " + text);
            String[] tmparray = text.toString().split(Pattern.quote("#"));
            baseURL.baseurl = tmparray[0].trim();
            System.out.println("hasil : " + tmparray[0]);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        root = new File(Environment.getExternalStorageDirectory() + File.separator + "rusapp", "folderku");
        if (Build.VERSION.SDK_INT >= 23) {
            //do your check here
            isStoragePermissionGranted();

            System.out.println("masuk if");
        }else{
            System.out.println("masuk else");
            root = new File(Environment.getExternalStorageDirectory() + File.separator + "rusapp", "folderku");
            try {
                if (!root.exists()) {
                    root.mkdirs();
                    gpxfile = new File(root, fileName);

                    FileWriter writer = new FileWriter(gpxfile, false);
                    writer.append("http://192.168.6.60/");
                    writer.flush();
                    writer.close();
                }
            } catch (IOException e) {
                System.out.println("error tulis : " + e.toString());
            }
            //baca
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
                Toast.makeText(getApplicationContext(),tmparray[0].trim().toString(),Toast.LENGTH_LONG).show();
                baseURL.baseurl = tmparray[0].trim();
            } catch (IOException e) {
                System.out.println("error baca : " + e.toString());
                //You'll need to add proper error handling here
            }
        }

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
                Log.d("makan", "onClick: "+baseURL.baseurl + "rusmart/api/login.php");
                AndroidNetworking.post(baseURL.baseurl + "rusmart/api/login.php")
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
