package com.kutuphanelerim.uzman.whereami;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tamer on 20.12.2017.
 */

public class whereami_db extends SQLiteOpenHelper{

    public whereami_db(Context context){
        super(context, "whereami_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        String createUserTable = "CREATE TABLE users("
                +"id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"name TEXT NOT NULL,"
                +"password TEXT NOT NULL,"
                +"created_at TEXT NOT NULL,"
                +"updated_at TEXT NOT NULL)";
        DB.execSQL(createUserTable); //Veritabaninda users tablosu olusturuldu.

        String createCoordinatesTable = "CREATE TABLE coordinates("
                +"id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"user_id INTEGER NOT NULL,"
                +"longitude TEXT NOT NULL,"
                +"latitude TEXT NOT NULL,"
                +"created_at TEXT NOT NULL)";
        DB.execSQL(createCoordinatesTable); //Veritabaninda coordinates tablosu olusturuldu.
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int old_version, int new_version) {
        DB.execSQL("DROP TABLE if exists users");//users tablosunu sil.
}
}
