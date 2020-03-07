package com.example.employeeattendance.ViewModel;

import androidx.lifecycle.AndroidViewModel;
import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.employeeattendance.Adapter.AttendanceLogItem;
import com.example.employeeattendance.Fragment.ModifyFragment2;
import com.example.employeeattendance.Repository.EmployeeRepository;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AttendanceViewModel extends AndroidViewModel {

    MutableLiveData<Boolean> validate;
    EmployeeRepository mRepository;
    MutableLiveData<Boolean> changed;
    Application app;
    LiveData<List<AttendanceLogItem>> loglist;

    public AttendanceViewModel(Application application) {
        super(application);
        app=application;

    }

    public void LogIn(String ecode, String epass) {
        mRepository = new EmployeeRepository(ecode, epass);
        validate = mRepository.getValidateResult();

    }

    public MutableLiveData<Boolean> getValidate(){
        return validate;
    }

    public void recordAttendance(String ecode, String mode){
        mRepository = new EmployeeRepository();
        changed=mRepository.isSuccessful();
        mRepository.addToLog(ecode,mode);
    }
    public MutableLiveData<Boolean> getChanged()
    {
        return changed;
    }

    public void getAttendance()
    {
        mRepository=new EmployeeRepository();
        mRepository.getLogList(app);
        loglist=mRepository.getAllRows();
    }

    public LiveData<List<AttendanceLogItem>> getAllRows()
    {
        return loglist;
    }

    public static void delete(String time)
    {
        EmployeeRepository.deleteLogItem(time);
    }


    public static void writeCSV(List<AttendanceLogItem> NPList)
    {
        String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName = "AttendanceData.csv";
        String filePath = baseDir + File.separator + "Download" + File.separator + fileName;
        File f = new File(filePath);
        CSVWriter writer;

        try {


            String[] data = {"Name", "Mode", "Time"};

            if (f.exists() && !f.isDirectory()) {
                FileWriter mFileWriter = new FileWriter(filePath, false);
                writer = new CSVWriter(mFileWriter);
            } else {
                writer = new CSVWriter(new FileWriter(filePath));
            }

            writer.writeNext(data);

            for(int i=0;i<NPList.size();i++) {
                data=new String[3];
                data[0]=NPList.get(i).getName();
                data[1]=NPList.get(i).getMode();
                data[2]=NPList.get(i).getTime();
                writer.writeNext(data);
            }
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void writeTXT(List<AttendanceLogItem> NPList)
    {

        String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName = "AttendanceData.txt";
        String filePath = baseDir + File.separator + "Download" + File.separator + fileName;
        File f = new File(filePath);
        FileWriter writer;

        try {

            String data;

            if (f.exists() && !f.isDirectory()) {
                writer = new FileWriter(filePath, false);
            } else {
                writer = new FileWriter(filePath);
            }


            for(int i=0;i<NPList.size();i++) {
                data=i+". "+NPList.get(i).getName()+" "+NPList.get(i).getMode()+" at "+NPList.get(i).getTime()+"\n";
                writer.append(data);
            }
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
