package com.cleanup.todoc.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cleanup.todoc.R;
import com.cleanup.todoc.databinding.ActivityMainBinding;
import com.cleanup.todoc.injection.ViewModelFactory;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.viewModel.MainViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <p>Home activity of the application which is displayed when the user opens the app.</p>
 * <p>Displays the list of tasks.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class MainActivity extends AppCompatActivity implements TasksAdapter.DeleteTaskListener {

    /**
     * The binding of all the elements in the activity
     */

    private ActivityMainBinding binding;

    /**
     * The ViewModel that deals with the data
     */

    private MainViewModel mMainViewModel;

    /**
     * The recyclerview adapter which handles the list of tasks
     */
    private TasksAdapter adapter;

    /**
     * Dialog to create a new task
     */
    @Nullable
    public AlertDialog dialog = null;

    /**
     * EditText that allows user to set the name of a task
     */
    @Nullable
    private EditText dialogEditText = null;


    /**
     * Spinner that allows the user to associate a project to a task
     */
    @Nullable
    private Spinner dialogSpinner = null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        configureViewModel();
        configureRecyclerView();
        getProjectToAdapter();
        verifPresenceTache();
        //getTasks();



        binding.fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTaskDialog();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.filter_alphabetical) {
            getTasksAToZ();
        } else if (id == R.id.filter_alphabetical_inverted) {
            getTasksZToA();
        } else if (id == R.id.filter_oldest_first) {
            getTasksOldToRecent();
        } else if (id == R.id.filter_recent_first) {
            getTasksRecentToOld();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeleteTask(Task task) {
        mMainViewModel.deleteTask(task);

    }

    /**
     * Called when the user clicks on the positive button of the Create Task Dialog.
     *
     * @param dialogInterface the current displayed dialog
     */
    private void onPositiveButtonClick(DialogInterface dialogInterface) {
        // If dialog is open
        if (dialogEditText != null && dialogSpinner != null) {
            // Get the name of the task
            String taskName = dialogEditText.getText().toString();

            // Get the selected project to be associated to the task
            Project taskProject = null;

            if (dialogSpinner.getSelectedItem() instanceof Project) {
                taskProject = (Project) dialogSpinner.getSelectedItem();
            }

            // If a name has not been set
            if (taskName.trim().isEmpty()) {
                dialogEditText.setError(getString(R.string.empty_task_name));
            }
            // If both project and name of the task have been set
            else if (taskProject != null) {

                mMainViewModel.createTask(taskProject.getId(),taskName, new Date().getTime());

                dialogInterface.dismiss();
            }
            // If name has been set, but project has not been set (this should never occur)
            else {
                dialogInterface.dismiss();
            }
        }
        // If dialog is already closed
        else {
            dialogInterface.dismiss();
        }
    }

    /**
     * Shows the Dialog for adding a Task
     */
    private void showAddTaskDialog() {
        final AlertDialog dialog = getAddTaskDialog();

        dialog.show();

        dialogEditText = dialog.findViewById(R.id.txt_task_name);
        dialogSpinner = dialog.findViewById(R.id.project_spinner);

        getProjectInSpinner();
    }




    /**
     * Returns the dialog allowing the user to create a new task.
     *
     * @return the dialog allowing the user to create a new task
     */
    @NonNull
    private AlertDialog getAddTaskDialog() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.Dialog);

        alertBuilder.setTitle(R.string.add_task);
        alertBuilder.setView(R.layout.dialog_add_task);
        alertBuilder.setPositiveButton(R.string.add, null);
        alertBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialogEditText = null;
                dialogSpinner = null;
                dialog = null;
            }
        });

        dialog = alertBuilder.create();

        // This instead of listener to positive button in order to avoid automatic dismiss
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        onPositiveButtonClick(dialog);
                    }
                });
            }
        });

        return dialog;
    }

    /**
     * Get all Projects in array from database and use it with the populateDialogSpinner method
     */

    private void getProjectInSpinner(){
        mMainViewModel.getAllProjects().observe(this, this::populateDialogSpinner);
    }

    /**
     * Get all Projects in a list from database and give it to the adapter
     */

    private void getProjectToAdapter(){

        mMainViewModel.getAllProject().observe(this, this::updateProject);
    }

    /**
     * Sets the data of the Spinner with projects to associate to a new task
     */
    private void populateDialogSpinner(Project[] projects) {

        final ArrayAdapter<Project> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,projects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (dialogSpinner != null) {
            dialogSpinner.setAdapter(adapter);
        }
    }



    public void configureViewModel() {

        this.mMainViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(MainViewModel.class);


    }

    public void configureRecyclerView() {

        adapter = new TasksAdapter(this);
        binding.listTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.listTasks.setAdapter(adapter);

    }

    private void getTasks() {
        this.mMainViewModel.getCurrentTasks().observe(this, this::updateTasks);
    }

    private void getTasksAToZ(){
        this.mMainViewModel.getTasksAToZ().observe(this, this::updateCurrentTask);
    }

    private void getTasksZToA(){
        this.mMainViewModel.getTasksZToA().observe(this, this::updateTasks);
    }

    private void getTasksRecentToOld(){
        this.mMainViewModel.getTasksRecentToOld().observe(this, this::updateTasks);
    }

    private void getTasksOldToRecent(){
        this.mMainViewModel.getTasksOldToRecent().observe(this, this::updateTasks);
    }


    private void updateCurrentTask(List<Task> tasks){
        updateTasks(tasks);
        //mMainViewModel.updateCurrentTask2(tasks);

    }

    private void updateTasks(List<Task> tasks) {
        this.adapter.updateTasks(tasks);

    }

    private void updateProject(List<Project> projects){
        this.adapter.updateProjects(projects);
    }

    private void verifPresenceTache() {

        mMainViewModel.getCurrentTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                if (tasks.size() == 0) {
                    binding.lblNoTask.setVisibility(View.VISIBLE);
                    binding.listTasks.setVisibility(View.GONE);
                } else {
                    binding.lblNoTask.setVisibility(View.GONE);
                    binding.listTasks.setVisibility(View.VISIBLE);
                    updateTasks(tasks);
                }
            }
        });

    }

}
