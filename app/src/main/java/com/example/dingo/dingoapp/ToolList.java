package com.example.bim.dingo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ToolList extends ArrayAdapter<Tool> {
    private Activity context;
    List<Tool> tools;

    public ToolList(Activity context, List<Tool> tools) {
        super(context, R.layout.layout_tool_list, tools);
        this.context = context;
        this.tools = tools;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItemTool = inflater.inflate(R.layout.layout_tool_list, null, true);

        TextView textViewNameTool = (TextView) listViewItemTool.findViewById(R.id.textViewNameTool);

        Tool tool = tools.get(position);
        textViewNameTool.setText(tool.getToolName());
        return listViewItemTool;
    }
}



