package com.example.dingo.dingoapp;

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
    FloatingActionButton buttonAddTask;
    ListView listViewTasks;
    DatabaseReference databaseTasks;
    List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_current_task_list2);
        databaseTasks = FirebaseDatabase.getInstance().getReference("tasks");
        listViewTasks = (ListView) findViewById(R.id.listViewTasks);
        buttonAddTask = (FloatingActionButton)findViewById(R.id.addTaskButton);
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createTask();

            }
        });
        tasks = new ArrayList<>();

        //adding an onclicklistener to button


        listViewTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task task = tasks.get(i);
                viewTask(task.getId(),task.getTaskTitle());
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
    private void viewTask(final String taskId, String taskTitle) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.view_task_dialog, null);
        dialogBuilder.setView(dialogView);

        final TextView viewTextTitle = (TextView) dialogView.findViewById(R.id.textViewTitle);
        final TextView viewTextDueDate  = (TextView) dialogView.findViewById(R.id.textViewDueDate);
        final TextView viewTextStatus = (TextView) dialogView.findViewById(R.id.textViewStatus);
        final TextView viewTextDescription = (TextView) dialogView.findViewById(R.id.textViewDescription);
        final ImageButton buttonEdit = (ImageButton) dialogView.findViewById(R.id.buttonEdit);
        final ImageButton buttonRemove = (ImageButton) dialogView.findViewById(R.id.buttonRemove);
        dialogBuilder.setTitle("Task");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = viewTextTitle.getText().toString().trim();
                String dueDate = viewTextDueDate.getText().toString().trim();
                String status = viewTextStatus.getText().toString().trim();
                String description = viewTextDescription.getText().toString().trim();
                updateTask(taskId,title, dueDate, status, description);
                b.dismiss();

            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(taskId);
                b.dismiss();
            }
        });
    }
    private void updateTask(final String id, String title, final String dueDate, String status, final String description) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_task_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText viewTextTitle = (EditText) dialogView.findViewById(R.id.textViewTitle);
        final EditText viewTextDueDate  = (EditText) dialogView.findViewById(R.id.textViewDueDate);
        final EditText viewTextStatus = (EditText) dialogView.findViewById(R.id.textViewStatus);
        final EditText viewTextDescription = (EditText) dialogView.findViewById(R.id.textViewDescription);
        final ImageButton buttonUpdate = (ImageButton) dialogView.findViewById(R.id.buttonConfirm);
        final ImageButton buttonDelete = (ImageButton) dialogView.findViewById(R.id.buttonDelete);
        final Spinner spinnerStatus = (Spinner) dialogView.findViewById(R.id.spinnerStatus);

        dialogBuilder.setTitle("Update Task");
        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = viewTextTitle.getText().toString().trim();
                String dueDate = viewTextDueDate.getText().toString().trim();
                String description = viewTextDescription.getText().toString().trim();
                String status = spinnerStatus.getSelectedItem().toString().trim();
                String[] taskStatus = new String[4];
                taskStatus[0] = "Not Started";
                taskStatus[1] = "In Progress";
                taskStatus[2] = "Complete";
                taskStatus[3] = "Late";
                int statusSel = 0;
                for(int i=0; i<4; i++) {
                    if(taskStatus[i] == status) {
                        statusSel = i;
                    }
                }
                if (!TextUtils.isEmpty(title)) {
                    update(id, title, dueDate, description, statusSel);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(id);
                b.dismiss();
            }
        });
    }

    private void createTask() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.create_task_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText viewTextTitle = (EditText) dialogView.findViewById(R.id.textViewTitle);
        final EditText viewTextDueDate  = (EditText) dialogView.findViewById(R.id.textViewDueDate);
        final Spinner spinnerStatus = (Spinner) dialogView.findViewById(R.id.spinnerStatus);
        final EditText viewTextDescription = (EditText) dialogView.findViewById(R.id.textViewDescription);
        final ImageButton buttonCreate = (ImageButton) dialogView.findViewById(R.id.buttonConfirm);
        final ImageButton buttonDelete = (ImageButton) dialogView.findViewById(R.id.buttonDelete);
        dialogBuilder.setTitle("Create Task");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = viewTextTitle.getText().toString().trim();
                String dueDate = viewTextDueDate.getText().toString().trim();
                String status = spinnerStatus.getSelectedItem().toString();
                String description = viewTextDescription.getText().toString().trim();
                if (!TextUtils.isEmpty(title)) {
                    String id = databaseTasks.push().getKey();
                    Task task = new Task(id, title, description, dueDate, 1);
                    databaseTasks.child(id).setValue(task);
                    b.dismiss();
                   // Toast.makeText(this, "Product added", Toast.LENGTH_LONG).show();
                }else {
                    //Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
                }


            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });

    }
    private void delete(String id) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("tasks").child(id);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Task Deleted", Toast.LENGTH_LONG).show();

    }
    private void update(String id, String title, String dueDate, String description, int statusSel) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("products").child(id);
        Task task = new Task(id, title, description, dueDate, statusSel);
        dR.setValue(task);

        Toast.makeText(getApplicationContext(), "Products Updated", Toast.LENGTH_LONG).show();
    }


}
