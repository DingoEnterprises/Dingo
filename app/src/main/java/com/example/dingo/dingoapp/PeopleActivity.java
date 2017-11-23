package com.example.dingo.dingoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class PeopleActivity extends AppCompatActivity {

    ArrayList<String> array = new ArrayList<>();
    ListView usersList;
    TextView noUsersText;
    int numUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        String url = "https://dingo-eb8b3.firebaseio.com/";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
            },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue reqQ = Volley.newRequestQueue(PeopleActivity.this);
        reqQ.add(request);

        public void onItemClick(AdapterView parent, View view, int position, long id) {

            startActivity(new Intent(PeopleActivity.this, Chat.class));
    });
}
    public void doOnSuccess(String s){
        try{
            JSONObject obj = new JSONObject(s);
            Iterator i = obj.keys();
            String key = "";

            while(i.hasNext()){
                key = i.next().toString();
                if (!key.equals(UserInfo.user)){
                    array.add(key);
                }
                numUsers ++;
            }


        }catch (JSONException e){
            e.printStackTrace();
        }

        if (numUsers <= 1){
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }else{
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array));
        }

    }
}
