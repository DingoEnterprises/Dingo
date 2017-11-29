package com.example.dingo.dingoapp;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CurrentTaskListFragment extends Fragment {
    MainTaskActivity taskActivity = new MainTaskActivity();
    List<Task> tasks = new ArrayList<Task>();
    ListView listViewTasks;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_current_task_list2, container, false);
        listViewTasks = (ListView) rootView.findViewById(R.id.listViewTasks);
        tasks.add(new Task("Test", "testTitle", "TestDescription", "12", 0));
        ArrayAdapter<Task> arrayAdapterTask = new ArrayAdapter<Task>(getActivity(),android.R.layout.simple_list_item_1, tasks);
        TaskList taskAdapter = new TaskList(getActivity(), tasks);
        listViewTasks.setAdapter(taskAdapter);
        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();

        taskActivity.pullFromDatabase();
        listViewTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task task = taskActivity.getTasks().get(i);
                taskActivity = new MainTaskActivity();
                taskActivity.viewTask(task.getId(), task.getTaskTitle());
                return true;
            };
            });
        taskActivity.pullFromDatabase();
    }

    @Override
    public void onResume() {
        super.onResume();
        taskActivity.pullFromDatabase();
        listViewTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task task = taskActivity.getTasks().get(i);
                taskActivity = new MainTaskActivity();
                taskActivity.viewTask(task.getId(), task.getTaskTitle());
                return true;
            }

            ;
        });
        taskActivity.pullFromDatabase();

    }
}