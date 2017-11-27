package com.example.dingo.dingoapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tair on 11/26/2017.
 */

public class CreateTask extends AppCompatActivity{
    EditText editTextTitle;
    EditText editTextDueDate;
    EditText editTextDescription;
    Spinner spinnerStatus;
    ImageButton buttonConfirm;
    ImageButton buttonDelete;
    DatabaseReference databaseTasks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_current_task_list2);
        databaseTasks = FirebaseDatabase.getInstance().getReference("tasks");
        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextDueDate = (EditText) findViewById(R.id.editTextDueDate);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        spinnerStatus = (Spinner) findViewById(R.id.spinnerStatus);
        buttonConfirm = (ImageButton) findViewById(R.id.buttonConfirm);
        buttonDelete = (ImageButton) findViewById(R.id.buttonDelete);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
                finish();
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        //adding an onclicklistener to button

    }

    private void addTask() {
        String title = editTextTitle.getText().toString().trim();
        String dueDate  = String.valueOf(editTextDueDate.getText().toString());
        if (!TextUtils.isEmpty(title)) {
            String id = databaseTasks.push().getKey();
            Task task = new Task(id, title, dueDate);
            databaseTasks.child(id).setValue(task);
            editTextTitle.setText("");
            editTextDueDate.setText("");
            Toast.makeText(this, "Task added", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }

}


