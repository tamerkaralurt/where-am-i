package com.kutuphanelerim.uzman.whereami;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Tamer on 20.12.2017.
 */

public class veriKaynagi {

    SQLiteDatabase DB;
    whereami_db myDB;

    public veriKaynagi(Context context){
        myDB = new whereami_db(context);
    }

    public void openDB(){
        DB = myDB.getWritableDatabase();
    }

    public void closeDB(){
        myDB.close();
    }

    public void createUser(Users user){
        ContentValues users = new ContentValues(); //String veri yapisi olusturuldu.
        users.put("name",user.getName());
        users.put("password",user.getPassword());
        users.put("created_at",user.getCreated_at());
        users.put("updated_at",user.getUpdated_at());
        DB.insert("users",null,users); //Veritabanina user eklendi.
    }

    public void selectUser(Users user){
        String name="";
        Cursor cursor = DB.rawQuery("SELECT * FROM users WHERE TRIM(name) = '"+name.trim()+"'", null);
    }

    public void deleteUser(Users user){
        DB.delete("users", "id=" + user.getId(),null);
    }

    public void createCoordinate(){
        ContentValues coordinate = new ContentValues();
    }

    public List<Coordinates> selectCoordinate(){
        List<Coordinates> listCoordinates = new ArrayList<Coordinates>();
        Coordinates coordinates = new Coordinates();
        String columns[] = {"id","user_id","longitude","latitude","created_at"};
        Cursor cursor = DB.query("Coordinates", columns, null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            coordinates.setId(cursor.getInt(0));
            coordinates.setUser_id(cursor.getInt(1));
            coordinates.setLongitude(cursor.getString(2));
            coordinates.setLatitude(cursor.getString(3));
            coordinates.setCreated_at(cursor.getString(4));
            listCoordinates.add(coordinates);
        }
        return listCoordinates;
    }

}
