package com.kutuphanelerim.uzman.whereami;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Users extends SQLiteOpenHelper {

    // Users Version
    private static final int DATABASE_VERSION = 1;

    // Users Name
    private static final String DATABASE_NAME = "db_whereami";//database adı

    private static final String TABLE_NAME = "Users";
    private static String ID = "id";
    private static String UserName = "username";
    private static String Password = "password";
    private static String Created_at = "created_at"; //2017-11-28:22:12:30
    private static String Updated_at = "updated_at";

    public Users(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {  // Databesi oluşturuyoruz.Bu methodu biz çağırmıyoruz. Users de obje oluşturduğumuzda otamatik çağırılıyor.
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + UserName + " TEXT,"
                + Password + " TEXT,"
                + Created_at + " TEXT,"
                + Updated_at + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    public void userDelete(int id){ //id si belli olan row u silmek için

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public void userAdd(String username, String password, String created_at,String updated_at) {
        //userEkle methodu ise adı üstünde Databese veri eklemek için
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserName, username);
        values.put(Password, password);
        values.put(Created_at, created_at);
        values.put(Updated_at, updated_at);

        db.insert(TABLE_NAME, null, values);
        db.close(); //Users Bağlantısını kapattık*/
    }

    public HashMap<String, String> userDetail(int id){
        //Databeseden id si belli olan row u çekmek için.
        //Bu methodda sadece tek row değerleri alınır.
        //HashMap bir çift boyutlu arraydir.anahtar-değer ikililerini bir arada tutmak için tasarlanmıştır.
        //map.put("x","300"); mesala burda anahtar x değeri 300.

        HashMap<String,String> user = new HashMap<String,String>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME+ " WHERE id="+id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.put(UserName, cursor.getString(1));
            user.put(Password, cursor.getString(2));
            user.put(Created_at, cursor.getString(3));
            user.put(Updated_at, cursor.getString(4));
        }
        cursor.close();
        db.close();
        return user;
    }

    public  ArrayList<HashMap<String, String>> users(){

        //Bu methodda ise tablodaki tüm değerleri alıyoruz
        //ArrayList adı üstünde Array lerin listelendiği bir Array.Burda hashmapleri listeleyeceğiz
        //Herbir satırı değer ve value ile hashmap a atıyoruz. Her bir satır 1 tane hashmap arrayı demek.
        //olusturdugumuz tüm hashmapleri ArrayList e atıp geri dönüyoruz(return).

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> userlist = new ArrayList<HashMap<String, String>>();
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for(int i=0; i<cursor.getColumnCount();i++)
                {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }

                userlist.add(map);
            } while (cursor.moveToNext());
        }
        db.close();
        return userlist;
    }

    public void userEdit(String username, String password, String created_at, String updated_at, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Bu methodda ise var olan veriyi güncelliyoruz(update)
        ContentValues values = new ContentValues();
        values.put(UserName, username);
        values.put(Password, password);
        values.put(Created_at, created_at);
        values.put(Updated_at, updated_at);

        // updating row
        db.update(TABLE_NAME, values, ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

}