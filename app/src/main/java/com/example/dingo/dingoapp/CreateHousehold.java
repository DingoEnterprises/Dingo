package com.example.dingo.dingoapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateHousehold extends AppCompatActivity{
    Button button;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    DatabaseReference householdReference;
    //FirebaseAuth.AuthStateListener mAuthListener;
    //FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mAuth = FirebaseAuth.getInstance();
        householdReference = FirebaseDatabase.getInstance().getReference("households");
        //drawerLayout = (DrawerLayout) findViewById(R.id.create_household_layout);
        //navigationView = (NavigationView) findViewById(R.id.navigation_bar);
        //navigationView.setNavigationItemSelectedListener(this);
        button = (Button) findViewById(R.id.create_household_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ((EditText) findViewById(R.id.household_name_input)).toString();
                Household household = new Household(name);
                String id = householdReference.push().getKey();
                householdReference.child(id).setValue(household);
                startActivity(new Intent(CreateHousehold.this,ListHousehold.class));

            }
        });
        setContentView(R.layout.activity_create_household);
        /*mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(CreateHousehold.this,ListHousehold.class));
                }
            }
        };*/
    }

   /* @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.logout:
                mAuth.signOut();
            case R.id.nav_households:
                Intent editorLaunchInterest = new Intent(getApplicationContext(), ListHousehold.class);
                startActivityForResult(editorLaunchInterest, 0);
            case

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }*/

}
