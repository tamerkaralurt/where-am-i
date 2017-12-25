package com.kutuphanelerim.uzman.whereami;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.List;

public class MainPageActivity extends ListActivity {

    ArrayAdapter<Coordinates> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        setListAdapter(arrayAdapter);
        //arrayAdapter.add(coordinatesList); bu kod da canli olarak koordinat ekleyebiliriz.
    }
}
