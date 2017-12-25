package com.kutuphanelerim.uzman.whereami;

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

public class RegisterActivity extends AppCompatActivity {

    private Button btn_register,btn_backLogin;
    private EditText edit_username,edit_password,edit_password_confirm;
    private String result;
    whereami_db DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Genel Tanimlamalar
        btn_register =(Button)findViewById(R.id.btn_register);
        btn_backLogin =(Button)findViewById(R.id.btn_backLogin);
        edit_username =(EditText)findViewById(R.id.edit_username);
        edit_password =(EditText)findViewById(R.id.edit_password);
        edit_password_confirm=(EditText)findViewById(R.id.edit_password_confirm);
        DB = new whereami_db(getApplicationContext());
        //#Genel Tanimlamalar.

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (edit_password.getText().toString().equals(edit_password_confirm.getText().toString())){
                        try
                        {
                            //O anki tarih bilgisi aliniyor.
                            Calendar calender = Calendar.getInstance();
                            SimpleDateFormat df =
                                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String formattedDate = df.format(calender.getTime());
                            //#O anki tarih bilgisi aliniyor.
                            /* Txt Dosyasina Ekleme */
                            result = edit_username.getText().toString()
                                    + " " + edit_password.getText().toString()
                                    + " " + formattedDate.toString()
                                    + " " + formattedDate.toString();
                            File myFile = new File("/storage/emulated/0/tbl_users.txt");
                            if(!myFile.exists())
                            {
                                myFile.createNewFile();
                            }
                            FileOutputStream fOut = new FileOutputStream(myFile);
                            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                            myOutWriter.append(result);
                            myOutWriter.close();
                            fOut.close();
                            /* #Txt Dosyasina Ekleme */
                            //Veritabanina Ekleme Islemleri Yapiliyor.
                            UsersModel user = new UsersModel(edit_username.toString(),edit_password.toString(),formattedDate.toString(),formattedDate.toString()); //UserModel classindan bir nesne tanimladik ve gelen verileri bu nesneye gonderdik.
                            long id = DB.createUser(user); //Gelen verileri veritabanina gonderiyoruz ve kayit olan uyenin id degerini geri donderiyor.
                            if (id > 0){ //Kullanici kayit edilmisse.
                                Log.e("Basarili", "Kullanıcı Oluşturuldu.");
                                Toast.makeText(getApplicationContext(), "User Created! ID= "+id, Toast.LENGTH_LONG).show();
                            }else{
                                Log.e("Basarili", "Kullanıcı Oluşturulamadi.");
                                Toast.makeText(getApplicationContext(), "User Not Created!", Toast.LENGTH_LONG).show();
                            }
                            //#Veritabanina Ekleme Islemleri Yapiliyor
                        }
                        catch (Exception e){
                            Log.e("error", e.getMessage());
                        }
                    }else{
                        Log.e("Sifreler", "Şifreler Uyuşmadı.");
                        Toast.makeText(getApplicationContext(), "Passwords Do Not Match!", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Log.e("error", e.getMessage());
                }
            }
        });

        btn_backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); //Login ekranina geri doneriyor.
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //DB.openDB();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //DB.closeDB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //DB.closeDB();
    }
}
