package com.cleanup.todoc;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for tasks
 *
 * @author Gaëtan HERFRAY
 */
public class TaskUnitTest {
    @Test
    public void test_projects() {
        final Task task1 = new Task(1L, "task 1", new Date().getTime());
        final Task task2 = new Task(2L,"task 2", new Date().getTime());
        final Task task3 = new Task(3L,"task 3", new Date().getTime());
        final Task task4 = new Task(1L,"task 4", new Date().getTime());

        assertSame(1L, task1.getProjectId());
        assertSame(2L, task2.getProjectId());
        assertSame(3L, task3.getProjectId());

    }

    @Test
    public void test_az_comparator() {
        final Task task1 = new Task(1L,"aaa", 123);
        final Task task2 = new Task(1L,"zzz", 124);
        final Task task3 = new Task(1L,"hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskAZComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task2);
    }

    @Test
    public void test_za_comparator() {
        final Task task1 = new Task(1L,"aaa", 123);
        final Task task2 = new Task(1L,"zzz", 124);
        final Task task3 = new Task(1L,"hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskZAComparator());

        assertSame(tasks.get(0), task2);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_recent_comparator() {
        final Task task1 = new Task(1L,"aaa", 123);
        final Task task2 = new Task(1L,"zzz", 124);
        final Task task3 = new Task(1L,"hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskRecentComparator());

        assertSame(tasks.get(0), task3);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_old_comparator() {
        final Task task1 = new Task(1L,"aaa", 123);
        final Task task2 = new Task(1L,"zzz", 124);
        final Task task3 = new Task(1L,"hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskOldComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task3);
    }
}