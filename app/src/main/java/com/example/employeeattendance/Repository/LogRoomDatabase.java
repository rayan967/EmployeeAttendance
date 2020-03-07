package com.example.employeeattendance.Repository;

import android.content.Context;
import android.util.Log;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.employeeattendance.Adapter.AttendanceLogItem;
import java.util.HashMap;

@Database(entities = {AttendanceLogItem.class}, version = 1, exportSchema = false)
public abstract class LogRoomDatabase extends RoomDatabase {
    public abstract LogDao aDao();

    private static LogRoomDatabase INSTANCE;


    public static LogRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LogRoomDatabase.class) {
                if (INSTANCE == null) {
                    Log.d("Room Created", "true");
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LogRoomDatabase.class, "al_database")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                    INSTANCE.aDao().deleteAll();
                }
            }
        }
        return INSTANCE;
    }
}