package com.cleanup.todoc.model;

public class TaskWithProject {

    /**
     * Task and Project variable to associate with class
     */

    private Task task;

    private Project project;


    /**
     * Instantiates a new TaskWithProject
     * @param task
     * @param project
     */


    public TaskWithProject(Task task, Project project){

        this.task = task;
        this.project = project;
    }


    /**
     * Getters and setters for the variables
     * @return
     */


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
