package com.cleanup.todoc.model;

public class TaskWithProject {

    //************************
    // VARIABLES
    //************************

    private Task task;

    private Project project;



    //*********************************
    // CONSTRUCTOR
    //*********************************


    public TaskWithProject(Task task, Project project){

        this.task = task;
        this.project = project;
    }



    //*********************************
    // GETTERS AND SETTERS
    //*********************************


    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
