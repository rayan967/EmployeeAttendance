package com.example.employeeattendance.ViewModelFactory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.employeeattendance.Fragment.RegisterFragment;
import com.example.employeeattendance.ViewModel.RegisterViewModel;

public class RegisterViewModelFactory implements ViewModelProvider.Factory {

    private Application mApplication;
    private RegisterFragment mFragment;
    private String ecode;
    private String ename;
    private String edob;
    private String epass;
    private String ephno;
    private String eemail;

    public RegisterViewModelFactory(Application application, RegisterFragment fragment) {
        mApplication = application;
        mFragment = fragment;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RegisterViewModel(mApplication,mFragment);
    }
}
