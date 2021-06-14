package com.example.appinspection.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.appinspection.model.AppRepository;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MakeInspectionViewModel extends AndroidViewModel
{

    private AppRepository appRepository;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<Boolean> loggedOutMutableData;
    private MutableLiveData<FirebaseDatabase> databaseReferenceMutableLiveData;


    public MakeInspectionViewModel(@NonNull Application application)
    {
        super(application);

        appRepository = new AppRepository(application);
        userLiveData = appRepository.getUserLiveData();
        loggedOutMutableData = appRepository.getLoggedOutMutableLiveData();
        databaseReferenceMutableLiveData = appRepository.getFirebaseDatabaseMutableLiveData();

    }


    public MutableLiveData<Boolean> getLoggedOutMutableData() {
        return loggedOutMutableData;
    }


    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<FirebaseDatabase> getDatabaseReferenceMutableLiveData() {
        return databaseReferenceMutableLiveData;
    }

    public void makeInspectionQ1(String question1ans, String question2ans, String question3ans, String typeTitle, int totalRate) {
        appRepository.saveInspectionQ(question1ans, question2ans, question3ans, typeTitle, totalRate);
    }

    public void makeInspectionTemp(String question1ans, String question2ans, String question3ans, String typeTitle, String savedQuestions, String text) {
    }
}
