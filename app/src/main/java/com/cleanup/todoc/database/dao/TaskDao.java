package com.cleanup.todoc.database.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao

public interface TaskDao {
    /**
     * Replace an existing Task with the same ID by a new one
     * @param task
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)

    void createTask(Task task);

    /**
     * Select one task with its ID
     * @param taskId
     * @return
     */
    @Query("SELECT * FROM Task WHERE id = :taskId")
    LiveData<Task> getTasks(Long taskId);

    /**
     * Select all Tasks
     * @return
     */
    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAllTasks();


    /**
     * Update one task
     * @param task
     * @return
     */
    @Update

    void updateTask(Task task);

    /**
     * Delete one task with its ID
     * @param taskId
     */
    @Query("DELETE FROM Task WHERE id = :taskId")

    void deleteTask(Long taskId);

    /**
     * Select all tasks by alphabetical order
     */
    @Query("SELECT * FROM Task ORDER BY name")
    LiveData<List<Task>> orderTaskAToZ();

    /**
     * Select all tasks by inverted alphabetical order
     */
    @Query("SELECT * FROM Task ORDER BY name DESC")
    LiveData<List<Task>> orderTaskZToA();

    /**
     * Select all tasks by date
     */
    @Query("SELECT * FROM Task ORDER BY creationTimestamp")
    LiveData<List<Task>> orderTaskOldToRecent();

    /**
     * Select all tasks by date inverted
     */

    @Query("SELECT * FROM Task ORDER BY creationTimestamp DESC")
    LiveData<List<Task>> orderTaskRecentToOld();


}
