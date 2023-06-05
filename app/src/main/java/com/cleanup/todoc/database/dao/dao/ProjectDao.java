package com.cleanup.todoc.database.dao.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;

@Dao

public interface ProjectDao {


    @Query("SELECT * FROM Project WHERE id = :ProjectId")
    LiveData<Project> getProject(long ProjectId);

    @Query("DELETE FROM Project WHERE id = :projectId")

    int deleteProject(long projectId);

}
