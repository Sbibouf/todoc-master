package com.cleanup.todoc.viewModel;

import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.injection.SortMethod;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.model.TaskWithProject;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class MainViewModel extends ViewModel {

    /**
     * Repositories
     */

    private TaskRepository mTaskRepository;

    private ProjectRepository mProjectRepository;

    private final Executor mExecutor;

    private SortMethod tri;



    /**
     * Data
     */



    public MainViewModel(TaskRepository taskRepository, ProjectRepository projectRepository, Executor executor) {
        mTaskRepository = taskRepository;
        mProjectRepository = projectRepository;
        mExecutor = executor;
        tri = SortMethod.NONE;
    }





    /**
     * Tasks
     *
     * @return
     */
    @Nullable
    public LiveData<List<Task>> getCurrentTasks() {
        if(tri == SortMethod.NONE){
            return mTaskRepository.getAllTasks();
        }
        else if (tri == SortMethod.ALPHABETICAL){

            return mTaskRepository.getTasksAToZ();
        }
        else if (tri == SortMethod.ALPHABETICAL_INVERTED){

            return mTaskRepository.getTasksZToA();
        }
        else if (tri == SortMethod.RECENT_FIRST){

            return mTaskRepository.getTasksRecentToOld();
        }
        else{

            return mTaskRepository.getTasksOldToRecent();
        }
    }

    @Nullable
    public LiveData<List<Task>> getAllTasks() {
        return mTaskRepository.getAllTasks();
    }


    public void createTask(Long projectId, String nameP, long creationTimestamp) {
        mExecutor.execute(() -> {
            mTaskRepository.createTask(new Task(projectId, nameP, creationTimestamp));
        });
    }


    public void deleteTask(Task task) {
        mExecutor.execute(() -> mTaskRepository.deleteTask(task));
    }


    /**
     * Project
     *
     * @param projectId
     * @return
     */

    public LiveData<Project> getProjectById(Long projectId) {
        return mProjectRepository.getProjectById(projectId);
    }


    public LiveData<List<Task>> getTasksAToZ() {
        tri = SortMethod.ALPHABETICAL;
        return mTaskRepository.getTasksAToZ();

    }

    public LiveData<List<Task>> getTasksZToA() {
        tri = SortMethod.ALPHABETICAL_INVERTED;
        return mTaskRepository.getTasksZToA();

    }

    public LiveData<List<Task>> getTasksRecentToOld() {
        tri = SortMethod.RECENT_FIRST;
        return mTaskRepository.getTasksRecentToOld();
    }

    public LiveData<List<Task>> getTasksOldToRecent() {
        tri = SortMethod.OLD_FIRST;
        return mTaskRepository.getTasksOldToRecent();
    }

    public LiveData<List<Project>> getAllProject() {
        return mProjectRepository.getAllProject();
    }

    public LiveData<Project[]> getAllProjects() {
        return mProjectRepository.getAllProjects();
    }



}
