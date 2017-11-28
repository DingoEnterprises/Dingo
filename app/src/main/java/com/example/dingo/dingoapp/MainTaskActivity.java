package com.example.dingo.dingoapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class MainTaskActivity extends Activity {
    FloatingActionButton addTaskButton;
    ListView listViewTasks;
    DatabaseReference databaseTasks;
    List<Task> tasks;
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        databaseTasks = FirebaseDatabase.getInstance().getReference("tasks");
        addTaskButton = (FloatingActionButton) findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentTaskActivity taskActivity = new CurrentTaskActivity();
                createTask();
            }
        });
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Current"));
        tabLayout.addTab(tabLayout.newTab().setText("Past"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final FixedTabsPagerAdapter adapter = new FixedTabsPagerAdapter
                (getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
    public void viewTask(final String taskId, String taskTitle) {
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
        b.getWindow().setLayout(1080, 1080);
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
        b.getWindow().setLayout(1080, 1080);
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

    public void createTask() {

        System.out.println("Test1");
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        System.out.println("Test2");
        LayoutInflater inflater = getLayoutInflater();
        System.out.println("Test3");
        final View dialogView = inflater.inflate(R.layout.create_task_dialog, null);
        dialogBuilder.setView(dialogView);

        System.out.println("Test4");

        final EditText editTextTitle = (EditText) dialogView.findViewById(R.id.editTextTitle);
        final EditText editTextDueDate  = (EditText) dialogView.findViewById(R.id.editTextDueDate);
        final Spinner spinnerStatus = (Spinner) dialogView.findViewById(R.id.spinnerStatus);
        final EditText editTextDescription = (EditText) dialogView.findViewById(R.id.editTextDescription);

        dialogBuilder.setTitle("Create Task");
        final AlertDialog b = dialogBuilder.create();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        b.show();
        b.getWindow().setLayout(lp.width, lp.height);

        b.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTextTitle.getText().toString().trim();
                String dueDate = editTextDueDate.getText().toString().trim();
                String status = spinnerStatus.getSelectedItem().toString();
                String description = editTextDescription.getText().toString().trim();
                if (!TextUtils.isEmpty(title)) {
                    databaseTasks.push();
                    String id = databaseTasks.getKey();
                    Task task = new Task(id, title, description, dueDate, 1);
                    databaseTasks.child(id).setValue(task);
                    CurrentTaskActivity taskActivity = new CurrentTaskActivity();
                    taskActivity.onStart();
                    b.dismiss();
                    // Toast.makeText(this, "Product added", Toast.LENGTH_LONG).show();
                }else {
                    //Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
                }


            }
        });

        b.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
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