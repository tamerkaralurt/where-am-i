package com.kutuphanelerim.uzman.whereami;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    @Override
    protected void onResume() {
        super.onResume();
        if (broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    //Veritabanına kayıt edilecek.

                    //txt_location.append("\n" + intent.getExtras().get("coordinates"));

                    try
                    {
                        Calendar calender = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String formattedDate = df.format(calender.getTime());
                        result += intent.getExtras().get("coordinates") + " " + formattedDate.toString()+ "\n";
                        File myFile = new File("/storage/emulated/0/locations.txt");

                        if(!myFile.exists())
                        {
                            myFile.createNewFile();
                        }

                        FileOutputStream fOut = new FileOutputStream(myFile);
                        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                        myOutWriter.append(result);
                        myOutWriter.close();
                        Log.e("Basarili", "Location Oluşturuldu.");
                        fOut.close();
                    }
                    catch (Exception e)
                    {
                        Log.e("error", e.getMessage());
                    }


                }
            };
        }
        registerReceiver(broadcastReceiver, new IntentFilter("location_update"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
        txt_loginUsername = (EditText) findViewById(R.id.txt_loginUsername);
        txt_loginPassword = (EditText) findViewById(R.id.txt_loginPassword);


        /*
        Silinecekler
         */
        //txt_location = (TextView) findViewById(R.id.textView12); //Silinecek Olan View
        //btn_stop = (Button) findViewById(R.id.btn_stop); //Silinecek Olan Button
        /*
        #Silinecekler
         */

        if(!runtime_permission())
            enable_buttons();
    }

    private void enable_buttons(){
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GPS_Service.class);
                startService(i);
                // Gelen kullanıcıyı sisteme giriş yaptıracaz.
            }
        });

//        btn_stop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), GPS_Service.class);
//                stopService(i);
//            }
//        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });
    }

    private boolean runtime_permission() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                enable_buttons();
            }else{
                runtime_permission();
            }
        }
    }
}
