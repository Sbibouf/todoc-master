package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {

    private TaskDao mTask;

    public TaskRepository(TaskDao taskDao) {

        mTask = taskDao;
    }

    //***************************
    // Get Task
    //***************************

    public LiveData<List<Task>> getTask (long taskId) {
        return this.mTask.getTasks(taskId);
    }

    //***************************
    // Create Task
    //***************************

    public void createTask (Task task) {
        mTask.createTask(task);
    }

    //***************************
    // Delete Task
    //***************************

    public void deleteTask(Task task) {
        mTask.deleteTask(task.getId());
    }
}
