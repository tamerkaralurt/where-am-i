package com.kutuphanelerim.uzman.whereami;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tamer on 20.12.2017.
 */

public class whereami_db extends SQLiteOpenHelper{

    private static final String databaseName = "whereami_db";
    private static final String usersTable = "tbl_users";
    private static final String coordinatesTable = "tbl_coordinates";
    private static final int databaseVersion = 3;


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

    public long createCoordinate(Coordinates coordinate) {
        try {
            SQLiteDatabase DB = this.getWritableDatabase();
            if (DB.isOpen()){
                ContentValues CV = new ContentValues();
                CV.put("user_id", coordinate.getUser_id());
                CV.put("longitude", coordinate.getLongitude());
                CV.put("latitude", coordinate.getLatitude());
                CV.put("created_at", coordinate.getCreated_at());
                long id = DB.insert(coordinatesTable, null, CV);
                return id;
            }else{
                Log.e("error", "Veritabani Acilamadi");
            }
        }catch (Exception e){
            Log.e("error", e.getMessage());
        }
        return 0;
    }

    public int selectUser(String name, String password) {
        try{
            SQLiteDatabase DB = this.getReadableDatabase();
            if (DB.isOpen()){
                String sqlQuery = "SELECT * FROM " + usersTable;
                Cursor cursor = DB.rawQuery(sqlQuery,null);
                try {
                    while (cursor.moveToNext()){
                        if(name == cursor.getString(1)){
                            if (password == cursor.getString(2)){
                                return 1;
                            }else{
                                Log.e("error", "Sifre Yanlis!");
                                Log.e("sonuc", cursor.getString(0));
                                return 0;
                            }
                        }else{
                            Log.e("error", "Boyle bir Uye Sistemde Yok!");
                            Log.e("sonuc", cursor.getString(0));
                            Log.e("sonuc", cursor.getString(1));
                            Log.e("sonuc", cursor.getString(2));
                            Log.e("sonuc", cursor.getString(3));
                            return 2;
                        }
                    }
                }catch (Exception e){
                    Log.e("error", e.getMessage());
                }finally {
                    cursor.close();
                    DB.close();
                }

            }else{
                Log.e("error", "Veritabani Acilamadi");
            }
        }catch (Exception e){
            Log.e("error", e.getMessage());
        }
        return 0;
    }

    public List<Coordinates> getCoordinatList() {
        List<Coordinates> coordinatList = new ArrayList<Coordinates>();
        SQLiteDatabase DB = this.getReadableDatabase();
        String sqlQuery = "SELECT * FROM " + coordinatesTable;
        Cursor cursor = DB.rawQuery(sqlQuery,null);
        int rowID = cursor.getColumnIndex("id");
        int rowUserId = cursor.getColumnIndex("user_id");
        int rowLongitude = cursor.getColumnIndex("longitude");
        int rowLatitude = cursor.getColumnIndex("latitude");
        int rowCreatedAt = cursor.getColumnIndex("created_at");

        try {
            while (cursor.moveToNext()){
                Coordinates coordinates = new Coordinates(cursor.getInt(rowUserId),cursor.getString(rowLongitude),cursor.getString(rowLatitude),cursor.getString(rowCreatedAt));
                coordinatList.add(coordinates);
            }
        }catch (Exception e){
            Log.e("error", e.getMessage());
        }finally {
            cursor.close();
            DB.close();
        }
        return coordinatList;
    }
}
