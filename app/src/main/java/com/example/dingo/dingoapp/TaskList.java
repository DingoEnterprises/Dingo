package com.example.dingo.dingoapp;

/**
 * Created by Tair on 11/25/2017.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TaskList extends ArrayAdapter<MyTask> {
    private Activity context;
    private List<MyTask> tasks;

    public TaskList(Activity context, List<MyTask> tasks) {
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
        TextView textViewStatus = (TextView) listViewItem.findViewById(R.id.textViewStatus);
        ImageView imageViewAssignee = (ImageView) listViewItem.findViewById(R.id.assigneeImage);


        MyTask task = tasks.get(position);
            textViewTitle.setText(task.getTasktitle());
            textViewDueDate.setText(String.valueOf(task.getTaskduedate()));
            textViewStatus.setText(task.gTaskstatus(task.getTaskstatussel()));
            imageViewAssignee.setImageResource(R.mipmap.ic_assignment_black_24dp);

        return listViewItem;
    }
}