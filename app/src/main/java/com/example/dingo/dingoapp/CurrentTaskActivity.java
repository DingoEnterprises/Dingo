package com.example.dingo.dingoapp;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

/**
 * Created by Tair on 11/26/2017.
 */

public class CurrentTaskActivity extends AppCompatActivity {

    ListView listViewTasks;
    DatabaseReference databaseTasks;
    List<Task> tasks;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_current_task_list2);
        databaseTasks = FirebaseDatabase.getInstance().getReference("tasks");
        listViewTasks = (ListView) findViewById(R.id.listViewTasks);

        tasks = new ArrayList<>();

        //adding an onclicklistener to button


        listViewTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task task = tasks.get(i);
                MainTaskActivity taskActivity = new MainTaskActivity();
                taskActivity.viewTask(task.getId(),task.getTaskTitle());
                return true;
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        databaseTasks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tasks.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Task task  = postSnapshot.getValue(Task.class);
                    tasks.add(task);
                }
                TaskList tasksAdapter = new TaskList(CurrentTaskActivity.this, tasks);
                listViewTasks.setAdapter(tasksAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
