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
    List<Task> tasks;

    public TaskList(Activity context, List<Task> tasks) {
        super(context, R.layout.fragment_current_task_list2, tasks);
        this.context = context;
        this.currentTasks = tasks;
    }

    public TaskList(Activity context, List<Task> tasks) {
        super(context, R.layout.layout_current_task_list, tasks);
        this.context = context;
        this.pastTasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_product_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewDescription = (TextView) listViewItem.findViewById(R.id.textViewDescription);
        TextView textViewDueDate = (TextView) listViewItem.findViewById(R.id.textViewDueDate);

        Task task = tasks.get(position);
        textViewName.setText(task.getTaskName());
        textViewDescription.setText(task.getTaskDecription());
        textViewDueDate.setText(task.getTaskDueDate());
        return listViewItem;
    }
}


