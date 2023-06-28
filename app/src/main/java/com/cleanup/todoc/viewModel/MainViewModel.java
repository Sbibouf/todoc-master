package com.cleanup.todoc.viewModel;

import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
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

    private Project[] allP;

    /**
     * Data
     */

    @Nullable
    private MutableLiveData<List<Task>> currentTasks = new MutableLiveData<>();
    private LiveData<List<Task>> mLiveData;
    private MediatorLiveData<List<Task>> mCurrentTask = new MediatorLiveData<>();


    public MainViewModel(TaskRepository taskRepository, ProjectRepository projectRepository, Executor executor) {
        mTaskRepository = taskRepository;
        mProjectRepository = projectRepository;
        mExecutor = executor;
    }

    public void init() {

        if(mLiveData != null){

            return;
        }

        mLiveData = mTaskRepository.getAllTasks();

        mCurrentTask.addSource(mLiveData, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                mCurrentTask.setValue(tasks);
            }
        });
        currentTasks = mCurrentTask;

    }



    /**
     * Tasks
     *
     * @return
     */
    @Nullable
    public LiveData<List<Task>> getCurrentTasks() {
        return this.currentTasks;
    }

    @Nullable
    public LiveData<List<Task>> getAllTasks() {
        return mTaskRepository.getAllTasks();
    }


    public void createTask(Long projectId, String nameP, long creationTimestamp, Project project) {
        mExecutor.execute(() -> {
            mTaskRepository.createTask(new Task(projectId, nameP, creationTimestamp, project));
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
        //return currentTasks.postValue(mTaskRepository.getTasksAToZ());
        return mTaskRepository.getTasksAToZ();

    }

    public LiveData<List<Task>> getTasksZToA() {
        return mTaskRepository.getTasksZToA();

    }

    public LiveData<List<Task>> getTasksRecentToOld() {
        return mTaskRepository.getTasksRecentToOld();
    }

    public LiveData<List<Task>> getTasksOldToRecent() {
        return mTaskRepository.getTasksOldToRecent();
    }

    public LiveData<List<Project>> getAllProject() {
        return mProjectRepository.getAllProject();
    }

    public LiveData<Project[]> getAllProjects() {
        return mProjectRepository.getAllProjects();
    }

    public void updateCurrentTask (List<Task> tasks){

        mCurrentTask.setValue(tasks);
    }
    public void updateCurrentTask2 (List<Task> tasks){

        currentTasks.setValue(tasks);
    }


}
