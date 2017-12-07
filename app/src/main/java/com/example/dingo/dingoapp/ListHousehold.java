package com.example.dingo.dingoapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListHousehold extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    List<Household> households;
    ListView listView;
    String personEmail;
    DatabaseReference databaseHouseholds;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //If no account then go back to login screen
                if (firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(ListHousehold.this, LoginActivity.class));
                }
            }
        };
        drawerLayout = (DrawerLayout) findViewById(R.id.household_activity);
        navigationView = (NavigationView) findViewById(R.id.navigation_bar);
        navigationView.setNavigationItemSelectedListener(this);
        households = new ArrayList<>();
        databaseHouseholds = FirebaseDatabase.getInstance().getReference("households");

        setContentView(R.layout.activity_list_household);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {

            personEmail = acct.getEmail();

        }

        listView = (ListView) findViewById(R.id.list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Intent editorLaunchInterest = new Intent(getApplicationContext(), MainTaskActivity.class);
                editorLaunchInterest.putExtra("household",position);
                //editorLaunchInterest.putExtra("name",choreList[position]);
                startActivityForResult(editorLaunchInterest, 0);
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_household);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    //from product_catalog
    @Override
    protected void onStart() {

        super.onStart();
        databaseHouseholds.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                households.clear();
                for (DataSnapshot aSingleSnapshot : dataSnapshot.getChildren()){
                    Household household = aSingleSnapshot.getValue(Household.class);
                    if (household.hasUser(personEmail))
                        households.add(household);
                }
                String[] householdList = new String[households.size()];
                for (int i = 0; i<households.size(); i++){
                    householdList[i]=households.get(i).getName();
                }
                HouseholdAdapter adapter = new HouseholdAdapter(ListHousehold.this,householdList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.logout:
                mAuth.signOut();
            case R.id.nav_households:
                Intent editorLaunchInterest = new Intent(getApplicationContext(), ListHousehold.class);
                startActivityForResult(editorLaunchInterest, 0);


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }


}
