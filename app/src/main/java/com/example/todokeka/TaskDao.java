package com.example.todokeka;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TaskDao {
    private SQLiteDatabase database;

    public TaskDao(Context context) {
        TaskDbHelper dbHelper = new TaskDbHelper(context);
        database = dbHelper.getWritableDatabase();
    }
    String y;

    public void addTask(Task task) {
        ContentValues values = new ContentValues();
        values.put("title", task.getTitle());
        String x=task.getPriority();

        if(x.equals("High"))
            y="1";
        else if(x.equals("Medium"))
            y="2";
        else
            y="3";
        values.put("priority", y);
        values.put("due_date", task.getDueDate());
        values.put("category", task.getCategory());
        values.put("status", task.getStatus());

        database.insert("tasks", null, values);
    }

    String pri;
    @SuppressLint("Range")
    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        Cursor cursor = database.query("tasks", null, null, null, null, null,"priority" );

        while (cursor.moveToNext()) {
            Task task = new Task();
            task.setId(cursor.getInt(cursor.getColumnIndex("id")));
            task.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            String man=cursor.getString(cursor.getColumnIndex("priority"));
            if(man.equals("3"))
                pri="Low";
            else if (man.equals("2")) {
                pri="Medium";
            }
            else{
                pri="High";
            }
            task.setPriority(pri);
            task.setDueDate(cursor.getString(cursor.getColumnIndex("due_date")));
            task.setCategory(cursor.getString(cursor.getColumnIndex("category")));
            task.setStatus(cursor.getString(cursor.getColumnIndex("status")));

            taskList.add(task);
        }

        cursor.close();
        return taskList;
    }

    String updatePri;
    public void updateTask(Task task) {
//        Log.e("Done","Taskupdate");
        if(task.getPriority().equals("High"))
            updatePri="1";
        else if (task.getPriority().equals("Medium")) {
            updatePri="2";
        }
        else
            updatePri="3";
        Log.e("title_Update","Recived " + task.getTitle());
        String sql = "UPDATE tasks SET title=?, priority=?, due_date=?, category=?, status=? WHERE id=?";
        Object[] bindArgs = {task.getTitle(), updatePri, task.getDueDate(), task.getCategory(), task.getStatus(), task.getId()};
        database.execSQL(sql, bindArgs);
    }

    public void deleteTask(int taskId) {
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(taskId)};

        database.delete("tasks", whereClause, whereArgs);
    }

    // Get tasks based on the selected category
    @SuppressLint("Range")
    public List<Task> getTasksByCategory(String category) {
        List<Task> taskList = new ArrayList<>();
        String selection = "category = ?";
        String[] selectionArgs = {category};

        Cursor cursor = database.query("tasks", null, selection, selectionArgs, null, null, null);

        while (cursor.moveToNext()) {
            Task task = new Task();
            task.setId(cursor.getInt(cursor.getColumnIndex("id")));
            task.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            task.setPriority(cursor.getString(cursor.getColumnIndex("priority")));
            task.setDueDate(cursor.getString(cursor.getColumnIndex("due_date")));
            task.setCategory(cursor.getString(cursor.getColumnIndex("category")));
            task.setStatus(cursor.getString(cursor.getColumnIndex("status")));

            taskList.add(task);
        }

        cursor.close();
        return taskList;
    }

    public List<Task> updateData() {
        List<Task> updatedTaskList = getAllTasks();
        return updatedTaskList;
    }
}