package com.cleanup.todoc;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

@RunWith(AndroidJUnit4ClassRunner.class)
public class DaoTest {


    //For Data

    ProjectDao projectDao;
    TaskDao taskDao;

    private TodocDatabase database;

    private static Long Task_Id= 1L;
    private static Project Project_demo = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
    private static Task Task_Demo = new Task(1L,"Tache_test", 123);
    private static Task Task_Demo2 = new Task(1L, "A_Tache_test",124);



    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {


    database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
            TodocDatabase.class)
            .allowMainThreadQueries()
            .build();
    taskDao = database.taskDao();
    projectDao = database.projectDao();


    }

    @After
    public void closeDb() throws IOException {
        database.close();
    }

    @Test
    public void insertAndGetTask() throws  InterruptedException{

        // Before : Adding a Project and a Task

        projectDao.createProject(Project_demo);
        taskDao.createTask(Task_Demo);

        // Test

        Task task = LiveDataTestUtil.getValue(database.taskDao().getTask(Task_Id));

        assertTrue(task.getName().equals(Task_Demo.getName())&& task.getId() == Task_Id) ;
    }


    @Test
    public void deleteTask() throws InterruptedException {

        // Before : Create a Task and project in base
        projectDao.createProject(Project_demo);
        taskDao.createTask(Task_Demo);

        // Get the task and delete it

        Task task = LiveDataTestUtil.getValue(database.taskDao().getTask(Task_Id));
        taskDao.deleteTask(task.getId());

        // Test

        List<Task> list = LiveDataTestUtil.getValue(database.taskDao().getAllTasks());

        assertFalse(list.contains(task));
    }

    @Test
    public void orderTask() throws InterruptedException {

        // Before: Create 2 tasks and 1 project
        projectDao.createProject(Project_demo);
        taskDao.createTask(Task_Demo);
        taskDao.createTask(Task_Demo2);


        //Test

        List<Task> orderList = LiveDataTestUtil.getValue(database.taskDao().orderTaskAToZ());

        assertEquals("A_Tache_test", orderList.get(0).getName());




    }
}
