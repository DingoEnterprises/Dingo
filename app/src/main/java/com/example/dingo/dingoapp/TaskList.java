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
        View listViewItem = inflater.inflate(R.layout.fragment_current_task_list2, null, true);

        TextView textViewTitle = (TextView) listViewItem.findViewById(R.id.textViewTitle);
        TextView textViewDueDate = (TextView) listViewItem.findViewById(R.id.textViewDueDate);
        TextView textViewStatus = (TextView) listViewItem.findViewById(R.id.textViewStatus);
        TextView textViewDescription = (TextView) listViewItem.findViewById(R.id.textViewDescription);

        Task task = tasks.get(position);
        textViewTitle.setText(task.getTaskTitle());
        textViewDueDate.setText(String.valueOf(task.getTaskDueDate()));
        textViewStatus.setText(task.getTaskStatus());
        textViewDescription.setText(task.getTaskDescription());
        return listViewItem;
    }
}