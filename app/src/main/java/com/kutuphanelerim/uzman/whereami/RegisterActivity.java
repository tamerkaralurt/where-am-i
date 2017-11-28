package com.kutuphanelerim.uzman.whereami;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private Button btn_register;
    private EditText edit_username,edit_password,edit_password_confirm;

    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_register =(Button)findViewById(R.id.btn_register);
        edit_username =(EditText)findViewById(R.id.edit_username);
        edit_password =(EditText)findViewById(R.id.edit_password);
        edit_password_confirm=(EditText)findViewById(R.id.edit_password_confirm);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Users user = new Users(getApplicationContext());
                try {
                    if (edit_password.getText().toString().equals(edit_password_confirm.getText().toString())){
                        try
                        {
                            Calendar calender = Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String formattedDate = df.format(calender.getTime());
                            result = edit_username.getText().toString() + " " + edit_password.getText().toString() + " " + formattedDate.toString()+ " " + formattedDate.toString();
                            File myFile = new File("/storage/emulated/0/data.txt");

                            if(!myFile.exists())
                            {
                                myFile.createNewFile();
                            }

                            FileOutputStream fOut = new FileOutputStream(myFile);
                            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                            myOutWriter.append(result);
                            myOutWriter.close();
                            Log.e("Basarili", "Kullanıcı Oluşturuldu.");
                            fOut.close();
                        }
                        catch (Exception e)
                        {
                            Log.e("error", e.getMessage());
                        }
                        //user.userAdd(edit_username.toString(),edit_password.toString(),formattedDate.toString(),formattedDate.toString());
                    }else{
                        Log.e("Sifreler", "Şifreler Uyuşmadı.");
                    }
                }catch (Exception e)
                {
                    Log.e("error", e.getMessage());
                }
            }
        });

    }
}
