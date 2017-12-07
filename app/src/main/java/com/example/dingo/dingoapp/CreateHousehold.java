package com.example.dingo.dingoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateHousehold extends AppCompatActivity {
    Button button;
    DatabaseReference householdReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        householdReference = FirebaseDatabase.getInstance().getReference("households");
        button = (Button) findViewById(R.id.create_household_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ((EditText) findViewById(R.id.household_name_input)).toString();
                Household household = new Household(name);
                String id = householdReference.push().getKey();
                householdReference.child(id).setValue(household);
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_household);
    }


}
