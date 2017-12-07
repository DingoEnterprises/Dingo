package com.example.dingo.dingoapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainTaskActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener{
    FloatingActionButton addTaskButton;
    DatabaseReference databaseTasks;
    TaskList taskAdapter;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    GoogleSignInAccount acct;


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

        drawerLayout = (DrawerLayout) findViewById(R.id.main_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_bar);
        navigationView.setNavigationItemSelectedListener(this);


        databaseTasks = FirebaseDatabase.getInstance().getReference("tasks");
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Current"));
        tabLayout.addTab(tabLayout.newTab().setText("Past"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        //GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this); //moved to class UserInfo
        /*if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
        }
        */

        final FixedTabsPagerAdapter adapter = new FixedTabsPagerAdapter
                (getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        mAuth = FirebaseAuth.getInstance();
        mAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //If no account then go back to login screen
                if (firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(MainTaskActivity.this, LoginActivity.class));
                }
            }
        };
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
        final TextView viewCreatedBy = (TextView) dialogView.findViewById(R.id.viewCreatedBy2);
        final TextView viewTextTitle = (TextView) dialogView.findViewById(R.id.viewTitle);
        final TextView viewTextDueDate  = (TextView) dialogView.findViewById(R.id.viewDueDate);
        final TextView viewTextStatus = (TextView) dialogView.findViewById(R.id.viewStatus);
        final TextView viewTextDescription = (TextView) dialogView.findViewById(R.id.viewDescription);
        final ImageView viewImageAssignee = (ImageView) dialogView.findViewById(R.id.imageViewAssignee);
        viewCreatedBy.setText(task.getTaskcreatedby());
        viewTextTitle.setText(task.getTasktitle());
        viewTextDueDate.setText(task.getTaskduedate());
        viewTextDescription.setText(task.getTaskdescription());

        viewImageAssignee.setImageResource(R.drawable.a1);

        //String assigneeImage = assignee.getProfilePicId();
        //viewImageAssignee.setImageResource(R.drawable.assigneeImage); //?

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
                updateTask(passOnTask,context);
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

    private void updateTask(final MyTask task, Context context) {

        //if (user.getIsAdmin() == true) {


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
        final Spinner spinnerAssignee = (Spinner) dialogView.findViewById(R.id.spinnerAssignee);
        final ImageView imageAssignee = (ImageView) dialogView.findViewById(R.id.imageViewAssignee);
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

        //else {
        //    Toast.makeText(this, "You must be an admin to edit this task", Toast.LENGTH_LONG).show();

        //}
    }


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
                String email = "sampleimail@gmail.com";
                String createdBy = acct.getDisplayName();
                if (!TextUtils.isEmpty(title)) {
                    String id = databaseTasks.push().getKey();

                    MyTask task = new MyTask(id, title, description, dueDate, statussel,createdBy, email); //add email

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
    @Override
    protected  void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

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

