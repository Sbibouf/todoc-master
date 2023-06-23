package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao

public interface ProjectDao {


    @Query("SELECT * FROM Project WHERE id = :ProjectId")
    LiveData<Project> getProjectbyId(Long ProjectId);

    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getAllProject();


    @Query("DELETE FROM Project WHERE id = :projectId")

    int deleteProject(Long projectId);

    @Insert

    void createProject(Project project);

    @Query("SELECT * FROM PROJECT")
    LiveData<Project[]> getAllProjects();
}
