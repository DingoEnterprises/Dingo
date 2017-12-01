package com.example.dingo.dingoapp;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PastTaskListFragment extends Fragment {
    MainTaskActivity taskActivity = new MainTaskActivity();
    List<Task> tasks;
    ListView listViewTasks;
    DatabaseReference databaseTasks;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_past_task_list, container, false);
        listViewTasks = (ListView) rootView.findViewById(R.id.listViewPTasks);
        //taskActivity.tasks.add(new Task("Test", "testTitle", "TestDescription", "12", 0));
        // ArrayAdapter<Task> arrayAdapterTask = new ArrayAdapter<Task>(getActivity(),android.R.layout.simple_list_item_1, taskActivity.tasks);
        tasks = new ArrayList<>();

        databaseTasks = FirebaseDatabase.getInstance().getReference("tasks");
        databaseTasks.keepSynced(true);


        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();


        listViewTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task task = tasks.get(i);
                return true;
            };
        });
        databaseTasks = FirebaseDatabase.getInstance().getReference("tasks");

        databaseTasks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tasks.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Task task = postSnapshot.getValue(Task.class);
                    tasks.add(task);

                }
                TaskList taskAdapter = new TaskList(getActivity(), tasks);
                listViewTasks.setAdapter(taskAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        listViewTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task task = tasks.get(i);

                return true;
            }

            ;
        });
        databaseTasks = FirebaseDatabase.getInstance().getReference("tasks");

        databaseTasks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tasks.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Task task = postSnapshot.getValue(Task.class);
                    tasks.add(task);

                }
                TaskList taskAdapter = new TaskList(getActivity(), tasks);
                listViewTasks.setAdapter(taskAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
