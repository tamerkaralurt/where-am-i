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
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainPageActivity extends AppCompatActivity {

    private BroadcastReceiver broadcastReceiver;
    private String result = ""; //Silinecek.
    whereami_db DB;
    Coordinates coordinate;

    @Override
    protected void onResume() {
        super.onResume();
        if (broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
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
                        Log.e("Basarili", "Koordinat Txt'e Yazildi.");
                        fOut.close();

                        //Veritabanı Kayit Islemleri
                        coordinate = new Coordinates(1,intent.getExtras().get("longitude").toString(),intent.getExtras().get("latitude").toString(),formattedDate.toString());
                        long id = DB.createCoordinate(coordinate); //Gelen verileri veritabanina gonderiyoruz ve kayit olan uyenin id degerini geri donderiyor.
                        if (id > 0){ //Koordinat kayit edilmisse.
                            Log.e("Basarili", "Koordinat Oluşturuldu.");
                            Toast.makeText(getApplicationContext(), "Coordinate Created! ID= "+id, Toast.LENGTH_LONG).show();
                        }else{
                            Log.e("Basarisiz", "Koordinat Oluşturulamadi.");
                            Toast.makeText(getApplicationContext(), "Coordinate Not Created!", Toast.LENGTH_LONG).show();
                        }
                        //#Veritabani Kayit Islemleri
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
        setContentView(R.layout.activity_main_page);
        DB = new whereami_db(getApplicationContext());
        Intent i = new Intent(getApplicationContext(), GPS_Service.class);
        startService(i);
        if(!runtime_permission())
            enable_buttons();
    }

    private void enable_buttons(){
        Intent i = new Intent(getApplicationContext(), GPS_Service.class);
        startService(i);
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
