package com.example.employeeattendance.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.employeeattendance.Fragment.RegisterFragment;
import com.example.employeeattendance.Repository.EmployeeRepository;

public class RegisterViewModel extends AndroidViewModel {
    MutableLiveData<Boolean> changed;
    RegisterFragment mFragment;


    public RegisterViewModel(Application application, RegisterFragment fragment) {
        super(application);
        mFragment=fragment;
    }

    public void register(String ecode, String ename, String edob, String epass, String ephno, String eemail)
    {
        EmployeeRepository mRepository=new EmployeeRepository(ecode,ename,edob,epass,ephno,eemail, this);
        mRepository.register();
        changed=mRepository.isSuccessful();
    }

    public void onFailure()
    {
        mFragment.onFailure();
    }

    public MutableLiveData<Boolean> getChanged()
    {
        return changed;
    }

    public static String getDateString(int mYear,int mMonth,int mDay)
    {
        String date;
        if((mMonth+1)<10)
            date=(mDay + "/0" + (mMonth+1) + "/" + mYear);
        else
            date=(mDay + "/" + (mMonth+1) + "/" + mYear);
        return  date;
    }

    public static boolean textFieldValidation(String ecode, String ename, String edob, String epass, String ephno, String eemail)
    {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        return !ecode.equals("")&&!ename.equals("")&&!edob.equals("")&&!epass.equals("")&&epass.length()>=6&&!ephno.equals("")&&!eemail.equals("")&eemail.trim().matches(emailPattern);
    }

}
