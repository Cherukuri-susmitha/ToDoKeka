package com.example.todokeka;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TaskListAdapter extends ArrayAdapter<Task> {

    private Context context;
    private int resource;
    private List<Task> taskList;

    public TaskListAdapter(Context context, int resource, List<Task> taskList) {
        super(context, resource, taskList);
        this.context = context;
        this.resource = resource;
        this.taskList = taskList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        Task task = taskList.get(position);

        TextView titleTextView = convertView.findViewById(R.id.titleTextView);
        TextView priorityTextView = convertView.findViewById(R.id.priorityTextView);
        TextView dueDateTextView = convertView.findViewById(R.id.dueDateTextView);
        TextView categoryTextView = convertView.findViewById(R.id.categoryTextView);
        TextView statusTextView = convertView.findViewById(R.id.statusTextView);

        titleTextView.setText(task.getTitle());
        priorityTextView.setText("Priority: " + task.getPriority());
        dueDateTextView.setText("Due Date: " + task.getDueDate());
        categoryTextView.setText("Category: " + task.getCategory());
        statusTextView.setText("Status: " + task.getStatus());

        return convertView;
    }
    public void updateData(List<Task> newTaskList) {
        clear();
        if (newTaskList != null) {
            addAll(newTaskList);
            notifyDataSetChanged();
        }
    }
}
