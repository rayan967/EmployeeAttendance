package com.example.employeeattendance.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.employeeattendance.Adapter.AttendanceLogItem;

import java.util.List;

@Dao
public interface LogDao {

    @Insert
    void insert(AttendanceLogItem np);

    @Query("DELETE FROM attendancelog_table")
    void deleteAll();

    @Query("SELECT * from attendancelog_table")
    LiveData<List<AttendanceLogItem>> getAllRows();

}
