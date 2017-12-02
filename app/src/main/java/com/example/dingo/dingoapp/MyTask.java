package com.example.dingo.dingoapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MyTask {
    private String taskid;
    private String tasktitle;
    private String taskdescription;
    private String[] taskstatus; //enumeration: late, notStarted, complete, inProgess
    private int taskstatussel;
    private String taskduedate;

    public MyTask() {
    }
    //For the Database
    public MyTask(String id, String title, String description, String duedate, int statussel) {
        taskid = id;
        tasktitle = title;
        taskdescription = description;
        taskduedate = duedate;
        taskstatus = new String[4];
        taskstatus[0] = "Not Started";
        taskstatus[1] = "In Progress";
        taskstatus[2] = "Complete";
        taskstatus[3] = "Late";
        taskstatussel = statussel;

    }


    //Setters and Getters
    public void setTaskid(String id) {taskid = id;}
    public String getTaskid() {return taskid;}
    public void setTasktitle(String title) {tasktitle = title;}
    public String getTasktitle() {return tasktitle;}
    public void setTaskdescription(String description) {taskdescription = description;}
    public String getTaskdescription() {return taskdescription;}
    //public String getTaskstatus(){return taskstatus[taskstatussel];}
    public int getTaskstatussel() {return taskstatussel;}
    public void setTaskduedate(String duedate) {taskduedate = duedate;}
    public String getTaskduedate(){return taskduedate;}



}
//End

