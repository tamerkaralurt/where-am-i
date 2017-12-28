package com.kutuphanelerim.uzman.whereami;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by tamer on 28.12.2017.
 */

public class GridAdapter extends BaseAdapter {

    private List<Coordinates> coordinatesList;
    private LayoutInflater layoutInflater;

    public GridAdapter(Activity activity, List<Coordinates> coordinates){
        layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         coordinatesList = coordinates;
    }

    @Override
    public int getCount() {
        return coordinatesList.size();
    }

    @Override
    public Object getItem(int i) {
        return coordinatesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View gridView;
        gridView = layoutInflater.inflate(R.layout.row, null);
        TextView textViewLocation = (TextView)gridView.findViewById(R.id.textViewLocation);
        TextView textViewHistory = (TextView)gridView.findViewById(R.id.textViewHistory);
        Coordinates coortinate = coordinatesList.get(i);
        textViewLocation.setText(coortinate.getLongitude().toString()+","+coortinate.getLatitude().toString());
        textViewHistory.setText(coortinate.getCreated_at().toString());
        return gridView;
    }

}
