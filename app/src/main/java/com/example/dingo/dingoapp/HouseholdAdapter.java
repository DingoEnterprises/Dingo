package com.example.dingo.dingoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ajrsa on 2017-12-06.
 */
//from last lab
public class HouseholdAdapter extends ArrayAdapter {
    private final Context context;
    private final String[] myHouseholds;
    public HouseholdAdapter(Context context, String[] householdList){
        super(context, R.layout.household_item_layout, householdList);
        this.context = context;
        this.myHouseholds = householdList;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.household_item_layout, parent, false);

        TextView choreNameTextField = (TextView) rowView.findViewById(R.id.householdName);
        TextView choreDescriptionTextField = (TextView) rowView.findViewById(R.id.householdDescription);
        //ImageView choreImage = (ImageView) rowView.findViewById(R.id.household_image);

        choreNameTextField.setText(myHouseholds[position]);
        choreDescriptionTextField.setText(position);


        return rowView;
    }
}
