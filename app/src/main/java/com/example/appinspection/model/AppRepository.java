package com.example.appinspection.model;

import android.annotation.SuppressLint;
import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AppRepository {
    private Application application;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference userRef,inspectionRef;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<Boolean> loggedOutMutableLiveData;
    private MutableLiveData<FirebaseDatabase> firebaseDatabaseMutableLiveData;
    String currentUserID;
    public AppRepository(Application application){

        this.application = application;
        this.firebaseAuth = FirebaseAuth.getInstance();


        this.firebaseDatabaseMutableLiveData = new MutableLiveData<>();
        this.userLiveData = new MutableLiveData<>();
        this.loggedOutMutableLiveData = new MutableLiveData<>();


        if (firebaseAuth.getCurrentUser() != null) {
            userLiveData.postValue(firebaseAuth.getCurrentUser());
            loggedOutMutableLiveData.postValue(false);
            currentUserID = firebaseAuth.getCurrentUser().getUid();
        }


        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        inspectionRef = FirebaseDatabase.getInstance().getReference().child("Inspection");
    }



    @SuppressLint("NewApi")
    public void register(String email, String password)
    {
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {

                        if(task.isSuccessful())
                        {
                            userLiveData.postValue(firebaseAuth.getCurrentUser());
                        }
                        else {
                            Toast.makeText(application, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    @SuppressLint("NewApi")
    public void login(String email, String password)
    {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                if (task.isSuccessful())
                {
                    userLiveData.postValue(firebaseAuth.getCurrentUser());
                }
                else {
                    Toast.makeText(application, "Login failed", Toast.LENGTH_SHORT).show();
                }
                    }
                });
    }

    public void logout(){
       firebaseAuth.signOut();
       loggedOutMutableLiveData.setValue(true);
    }


    public MutableLiveData<FirebaseDatabase> getFirebaseDatabaseMutableLiveData() {

        return firebaseDatabaseMutableLiveData;
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutMutableLiveData() {
        return loggedOutMutableLiveData;
    }


    public void saveInspectionQ(String question1ans, String question2ans, String question3ans, String typeTitle, int totalRate)
    {
        userRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                System.out.println("PLLLLLLLL"+question1ans+"LSLSL"+question2ans);
                System.out.println("fmmmm"+inspectionRef);
                HashMap postMap = new HashMap();
                postMap.put("user_id", currentUserID);
                postMap.put("title", typeTitle);
                postMap.put("rating", String.valueOf(totalRate));
                postMap.put("q1", question1ans);
                postMap.put("q2", question2ans);
                postMap.put("q3", question3ans);
                inspectionRef.child(currentUserID+typeTitle).updateChildren(postMap)
                        .addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task)
                            {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(application, "Saved", Toast.LENGTH_SHORT).show();

                                }
                                else
                                {
                                    String message = task.getException().getMessage();
                                    Toast.makeText(application, "Error: "+message, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




}
