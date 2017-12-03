package com.example.bim.dingo;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class Closet extends AppCompatActivity {

    EditText editTextNameTool;
    Button buttonAddTool;
    ListView listViewTools;

    List<Tool> tools;

    DatabaseReference databaseTools;

    int numberOfTools;
    TextView number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.closet);

        databaseTools = FirebaseDatabase.getInstance().getReference("tools");
        editTextNameTool = (EditText) findViewById(R.id.editTextNameTool);
        listViewTools = (ListView) findViewById(R.id.listViewTools);
        buttonAddTool = (Button) findViewById(R.id.addButtonTool);

        numberOfTools = 0;
        number = (TextView) findViewById(R.id.textViewCloset);
        number.setText("Broom closet: " + numberOfTools + " tools");

        tools = new ArrayList<>();

        //adding an onclicklistener to button
        buttonAddTool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTool();
            }
        });

        listViewTools.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Tool tool = tools.get(i);
                showUpdateDeleteDialog(tool.getId(), tool.getToolName());
                return true;
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseTools.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                tools.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting tool
                    Tool tool = postSnapshot.getValue(Tool.class);
                    //adding tool to the list
                    tools.add(tool);
                }

                //creating adapter
                ToolList toolsAdapter = new ToolList(Closet.this, tools);
                //attaching adapter to the listview
                listViewTools.setAdapter(toolsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void showUpdateDeleteDialog(final String toolId, String toolName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_tool_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextNameTool = (EditText) dialogView.findViewById(R.id.editTextNameTool);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateTool);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteTool);

        dialogBuilder.setTitle(toolName);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextNameTool.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {
                    updateTool(toolId, name);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTool(toolId);
                b.dismiss();
            }
        });
    }

    private void updateTool(String id, String name) {
        //getting the specified tool reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("tools").child(id);
        //updating tool
        Tool tool = new Tool(id, name);
        dR.setValue(tool);
        Toast.makeText(getApplicationContext(), "Tool Updated", Toast.LENGTH_LONG).show();
    }

    private boolean deleteTool(String id) {
        //getting the specified tool reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("tools").child(id);
        //removing prodct
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Tool Deleted", Toast.LENGTH_LONG).show();
        numberOfTools--;
        number.setText("Broom closet: " + numberOfTools + " tools");
        return true;
    }

    private void addTool() {
        //getting the values to save
        String name = editTextNameTool.getText().toString().trim();

        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Tool
            String id = databaseTools.push().getKey();

            //creating an Tool Object
            Tool tool = new Tool(id, name);

            //Saving the Tool
            databaseTools.child(id).setValue(tool);

            //setting edittext to blank again
            editTextNameTool.setText("");


            //displaying a success toast
            Toast.makeText(this, "Tool added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }


        numberOfTools++;
        number.setText("Broom closet: " + numberOfTools + " tools");
    }

}

