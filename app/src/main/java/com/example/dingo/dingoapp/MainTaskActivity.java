package com.example.dingo.dingoapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainTaskActivity extends Activity {
    FloatingActionButton addTaskButton;
    DatabaseReference databaseTasks;
    TaskList taskAdapter;
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);
        addTaskButton = (FloatingActionButton) findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTask();
            }
        });

        databaseTasks = FirebaseDatabase.getInstance().getReference("tasks");
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
    //public List<Task> getTasks() {
   //     return tasks;
   // }
    //public void addTasks(Task task) {
   //     tasks.add(task);
   // }
    //public void clearTasks() {
    //    tasks.clear{};
   // }

    public void viewTask(final MyTask task, final Context context) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context)
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setNeutralButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

        }
    });;

        System.out.println("Test2");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        System.out.println("Test3");
        final View dialogView = inflater.inflate(R.layout.view_task_dialog, null);
        dialogBuilder.setView(dialogView);

        final TextView viewTextTitle = (TextView) dialogView.findViewById(R.id.viewTitle);
        final TextView viewTextDueDate  = (TextView) dialogView.findViewById(R.id.viewDueDate);
        final TextView viewTextStatus = (TextView) dialogView.findViewById(R.id.viewStatus);
        final TextView viewTextDescription = (TextView) dialogView.findViewById(R.id.viewDescription);
        //final Spinner spinnerStatus = (Spinner) dialogView.findViewById(R.id.spinnerStatus);
        viewTextTitle.setText(task.getTasktitle());
        viewTextDueDate.setText(task.getTaskduedate());
        viewTextDescription.setText(task.getTaskdescription());
        String[] taskStatus = new String[4];
        taskStatus[0] = "Not Started";
        taskStatus[1] = "In Progress";
        taskStatus[2] = "Complete";
        taskStatus[3] = "Late";
        viewTextStatus.setText(taskStatus[task.getTaskstatussel()]);
        dialogBuilder.setTitle("Task");
        final AlertDialog b = dialogBuilder.create();
        b.getWindow().setLayout(1080, 1080);
        b.show();

        b.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyTask passOnTask = task;
                updateTask(passOnTask, context);
                b.dismiss();



            }
        });
        b.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyTask passOnTask = task;
                delete(passOnTask);
                b.dismiss();



            }
        });

    }
    private void updateTask(final MyTask task, Context context) { //need a class called User  , User user

       // if (user.isAdmin == true) {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context)
                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

            System.out.println("Test2");
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            System.out.println("Test3");
            final View dialogView = inflater.inflate(R.layout.update_task_dialog, null);
            dialogBuilder.setView(dialogView);

            final EditText editTextTitle = (EditText) dialogView.findViewById(R.id.editTextTitle);
            final EditText editTextDueDate = (EditText) dialogView.findViewById(R.id.editTextDueDate);
            final Spinner spinnerStatus = (Spinner) dialogView.findViewById(R.id.spinnerStatus);
            final EditText editTextDescription = (EditText) dialogView.findViewById(R.id.editTextDescription);
            //put spinner for assignee

            dialogBuilder.setTitle("Update Task");
            final AlertDialog b = dialogBuilder.create();
            b.getWindow().setLayout(1080, 1080);
            b.show();
            b.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyTask passOnTask = task;
                    passOnTask.setTasktitle(editTextTitle.getText().toString().trim());
                    passOnTask.setTaskduedate(editTextDueDate.getText().toString().trim());
                    passOnTask.sTaskstatus(spinnerStatus.getSelectedItemPosition());
                    passOnTask.setTaskdescription(editTextDescription.getText().toString().trim());
                    if (!TextUtils.isEmpty(editTextTitle.getText().toString().trim())) {
                        update(passOnTask);
                        b.dismiss();
                        // Toast.makeText(this, "Product added", Toast.LENGTH_LONG).show();
                    } else {
                        //Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
                    }


                }
            });
            b.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyTask passOnTask = task;
                    delete(passOnTask);
                    b.dismiss();


                }
            });

        }

       // else {
            //Toast.makeText(this, "You must be an admin to edit this task", Toast.LENGTH_LONG).show();
      //  }
   // }

    public void createTask() {

        System.out.println("Test1");
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
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
       // final ImageView profilePic = (ImageView)findViewById(R.id.profilePic);   ///added by Ash

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
                int statussel = spinnerStatus.getSelectedItemPosition();
                String description = editTextDescription.getText().toString().trim();
                if (!TextUtils.isEmpty(title)) {
                    String id = databaseTasks.push().getKey();
                    MyTask task = new MyTask(id, title, description, dueDate, statussel); //add email
                    databaseTasks.child(id).setValue(task);
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

    //method to be called by createTask and updateTask!!!
    public void setTaskAssigneeImage(View view) { //need to access the list of users to set the correct image
        Intent returnIntent = new Intent();
        ImageView selectedImage = (ImageView) view;
        returnIntent.putExtra("imageID", selectedImage.getId());
        setResult(RESULT_OK, returnIntent);
        finish();
    }
   /* @Override ///added by Anthony
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode == RESULT_CANCELED) return;
        ImageView assigneeImage = (ImageView) findViewById(R.id.assigneeImage); //assigneeImage exists in layout_task_list

        //ACCESS LIST_OF_IMAGES ACTIVITY and listen to for image on click
        ListView listViewUsers;
        listViewUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task task = tasks.get(assigneeimage);
                taskActivity.viewTask(task, getActivity());
                return true;
            }

        //Figuring out the correct image //WE NEED A LIST_OF_IMAGES XML ACTIVITY FOR THIS
        String drawableName = "defaultProfilePic";
        switch (data.getIntExtra("imageID,R.id.useridDefault")) {
            //NEED TO LIST CASES FOR EACH USER THAT EXISTS IN THE PROGRAM
            case R.id.useridDefault: //useridDefault should exist in LIST_OF_IMAGES XML ACTIVITY
                drawableName = "defaultProfilePic";
                break;
            //case R.id.userid001:
            //  drawableName = "user001ProfilePic";
            //  break;
            //case R.id.userid002:
            //  drawableName = "user002ProfilePic";
            //  break;
            //case R.id.userid003:
            //  drawableName = "user003ProfilePic";
            //  break;
        }
        int resultID = getResources().getIdentifier(drawableName, "drawable", getPackageName());
        assigneeImage.setImageResource(resultID);
    }*/

    private void delete(MyTask task) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("tasks").child(task.getTaskid());
        dR.removeValue();
        //Toast.makeText(getApplicationContext(), "Task Deleted", Toast.LENGTH_LONG).show();

    }
    private void update(MyTask task) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("tasks").child(task.getTaskid());
        dR.setValue(task);

        //Toast.makeText(getApplicationContext(), "Products Updated", Toast.LENGTH_LONG).show();
    }

    }

