package com.example.dingo.dingoapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class Task {
    private String taskID;
    private String taskTitle;
    private String taskDescription;
    private String[] taskStatus; //enumeration: late, notStarted, complete, inProgess
    private int taskStatusSel;
    private String taskDueDate;

    public Task() {
    }
    //For the Database
    public Task(String id, String title, String description, String dueDate, int statusSel) {
        taskID = id;
        taskTitle = title;
        taskDescription = description;
        taskDueDate = dueDate;
        taskStatus = new String[4];
        taskStatus[0] = "Not Started";
        taskStatus[1] = "In Progress";
        taskStatus[2] = "Complete";
        taskStatus[3] = "Late";
        taskStatusSel = statusSel;

    }

    //For the Users
    public Task(String title, String description, String dueDate) {
        taskTitle = title;
        taskDescription = description;
        taskDueDate = dueDate;
    }

    //Setters and Getters
    public void setId(String id) {taskID = id;}
    public String getId(){return taskID;}

    public void setTaskTitle(String title) {taskTitle = title;}
    public String getTaskTitle() {return taskTitle;}

    public void setTaskDescription(String description) {taskDescription = description;}
    public String getTaskDescription() {return taskDescription;}
    public String getTaskStatus(){return taskStatus[taskStatusSel];}
    public void setTaskDueDate(String dueDate) {taskDueDate = dueDate;}
    public String getTaskDueDate(){return taskDueDate;}



}
//End

