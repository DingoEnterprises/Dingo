package com.example.dingo.dingoapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CurrentTaskListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_task_list2, container, false);



















    //This code was based off the productcatalog from mgarzon (Lab 3)
    //Start

    //MAIN ACTIVITY

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
    import android.widget.Toast;

    import java.util.ArrayList;
    import java.util.List;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;

        public class MainActivity extends AppCompatActivity {

            EditText editTextName;
            EditText editTextPrice;
            Button buttonAddProduct;
            ListView listViewProducts;
            DatabaseReference databaseProducts;
            List<Product> products;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                databaseProducts = FirebaseDatabase.getInstance().getReference("tasks");
                editTextName = (EditText) findViewById(R.id.editTextName);
                editTextPrice = (EditText) findViewById(R.id.editTextPrice);
                listViewProducts = (ListView) findViewById(R.id.listViewProducts);
                buttonAddProduct = (Button) findViewById(R.id.addButton);

                products = new ArrayList<>();

                //adding an onclicklistener to button
                buttonAddProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addTask();
                    }
                });

                listViewProducts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Product product = products.get(i);
                        showUpdateDeleteDialog(product.getId(), product.getProductName());
                        return true;
                    }
                });
            }


            @Override
            protected void onStart() {
                super.onStart();
                databaseProducts.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        products.clear();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Product product = postSnapshot.getValue(Product.class);
                            products.add(product);
                        }
                        ProductList productsAdapter = new ProductList(MainActivity.this, products);
                        listViewProducts.setAdapter(productsAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }


            private void showUpdateDeleteDialog(final String productId, String productName) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.update_dialog, null);
                dialogBuilder.setView(dialogView);

                final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
                final EditText editTextPrice = (EditText) dialogView.findViewById(R.id.editTextPrice);
                final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateProduct);
                final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteProduct);

                dialogBuilder.setTitle(productName);
                final AlertDialog b = dialogBuilder.create();
                b.show();

                buttonUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = editTextName.getText().toString().trim();
                        double price = Double.parseDouble(String.valueOf(editTextPrice.getText().toString()));
                        if (!TextUtils.isEmpty(name)) {
                            updateProduct(productId, name, price);
                            b.dismiss();
                        }
                    }
                });

                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteProduct(productId);
                        b.dismiss();
                    }
                });
            }

            private void updateProduct(String id, String name, double price) {
                DatabaseReference dR = FirebaseDatabase.getInstance().getReference("products").child(id);
                Product product = new Product(id, name, price);
                dR.setValue(product);

                Toast.makeText(getApplicationContext(), "Products Updated", Toast.LENGTH_LONG).show();
            }

            private void deleteProduct(String id) {
                DatabaseReference dR = FirebaseDatabase.getInstance().getReference("products").child(id);
                dR.removeValue();
                Toast.makeText(getApplicationContext(), "Prodcut Deleted", Toast.LENGTH_LONG).show();
            }

            private void addProduct() {
                String name = editTextName.getText().toString().trim();
                double price = Double.parseDouble(String.valueOf(editTextPrice.getText().toString()));
                if (!TextUtils.isEmpty(name)) {
                    String id = databaseProducts.push().getKey();
                    Product product = new Product(id, name, price);
                    databaseProducts.child(id).setValue(product);
                    editTextName.setText("");
                    editTextPrice.setText("");
                    Toast.makeText(this, "Product added", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
                }

            }
        }

        //disclaimer: TASK and TASK LIST are based off of miguel's model in lab 3

        //TASK

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
            public String getTaskDecription() {return taskDescription;}

            public void setTaskDueDate(String dueDate) {taskDueDate = dueDate;}
            public String getTaskDueDate(){return taskDueDate;}

        //TASK LIST

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
                super(context, R.layout.layout_current_task_list, tasks);
                this.context = context;
                this.currentTasks = tasks;
            }

            public TaskList(Activity context, List<Task> tasks) {
                super(context, R.layout.layout_past_task_list, tasks);
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






    //End

    }
}
