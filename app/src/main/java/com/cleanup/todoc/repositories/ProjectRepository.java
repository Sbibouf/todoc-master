package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectRepository {

    /**
     * Dao
     */
    private ProjectDao mProjectDao;


    /**
     * Instance variable for the singleton
     */
    private static ProjectRepository instance = null;

    /**
     * Constructor
     * @param projectDao
     */

    public ProjectRepository(ProjectDao projectDao){

        this.mProjectDao=projectDao;
    }

    /**
     * Get an instance of a new ProjectRepository
     */

    public static ProjectRepository getInstance(ProjectDao projectDao){
        if(instance==null){
            synchronized (ProjectRepository.class){
                if(instance==null){
                    instance = new ProjectRepository(projectDao);
                }
            }
        }
        return instance;
    }

    /**
     * Return a Livedata with a list of all project from Dao
     * @return
     */
    public LiveData<List<Project>> getAllProject () {
        return this.mProjectDao.getAllProject();
    }

    /**
     * Return a livedata with an Araay of all projects from Dao
     * @return
     */

    public LiveData<Project[]> getAllProjects() {return this.mProjectDao.getAllProjects();}
}
