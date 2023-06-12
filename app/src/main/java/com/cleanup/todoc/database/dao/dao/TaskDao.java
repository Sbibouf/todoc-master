package com.cleanup.todoc.database.dao.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)

    void createTask(Task task);

    @Query("SELECT * FROM Task WHERE id = :taskId")
    LiveData<Task> getTasks(long taskId);

    @Insert

    long insertTask(Task task);

    @Update

    int updateTask(Task task);

    @Query("DELETE FROM Task WHERE id = :taskId")

    int deleteTask(long taskId);
}
