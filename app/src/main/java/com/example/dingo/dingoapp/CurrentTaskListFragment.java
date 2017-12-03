package com.example.dingo.dingoapp;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CurrentTaskListFragment extends Fragment {
    MainTaskActivity taskActivity = new MainTaskActivity();
    List<MyTask> tasks;
    ListView listViewTasks;
    DatabaseReference databaseTasks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_current_task_list2, container, false);
        listViewTasks = (ListView) rootView.findViewById(R.id.listViewTasks);
        tasks = new ArrayList<>();

        databaseTasks = FirebaseDatabase.getInstance().getReference("tasks");
        databaseTasks.keepSynced(true);


        return rootView;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listViewTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                MyTask task = tasks.get(i);
                taskActivity.viewTask(task, getActivity());


                return true;
            }

            ;
        });
    }
    @Override
    public void onStart(){
        super.onStart();


        databaseTasks = FirebaseDatabase.getInstance().getReference("tasks");

        databaseTasks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tasks.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        MyTask task = postSnapshot.getValue(MyTask.class);
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


    }

}