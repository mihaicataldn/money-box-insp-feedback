package com.example.appinspection.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.airbnb.lottie.LottieAnimationView;
import com.example.appinspection.model.AppRepository;
import com.google.firebase.auth.FirebaseUser;

public class LoginRegisterViewModel extends AndroidViewModel
{

    private AppRepository appRepository;
    private MutableLiveData<FirebaseUser> userLiveData;


    public LoginRegisterViewModel(@NonNull Application application)
    {
        super(application);

        appRepository = new AppRepository(application);
        userLiveData = appRepository.getUserLiveData();

    }


    public void  register(String email, String password, String name){
        appRepository.register(email, password);

    }


    public void login(String email, String password)
    {
        appRepository.login(email, password);
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }
}
