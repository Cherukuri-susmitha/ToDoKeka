package com.example.todokeka;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

//MainActivity.java
public class MainActivity extends AppCompatActivity {
    private TaskDao taskDao;
    private TaskListAdapter taskListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskDao = new TaskDao(this);

        ListView taskListView = findViewById(R.id.taskListView);
        taskListAdapter = new TaskListAdapter(this, R.layout.task_list_item, taskDao.getAllTasks());
        taskListView.setAdapter(taskListAdapter);

        Button addTaskButton = findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open AddTaskActivity for creating a new task
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivityForResult(intent, ADD_TASK_REQUEST_CODE);
            }
        });
        // Handle task selection for viewing/editing
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Task selectedTask = taskListAdapter.getItem(position);

                if (selectedTask != null) {
//                    updateTask(selectedTask);
                    Toast.makeText(MainActivity.this, selectedTask.getTitle() + selectedTask.getStatus() + " " + selectedTask.getId(), Toast.LENGTH_SHORT).show();

//                    // Open TaskDetailActivity for viewing/editing the task
                    String str= String.valueOf(selectedTask.getId());
                    String title= String.valueOf(selectedTask.getTitle());
                    String due= String.valueOf(selectedTask.getDueDate());
                    String priority= String.valueOf(selectedTask.getPriority());
                    String category= String.valueOf(selectedTask.getCategory());
                    String status= String.valueOf(selectedTask.getStatus());


                    Intent intent = new Intent(MainActivity.this, TaskDetailsActivity.class);
//                    intent.putExtra("taskId", str);
                    intent.putExtra("id",str);
                    intent.putExtra("title",title);
                    intent.putExtra("due",due);
                    intent.putExtra("category",category);
                    intent.putExtra("priority",priority);
                    intent.putExtra("status",status);

                    startActivity(intent);
                } else {
                    // Handle the case where selectedTask is null (should not happen)
                    // Log an error or show a toast message
                    Log.e("MainActivity", "Selected task is null.");
                    // You can also show a Toast message to inform the user
                    Toast.makeText(MainActivity.this, "Error: Task not found.", Toast.LENGTH_SHORT).show();
                }
            }
        });



        // Handle task deletion
        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Task selectedTask = taskListAdapter.getItem(position);
                deleteTask(selectedTask);
                return true; // Consume the long click event
            }
        });
    }

    private static final int ADD_TASK_REQUEST_CODE = 1;

    // Handle the result from AddTaskActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TASK_REQUEST_CODE && resultCode == RESULT_OK) {
            // Reload tasks when a new task is added
            taskListAdapter.updateData(taskDao.getAllTasks());
        }
    }

    // Delete task from the list and update the adapter
    private void deleteTask(Task task) {
        taskDao.deleteTask(task.getId());
        if (taskListAdapter != null) {
            taskListAdapter.updateData(taskDao.getAllTasks());
        }
    }

    // Update task in the list and update the adapter
    private void updateTask(Task updatedTask) {
        taskDao.updateTask(updatedTask);
        if (taskListAdapter != null) {
            taskListAdapter.updateData(taskDao.getAllTasks());
        }
    }
}