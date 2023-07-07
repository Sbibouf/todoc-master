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

    /**
     * Sort method to help returning the proper livedata
     */

    private SortMethod tri;

    /**
     * The mediatorlivedata that allow to associate a task with a project from livedata
     */

    private final MediatorLiveData<List<TaskWithProject>> result = new MediatorLiveData<>();

    /**
     * A lists of task and project to use for merging livedata into mediatorlivedata
     */

    private List<Task> mTasks = new ArrayList<>();

    private List<Project> mProjects = new ArrayList<>();





    /**
     * Instantiates a new MainViewModel
     */



    public MainViewModel(TaskRepository taskRepository, ProjectRepository projectRepository, Executor executor) {
        mTaskRepository = taskRepository;
        mProjectRepository = projectRepository;
        mExecutor = executor;
        tri = SortMethod.NONE;

        LiveData<List<Project>> mLiveDataProject = projectRepository.getAllProject();
        LiveData<List<Task>> mLiveDataTask = taskRepository.getAllTasks();


        result.addSource(mLiveDataProject, new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
                mProjects=projects;
                merge();

            }
        });

        result.addSource(mLiveDataTask, newTasks->{
            mTasks = newTasks;
            merge();

        });


    }

    /**
     * Method that merge task and project together
     */

    private void merge(){
        List<TaskWithProject> taskWithProjects = new ArrayList<>();
        for(Task task : mTasks){
            for(Project project : mProjects){
                if(project.getId()==task.getProjectId()){
                    taskWithProjects.add(new TaskWithProject(task, project));
                    break;
                }
            }
        }
        result.setValue(taskWithProjects);


    }

    /**
     * Returning the livedata with the project associate to the task
     * @return
     */

    public LiveData<List<TaskWithProject>> getTaskWithProject(){

        return result;
    }





    /**
     * Return the proper livedata depending on the sort method
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


    /**
     * Method that create a Task
     * @param projectId
     * @param nameP
     * @param creationTimestamp
     */
    public void createTask(Long projectId, String nameP, long creationTimestamp) {
        mExecutor.execute(() -> {
            mTaskRepository.createTask(new Task(projectId, nameP, creationTimestamp));
        });
    }

    /**
     * Delete a task from base
     * @param task
     */

    public void deleteTask(Task task) {
        mExecutor.execute(() -> mTaskRepository.deleteTask(task));
    }


    /**
     * Change the sort method variable and return the livedata from repository
     * @return
     */
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


    /**
     * Return a livedata with the array of project for the spinner of the activity
     * @return
     */
    public LiveData<Project[]> getAllProjects() {
        return mProjectRepository.getAllProjects();
    }



}
