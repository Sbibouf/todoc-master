package com.cleanup.todoc.injection;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.database.dao.TodocDatabase;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;
import com.cleanup.todoc.viewModel.MainViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ViewModelFactory implements ViewModelProvider.Factory {

    /**
     * Repositories
     */

    private final TaskRepository mTaskRepository;
    private final ProjectRepository mProjectRepository;
    private final Executor mExecutor;

    private static volatile ViewModelFactory sFactory;

    public static ViewModelFactory getInstance(Context context) {
        if(sFactory == null) {
            synchronized (ViewModelFactory.class){
                if (sFactory == null) {
                    sFactory = new ViewModelFactory(context);
                }
            }
        }
        return sFactory;
    }

    /**
     * Constructor
     * @param context
     */

    private ViewModelFactory(Context context) {
        TodocDatabase todocDatabase = TodocDatabase.getInstance(context);
        this.mProjectRepository = new ProjectRepository(todocDatabase.projectDao());
        this.mTaskRepository = new TaskRepository(todocDatabase.taskDao());
        this.mExecutor = Executors.newSingleThreadExecutor();

    }



    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(mTaskRepository,mProjectRepository,mExecutor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
