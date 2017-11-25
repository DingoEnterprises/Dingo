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
    private String taskName;
    private String taskDescription;
    private String taskStatus; //enumeration: late, notStarted, complete, inProgess
    private String taskDueDate;

    public Task() {}

    //For the Database
    public Task(String id, String name, String description, String dueDate) {
        taskID = id;
        taskName = name;
        taskDescription = description;
        taskDueDate = dueDate;
    }

    //For the Users
    public Task(String name, String description, String dueDate) {
        taskName = name;
        taskDescription = description;
        taskDueDate = dueDate;
    }

    //Setters and Getters
    public void setId(String id) {taskID = id;}
    public String getId(){return taskID;}

    public void setTaskName(String name) {taskName = name;}
    public String getTaskName() {return taskName;}

    public void setTaskDescription(String description) {taskDescription = description;}
    public String getTaskDescription() {return taskDescription;}

    public void setTaskDueDate(String dueDate) {taskDueDate = dueDate;}
    public String getTaskDueDate(){return taskDueDate;}

    //TASK LIST





    //End

}
//End

