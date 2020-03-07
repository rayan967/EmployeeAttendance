package com.example.employeeattendance.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.employeeattendance.Fragment.ModifyFragment2;
import com.example.employeeattendance.Repository.EmployeeRepository;

import java.util.Map;

public class ModifyViewModel extends AndroidViewModel {

    MutableLiveData<Boolean> validate;
    MutableLiveData<Map<String,Object>> details;
    MutableLiveData<Boolean> changed;
    EmployeeRepository mRepository;

    public ModifyViewModel(Application application,String ecode,String epass){
        super(application);

    }

    public ModifyViewModel(Application application){
        super(application);

    }

    public void LogIn(String ecode, String epass)
    {
        mRepository=new EmployeeRepository(ecode,epass);
        validate=mRepository.getValidateResult();
        details=mRepository.getDetails();

    }

    public void modifyElements(Map<String, Object> user)
    {
        EmployeeRepository mRepository=new EmployeeRepository();
        changed=mRepository.isSuccessful();
        mRepository.modifyData(user);
    }

    public MutableLiveData<Boolean> getChanged()
    {
        return changed;
    }

    public void delete(Map<String, Object> user)
    {
        EmployeeRepository mRepository=new EmployeeRepository();
        mRepository.delete(user);
        changed=mRepository.isSuccessful();
    }



    public MutableLiveData<Boolean> getValidate(){
        return validate;
    }

    public MutableLiveData<Map<String,Object>> getDetails()
    {
        return details;
    }

}
