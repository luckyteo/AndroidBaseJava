package com.example.native_new.android.androidbasejava.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.native_new.android.androidbasejava.db.model.TasksEntity;

import java.util.List;

@Dao
public interface TasksDao {

    @Query("SELECT * FROM tasks")
    List<TasksEntity> getTasks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTaskEntity(List<TasksEntity> tasksEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTaskEntity(TasksEntity entity);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateTaskEntity(TasksEntity entity);

    @Delete
    void removeTaskEntity(TasksEntity entity);
}
