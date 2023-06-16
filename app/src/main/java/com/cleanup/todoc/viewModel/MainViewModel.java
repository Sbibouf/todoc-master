package com.cleanup.todoc.viewModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class MainViewModel extends ViewModel {

    /**
     * Repositories
     */

    private TaskRepository mTaskRepository;

    private ProjectRepository mProjectRepository;

    private final Executor mExecutor;

    /**
     * Data
     */

    @Nullable
    private LiveData<List<Task>> currentTasks;

    public MainViewModel(TaskRepository taskRepository, ProjectRepository projectRepository, Executor executor) {
        mTaskRepository = taskRepository;
        mProjectRepository = projectRepository;
        mExecutor = executor;
    }

    public void init(){

        if(this.currentTasks != null){

            return;
        }
        currentTasks = mTaskRepository.getAllTasks();
    }

    /**
     * Tasks
     * @return
     */
    @Nullable
    public LiveData<List<Task>> getCurrentTasks() {return this.currentTasks;}

    @Nullable
    public LiveData<List<Task>> getAllTasks() {return mTaskRepository.getAllTasks(); }





    public void createTask (Long projectId, String name, long creationTimestamp){
        mExecutor.execute(()->{
            mTaskRepository.createTask(new Task(projectId,name,creationTimestamp));
        });
    }


    public void deleteTask (Task task) {
        mExecutor.execute(()->mTaskRepository.deleteTask(task));
    }


    /**
     * Project
     * @param projectId
     * @return
     */

    public LiveData<Project> getProject(Long projectId) {
        return mProjectRepository.getProject(projectId);
    }
}
