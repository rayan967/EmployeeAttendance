package com.example.employeeattendance.ViewModelFactory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.employeeattendance.ViewModel.ModifyViewModel;

public class ModifyViewModelFactory implements ViewModelProvider.Factory {

    private Application mApplication;
    private String ecode;
    private String epass;

    public ModifyViewModelFactory(Application application, String ecode,String epass) {
        mApplication = application;
        this.ecode=ecode;
        this.epass=epass;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ModifyViewModel(mApplication,ecode,epass);
    }
}
