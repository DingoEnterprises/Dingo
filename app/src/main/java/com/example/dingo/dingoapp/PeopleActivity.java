package com.example.dingo.dingoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;

public class PeopleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        public void onItemClick(AdapterView.OnItemClickListener()){
            startActivity(new Intent(Users.this, Chat.class));
    }

    }
}
