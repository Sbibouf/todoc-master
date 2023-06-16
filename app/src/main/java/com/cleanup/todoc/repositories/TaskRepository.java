package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {

    private TaskDao mTask; // Variable recupérant le Dao des tasks

    private static TaskRepository instance = null;

    /**
     * Constructor
     * @param taskDao
     */

    public TaskRepository(TaskDao taskDao) {

        mTask = taskDao;
    }

    /**
     * Singleton
     */

    public static TaskRepository getInstance(){
        if(instance==null){
            synchronized (TaskRepository.class){
                if(instance==null){
                    instance = new TaskRepository(instance.mTask);
                }
            }
        }
        return instance;
    }

    /**
     * Return a liveData with the list of Task identified by the ID
     * @param taskId
     * @return
     */

    public LiveData<Task> getTask (Long taskId) {
        return this.mTask.getTasks(taskId);
    }

    public LiveData<List<Task>> getAllTasks() {return this.mTask.getAllTasks();}
    /**
     * Create a Task and add it in the list via TaskDao
     * @param task
     */

    public void createTask (Task task) {
        mTask.createTask(task);
    }

    /**
     * Remove a Task from the list via TaskDao
     * @param task
     */

    public void deleteTask(Task task) {
        mTask.deleteTask(task.getId());
    }
}
