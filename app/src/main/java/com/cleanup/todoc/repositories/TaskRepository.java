package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
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

    public static TaskRepository getInstance(TaskDao taskDao){
        if(instance==null){
            synchronized (TaskRepository.class){
                if(instance==null){
                    instance = new TaskRepository(taskDao);
                }
            }
        }
        return instance;
    }

    /**
     * Return a liveData with the list of Task
     * @return
     */

    public LiveData<List<Task>> getAllTasks() {return  this.mTask.getAllTasks();}

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

    /**
     * Return a liveData with a list of Task sort A to Z
     * @return
     */

    public LiveData<List<Task>> getTasksAToZ(){
        return mTask.orderTaskAToZ();
    }


    /**
     * Return a liveData with a list of Task sort Z to A
     * @return
     */

    public LiveData<List<Task>> getTasksZToA(){
        return mTask.orderTaskZToA();
    }


    /**
     * Return a liveData with a list of Task sort Recent to Old
     * @return
     */

    public LiveData<List<Task>> getTasksRecentToOld(){
        return mTask.orderTaskRecentToOld();
    }


    /**
     * Return a liveData with a list of Task sort Old to Recent
     * @return
     */

    public LiveData<List<Task>> getTasksOldToRecent(){
        return mTask.orderTaskOldToRecent();
    }

}
