package com.example.dingo.dingoapp;

/**
 * Created by Tair on 11/25/2017.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TaskList extends ArrayAdapter<Task> {
    private Activity context;
    private List<Task> tasks;

    public TaskList(Activity context, List<Task> tasks) {
        super(context, R.layout.fragment_current_task_list2, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_task_list, null, true);

        TextView textViewTitle = (TextView) listViewItem.findViewById(R.id.textViewTitle);
        TextView textViewDueDate = (TextView) listViewItem.findViewById(R.id.textViewDueDate);


        Task task = tasks.get(position);
        try {
            textViewTitle.setText(task.getTasktitle());
            textViewDueDate.setText(String.valueOf(task.getTaskduedate()));
        }catch (Exception e) {
            textViewTitle.setText("Working?");
            textViewDueDate.setText(String.valueOf("Maybe?"));
        }
        return listViewItem;
    }
}