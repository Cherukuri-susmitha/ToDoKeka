//package com.example.todokeka;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//class TaskDetailActivity extends Activity {
//
//    private Task task;
//
//    private EditText titleEditText;
//    private EditText priorityEditText;
//    private EditText dueDateEditText;
//    private EditText categoryEditText;
//    private EditText statusEditText;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_task_detail);
//
//        // Get the Task object from the intent
//        Intent intent = getIntent();
//        task = (Task) intent.getSerializableExtra("task");
//
//        // Initialize EditTexts
//        titleEditText = findViewById(R.id.titleEditText);
//        priorityEditText = findViewById(R.id.forPriority);
//        dueDateEditText = findViewById(R.id.forDueDate);
//        categoryEditText = findViewById(R.id.forCategory);
//        statusEditText = findViewById(R.id.forStatus);
//
//        // Set initial values
//        titleEditText.setText(task.getTitle());
//        priorityEditText.setText(task.getPriority());
//        dueDateEditText.setText(task.getDueDate());
//        categoryEditText.setText(task.getCategory());
//        statusEditText.setText(task.getStatus());
//
//        // Set up the Save button
//        Button saveButton = findViewById(R.id.saveTaskButton);
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Save the edited task
//                saveTask();
//            }
//        });
//    }
//
//    private void saveTask() {
//        // Update the task with edited values
//        task.setTitle(titleEditText.getText().toString());
//        task.setPriority(priorityEditText.getText().toString());
//        task.setDueDate(dueDateEditText.getText().toString());
//        task.setCategory(categoryEditText.getText().toString());
//        task.setStatus(statusEditText.getText().toString());
//
//        // Return the updated task to the calling activity
//        Intent resultIntent = new Intent();
//        resultIntent.putExtra("editedTask", task);
//
//        setResult(Activity.RESULT_OK, resultIntent);
//        finish();
//    }
//}

package com.example.todokeka;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TaskDetailsActivity extends AppCompatActivity {

    EditText Title, Duedate, Category;

    Spinner Priority, Status;
    Calendar calendar;
    private SimpleDateFormat dateFormat;


    List<String> footballPlayers = new ArrayList<>();
    List<String> StatusList = new ArrayList<>();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());


        Title = findViewById(R.id.forText);
        Duedate = findViewById(R.id.forDueDate);
        Category = findViewById(R.id.forCategory);
        Priority = findViewById(R.id.forPriority);
        Status = findViewById(R.id.forStatus);
//        sp=findViewById(R.id.spinnertrail);

        Duedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog1();
            }
        });

        Button update_button = findViewById(R.id.updateTaskButton);
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTask();
            }
        });




        StatusList.add("In Progress");
        StatusList.add("Completed");
        StatusList.add("In Completed");
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, StatusList);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Status.setAdapter(arrayAdapter1);
        Status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose Football players from lis")){
                }else {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(),"Selected: " +item, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


//        footballPlayers.add(0, getIntent("pri"));
        footballPlayers.add("Low");
        footballPlayers.add("Medium");
        footballPlayers.add("High");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, footballPlayers);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Priority.setAdapter(arrayAdapter);
        Priority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose Football players from lis")){
                }else {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(),"Selected: " +item, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });







        Intent intent = getIntent();
        String forId=intent.getStringExtra("id");
        String forTitle=intent.getStringExtra("title");
        String forDue=intent.getStringExtra("due");
        String forCategory=intent.getStringExtra("category");
        String forPriority=intent.getStringExtra("priority");
        String forStatus=intent.getStringExtra("status");


        Title.setText(forTitle);
//        Priority.setTex(forPriority);
        Duedate.setText(forDue);
        Category.setText(forCategory);
//        Status.setText(forStatus);
        updateSelectedValues(forPriority);
        updateStatus(forStatus);



    }
    private void updateSelectedValues(String str){
        int index=footballPlayers.indexOf(str);
        if(index!=-1){
            Priority.setSelection(index);
        }
    }
    private void updateStatus(String str){
        int index=StatusList.indexOf(str);
        if(index!=-1){
            Status.setSelection(index);
        }
    }

    private void showDatePickerDialog1() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateDueDate();
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private void updateDueDate() {
        Duedate.setText(dateFormat.format(calendar.getTime()));
    }

    private void updateTask() {
        Intent intent = getIntent();
        int forId=Integer.parseInt(intent.getStringExtra("id"));
        String title = Title.getText().toString();
        String priority = Priority.getSelectedItem().toString();
        String dueDate = Duedate.getText().toString();
        String category = Category.getText().toString();
        String status = Status.getSelectedItem().toString();

        // Create a new Task object with the entered details
        Task newTask = new Task(forId, title,priority, dueDate, category, status);


        // Save the new task to the database or perform your desired action
        // (You need to implement the logic to save the task in your TaskDao class)

        // For demonstration, let's assume you have a TaskDao instance called taskDao
        TaskDao taskDao = new TaskDao(this);
        taskDao.updateTask(newTask);

        setResult(RESULT_OK);
        // Finish the activity and return to the main activity
        finish();
    }
}
//package com.example.todokeka;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.annotation.SuppressLint;
//import android.app.DatePickerDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.TextView;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Locale;
//
//public class TaskDetailsActivity extends AppCompatActivity {
//
//    EditText Title, Duedate, Category, Priority, Status;
//    Calendar calendar;
//
//    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_task_detail);
//
//        Title = findViewById(R.id.forText);
//        Duedate = findViewById(R.id.forDueDate);
//        Category = findViewById(R.id.forCategory);
//        Priority = findViewById(R.id.forPriority);
//        Status = findViewById(R.id.forStatus);
//
//        Button update_button = findViewById(R.id.saveTaskButton);
//        update_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                updateTask();
//            }
//        });
//        Intent intent = getIntent();
//        String forId=intent.getStringExtra("id");
//        String forTitle=intent.getStringExtra("title");
//        String forPriority=intent.getStringExtra("priority");
//        String forDue=intent.getStringExtra("due");
//        String forCategory=intent.getStringExtra("category");
//        String forStatus=intent.getStringExtra("status");
//
//
//        Title.setText(forTitle);
//        Priority.setText(forPriority);
//        Duedate.setText(forDue);
//        Category.setText(forCategory);
//        Status.setText(forStatus);
//
//
//    }
//    private void updateTask() {
//        Intent intent = getIntent();
//        int forId=Integer.parseInt(intent.getStringExtra("id"));
//        String title = Title.getText().toString();
//        String priority = Priority.getText().toString();
//        String dueDate = Duedate.getText().toString();
//        String category = Category.getText().toString();
//        String status = Status.getText().toString();
//
//        // Create a new Task object with the entered details
//        Task newTask1 = new Task(forId, title,priority, dueDate, category, status);
//        Task newTask = new Task(forId, title, priority, dueDate, category, status);
//
//
//        // Save the new task to the database or perform your desired action
//        // (You need to implement the logic to save the task in your TaskDao class)
//
//        // For demonstration, let's assume you have a TaskDao instance called taskDao
//        TaskDao taskDao = new TaskDao(this);
//        taskDao.updateTask(newTask);
//
//        setResult(RESULT_OK);
//        // Finish the activity and return to the main activity
//        finish();
//    }
//}