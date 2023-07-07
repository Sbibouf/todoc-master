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


    /**
     * Get Project from base with Id
     * @param ProjectId
     * @return
     */
    @Query("SELECT * FROM Project WHERE id = :ProjectId")
    LiveData<Project> getProjectbyId(Long ProjectId);


    /**
     * Get all project from base
     * @return
     */
    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getAllProject();


    /**
     * Delete Project from base
     *
     * @param projectId
     * @return
     */
    @Query("DELETE FROM Project WHERE id = :projectId")
    int deleteProject(Long projectId);


    /**
     * Insert a new project into database
     * @param project
     */
    @Insert
    void createProject(Project project);


    /**
     * Get all project in an array
     * @return
     */
    @Query("SELECT * FROM PROJECT")
    LiveData<Project[]> getAllProjects();
}
