package com.example.dingo.dingoapp;

import android.app.Activity;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CurrentTaskListFragment extends Fragment {
    ListView listViewTasks;
    DatabaseReference databaseTasks;
    List<Task> tasks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_task_list2, container, false);
    }
}
    class UpdateTaskList extends Activity {
        ListView listViewTasks;
        DatabaseReference databaseTasks;
        List<Task> tasks;

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            databaseTasks = FirebaseDatabase.getInstance().getReference("tasks");
            tasks = new ArrayList<>();


            listViewTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Task task = tasks.get(i);
                    MainTaskActivity taskActivity = new MainTaskActivity();
                    taskActivity.viewTask(task.getId(), task.getTaskTitle());
                    return true;
                }
            });
            return inflater.inflate(R.layout.fragment_current_task_list2, container, false);
        }
        @Override
        public void onStart() {
            super.onStart();
            databaseTasks.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    tasks.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Task task  = postSnapshot.getValue(Task.class);
                        tasks.add(task);
                    }
                    TaskList tasksAdapter = new TaskList(UpdateTaskList.this, tasks);
                    listViewTasks.setAdapter(tasksAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
