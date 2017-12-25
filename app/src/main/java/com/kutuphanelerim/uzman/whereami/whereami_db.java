package com.kutuphanelerim.uzman.whereami;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by tamer on 20.12.2017.
 */

public class whereami_db extends SQLiteOpenHelper{

    private static final String databaseName = "whereami_db";
    private static final String usersTable = "tbl_users";
    private static final String coordinatesTable = "coordinates";
    private static final int databaseVersion = 2;


    public whereami_db(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        //Tablolar Olusturuluyor.
        String createUserTable = "CREATE TABLE "+usersTable+"(id integer PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, password TEXT NOT NULL, created_at TEXT NOT NULL, updated_at TEXT NOT NULL)";
        DB.execSQL(createUserTable); //Veritabaninda users tablosu olusturuldu.

        String createCoordinatesTable = "CREATE TABLE "+coordinatesTable+"(id integer PRIMARY KEY AUTOINCREMENT, user_id INTEGER NOT NULL, longitude TEXT NOT NULL, latitude TEXT NOT NULL, created_at TEXT NOT NULL)";
        DB.execSQL(createCoordinatesTable); //Veritabaninda coordinates tablosu olusturuldu.
        //#Tablolar Olusturuluyor.
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int old_version, int new_version) {
        DB.execSQL("DROP TABLE IF EXISTS " + usersTable);//users tablosunu sil.
        DB.execSQL("DROP TABLE IF EXISTS " + coordinatesTable);//coordinates tablosunu sil.
        onCreate(DB);
    }

    public long createUser(UsersModel user) {
        try {
            SQLiteDatabase DB = this.getWritableDatabase();
            if (DB.isOpen()){
                ContentValues CV = new ContentValues();
                CV.put("name", user.getName());
                CV.put("password", user.getPassword());
                CV.put("created_at", user.getCreated_at());
                CV.put("updated_at", user.getUpdated_at());
                long id = DB.insert(usersTable, null, CV);
                return id;
            }else{
                Log.e("error", "Veritabani Acilamadi");
            }
        }catch (Exception e){
            Log.e("error", e.getMessage());
        }
        return 0;
    }

}
