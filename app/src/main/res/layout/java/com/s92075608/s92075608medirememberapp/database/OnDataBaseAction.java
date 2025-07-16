package com.s92075608.s92075608medirememberapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.s92075608.s92075608medirememberapp.Model.Task;

import java.util.List;
//provides a set of methods that can be used to perform CRUD (create, read, update, delete)
@Dao
public interface OnDataBaseAction {
//get task list
    @Query("SELECT * FROM Task")
    List<Task> getAllTasksList();
//delete medication
    @Query("DELETE FROM Task")
    void truncateTheList();
//insert data
    @Insert
    void insertDataIntoTaskList(Task task);
//find and delete medication
    @Query("DELETE FROM Task WHERE taskId = :taskId")
    void deleteTaskFromId(int taskId);
//select medication
    @Query("SELECT * FROM Task WHERE taskId = :taskId")
    Task selectDataFromAnId(int taskId);
//update medication description
    @Query("UPDATE Task SET taskTitle = :taskTitle, taskDescription = :taskDescription, date = :taskDate, " +
            "lastAlarm = :taskTime, event = :taskEvent WHERE taskId = :taskId")
    void updateAnExistingRow(int taskId, String taskTitle, String taskDescription , String taskDate, String taskTime,
                            String taskEvent);

}
