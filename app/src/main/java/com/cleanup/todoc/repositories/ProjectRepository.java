package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

public class ProjectRepository {

    private ProjectDao mProjectDao; // Variable récupérant le Dao des Projects

    //***************************
    // Constructor
    //***************************

    public ProjectRepository(ProjectDao projectDao){

        this.mProjectDao=projectDao;
    }


    //***************************
    // Get Project
    //***************************

    public LiveData<Project> getProject (long projectId) {
        return this.mProjectDao.getProject(projectId);
    }
}
