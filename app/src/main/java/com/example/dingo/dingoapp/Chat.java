package com.example.dingo.dingoapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Chat extends AppCompatActivity {

    LinearLayout layout;
    RelativeLayout rlayout;
    ImageView sendButton;
    EditText msg;
    ScrollView sView;
    Firebase f1, f2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        layout = (LinearLayout) findViewById(R.id.layout1);
        rlayout = (RelativeLayout) findViewById(R.id.layout2);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        msg = (EditText)findViewById(R.id.messageArea);
        sView = (ScrollView)findViewById(R.id.scroll);

        Firebase.setAndroidContext(this);
        f1 = new Firebase ("https://dingo-eb8b3.firebaseio.com/"+ UserInfo.user);
        f2 = new Firebase("https://dingo-eb8b3.firebaseio.com/"+UserInfo.user);

        sendButton.setOnClickListener(new View.OnClickListener(){
            String msgText = msg.getText().toString();
            public void onClick(View v) {
                if (!msgText.equals("")) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", msgText);
                    map.put("user", UserInfo.user);
                    f1.push().setValue(map);
                    f2.push().setValue(map);
                    msg.setText("");
                }
            }
        });

        f1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String msgT = map.get("message").toString();
                String user = map.get("user").toString();

                if(user.equals(UserInfo.user)){
                    addMessageBox(msgT,1);
                }else{
                    addMessageBox(UserInfo.chatWith + msgT,2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void addMessageBox(String msg, int type){
        TextView txtView = new TextView(Chat.this);
        txtView.setText(msg);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if(type == 1) {
            lp2.gravity = Gravity.LEFT;
           // txtView.setBackgroundResource(R.drawable.bluebox);
        }
        else{
            lp2.gravity = Gravity.RIGHT;
            //txtView.setBackgroundResource(R.drawable.redbox);
        }
        txtView.setLayoutParams(lp2);
        layout.addView(txtView);
        sView.fullScroll(View.FOCUS_DOWN);
    }
    }

