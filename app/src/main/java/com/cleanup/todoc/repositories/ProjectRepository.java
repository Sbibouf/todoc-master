package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectRepository {

    private ProjectDao mProjectDao; // Variable récupérant le Dao des Projects


    private static ProjectRepository instance = null;

    /**
     * Constructor
     * @param projectDao
     */

    public ProjectRepository(ProjectDao projectDao){

        this.mProjectDao=projectDao;
    }

    /**
     * Singleton
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
     * Return a LiveData with the project identified with the ID
     * @param projectId
     * @return
     */

    public LiveData<Project> getProjectById (Long projectId) {
        return this.mProjectDao.getProjectbyId(projectId);
    }
    public LiveData<List<Project>> getAllProject () {
        return this.mProjectDao.getAllProject();
    }

    public LiveData<Project[]> getAllProjects() {return this.mProjectDao.getAllProjects();}
}
