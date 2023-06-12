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
    private LiveData<Task> currentTasks;

    public MainViewModel(TaskRepository taskRepository, ProjectRepository projectRepository, Executor executor) {
        mTaskRepository = taskRepository;
        mProjectRepository = projectRepository;
        mExecutor = executor;
    }

    public void init(long taskId){

        if(this.currentTasks != null){

            return;
        }
        currentTasks = mTaskRepository.getTask(taskId);
    }

    /**
     * Tasks
     * @return
     */
    @Nullable
    public LiveData<Task> getCurrentTasks() {return this.currentTasks;}



    public void createTask(long id, long taskId, String name, long creationTimestamp ) {

        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTaskRepository.createTask(new Task(id, taskId, name, creationTimestamp));
            }
        });
    }



    public void deleteTask(Task task) {

        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTaskRepository.deleteTask(task);
            }
        });
    }

    /**
     * Project
     * @param projectId
     * @return
     */

    public LiveData<Project> getProject(long projectId) {
        return mProjectRepository.getProject(projectId);
    }
}
