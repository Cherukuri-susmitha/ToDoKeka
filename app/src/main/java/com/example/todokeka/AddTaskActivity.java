package com.example.todokeka;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTaskActivity extends Activity {

    private EditText titleEditText, dueDateEditText, categoryEditText;
    private Spinner prioritySpinner, statusSpinner;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        titleEditText = findViewById(R.id.titleEditText);
        prioritySpinner = findViewById(R.id.prioritySpinner);
        dueDateEditText = findViewById(R.id.dueDateEditText);
        categoryEditText = findViewById(R.id.categoryEditText);
        statusSpinner = findViewById(R.id.statusSpinner);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        Button saveTaskButton = findViewById(R.id.saveTaskButton);
        saveTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
            }
        });

        dueDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
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
        dueDateEditText.setText(dateFormat.format(calendar.getTime()));
    }

    private void saveTask() {
        String title = titleEditText.getText().toString();
        String priority = prioritySpinner.getSelectedItem().toString();
        String dueDate = dueDateEditText.getText().toString();
        String category = categoryEditText.getText().toString();
        String status = statusSpinner.getSelectedItem().toString();

        // Create a new Task object with the entered details
        Task newTask = new Task(0, title, priority, dueDate, category, status);

        // Save the new task to the database or perform your desired action
        // (You need to implement the logic to save the task in your TaskDao class)

        // For demonstration, let's assume you have a TaskDao instance called taskDao
        TaskDao taskDao = new TaskDao(this);
        taskDao.addTask(newTask);

        setResult(RESULT_OK);
        // Finish the activity and return to the main activity
        finish();
    }
}

