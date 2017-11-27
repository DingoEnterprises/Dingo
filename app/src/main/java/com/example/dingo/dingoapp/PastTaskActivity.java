package com.example.dingo.dingoapp;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/**
 * Created by Tair on 11/26/2017.
 */

public class PastTaskActivity extends AppCompatActivity {
    EditText editTextTitle;
    EditText editTextDueDate;
    Button buttonAddTask;
    ListView listViewTasks;
    DatabaseReference databaseTasks;
    List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_past_task_list);
        databaseTasks = FirebaseDatabase.getInstance().getReference("tasks");
        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextDueDate = (EditText) findViewById(R.id.editTextDueDate);
        listViewTasks = (ListView) findViewById(R.id.listViewTasks);
        buttonAddTask = (Button) findViewById(R.id.addTaskButton);

        tasks = new ArrayList<>();

        //adding an onclicklistener to button
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });

        listViewTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task task = tasks.get(i);
                showUpdateDeleteDialog(task.getId(), task.getTaskTitle());
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
                TaskList tasksAdapter = new TaskList(PastTaskActivity.this, tasks);
                listViewTasks.setAdapter(tasksAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void showUpdateDeleteDialog(final String taskId, String taskTitle) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_task_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextTitle = (EditText) dialogView.findViewById(R.id.editTextTitle);
        final EditText editTextDueDate  = (EditText) dialogView.findViewById(R.id.editTextDueDate);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonConfirm);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);

        dialogBuilder.setTitle(taskTitle);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTextTitle.getText().toString().trim();
                String dueDate = new String();
                dueDate = String.valueOf(editTextDueDate.getText().toString());
                if (!TextUtils.isEmpty(title)) {
                    updateTask(taskId, title, dueDate);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTask(taskId);
                b.dismiss();
            }
        });
    }

    private void updateTask(String id, String title, String dueDate) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("tasks").child(id);
        Task task = new Task(id, title, dueDate);
        dR.setValue(task);

        Toast.makeText(getApplicationContext(), "Tasks Updated", Toast.LENGTH_LONG).show();
    }

    private void deleteTask(String id) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("tasks").child(id);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Task Deleted", Toast.LENGTH_LONG).show();
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
