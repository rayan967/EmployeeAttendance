package com.example.employeeattendance.Adapter;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "attendancelog_table")
public class AttendanceLogItem {

        @PrimaryKey(autoGenerate = true)
        public int key;
        @ColumnInfo(name = "Name")
        public String name;
        @ColumnInfo(name = "Mode")
        public String mode;
        @ColumnInfo(name = "Time")
        public String time;


        public AttendanceLogItem(String name, String mode, String time) {
            this.name=name;
            this.mode=mode;
            this.time=time;
        }
        public AttendanceLogItem(){}


        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }


        public String getMode() {
            return mode;
        }
        public void setMode(String mode) {
            this.mode = mode;
        }


        public String getTime() {
            return time;
        }
        public void setTime(String time) {
            this.time = time;
        }
        

}
