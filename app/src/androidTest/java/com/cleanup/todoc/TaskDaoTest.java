package com.cleanup.todoc;


import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

@RunWith(AndroidJUnit4ClassRunner.class)
public class TaskDaoTest {


    //For Data

    private TodocDatabase database;

    private static Long Task_Id= 1L;
    private static Project Project_Demo = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
    private static Task Task_Demo = new Task("Tache_test", new Date().getTime(), Project_Demo, 1L);



    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() {

    this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
            TodocDatabase.class)
            .allowMainThreadQueries()
            .build();}

    @After
    public void closeDb() throws Exception{
        database.close();
    }

    @Test
    public void insertAndGetTask() throws  InterruptedException{

        // Before : Adding a Task

        this.database.taskDao().createTask(Task_Demo);

        // Test

        Task task = LiveDataTestUtil.getValue(this.database.taskDao().getTasks(Task_Id));

        assertTrue(task.getName().equals(Task_Demo.getName())&& task.getId() == Task_Id) ;
    }
}
