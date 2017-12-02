package com.example.dingo.dingoapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    private String taskassigneeemail;

    public MyTask() {}

    //For the Database
    public MyTask(String id, String title, String description, String duedate, int statussel, String assigneeemail) {
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
        taskassigneeemail = assigneeemail;
    }


    //Setters and Getters
    public void setTaskid(String id) {taskid = id;}
    public String getTaskid() {return taskid;}
    public void setTasktitle(String title) {tasktitle = title;}
    public String getTasktitle() {return tasktitle;}
    public void setTaskdescription(String description) {taskdescription = description;}
    public String getTaskdescription() {return taskdescription;}
    public String gTaskstatus(int taskstatussel){
        taskstatus = new String[4];
        taskstatus[0] = "Not Started";
        taskstatus[1] = "In Progress";
        taskstatus[2] = "Complete";
        taskstatus[3] = "Late";
        return taskstatus[taskstatussel];
    }
    public int getTaskstatussel() {return taskstatussel;}
    public void setTaskduedate(String duedate) {taskduedate = duedate;}
    public String getTaskduedate(){return taskduedate;}
    public void sTaskstatus(int sel) {
        taskstatussel = sel;
    }
    public void byTaskstatussel(String sel) {
        for (int i=0;i<taskstatus.length;i++) {
            if (taskstatus[i].equals(sel)) {
                taskstatussel = i;
                break;
            }
        }
    }
    public void setTaskassigneeemail(String email) {taskassigneeemail = email;}
    public String getTaskassigneeemail() {return taskassigneeemail;}

}
//End

