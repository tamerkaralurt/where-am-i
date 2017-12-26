package com.kutuphanelerim.uzman.whereami;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button btn_login, btn_stop, btn_register;
    private EditText txt_loginUsername, txt_loginPassword;
    private BroadcastReceiver broadcastReceiver;
    //private TextView txt_location;
    private String result = ""; //Silinecek.
    whereami_db DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB = new whereami_db(getApplicationContext());
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
        txt_loginUsername = (EditText) findViewById(R.id.txt_loginUsername);
        txt_loginPassword = (EditText) findViewById(R.id.txt_loginPassword);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txt_loginUsername.getText().toString().isEmpty() || txt_loginPassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "There is empty area!", Toast.LENGTH_LONG).show();
                }else{

                    String name = txt_loginUsername.getText().toString();
                    String password = txt_loginPassword.getText().toString();

                    Log.e("Degerler", name+" "+password);

                    if (DB.selectUser(name,password) == 1){
                        Intent mainPage = new Intent(MainActivity.this, MainPageActivity.class);
                        startActivity(mainPage);
                    }else if(DB.selectUser(name,password) == 2) {
                        Toast.makeText(getApplicationContext(), "User not found!", Toast.LENGTH_LONG).show();
                    }else if(DB.selectUser(name,password) == 0){
                        Toast.makeText(getApplicationContext(), "Password is wrong!", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });
    }
}
