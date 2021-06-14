package com.example.appinspection.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.airbnb.lottie.LottieAnimationView;
import com.example.appinspection.R;
import com.example.appinspection.viewmodel.LoggedInViewModel;
import com.example.appinspection.viewmodel.MakeInspectionViewModel;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoggedInFragment extends Fragment {

    private TextView loggedInUserTextView;
    private Button logOutButton, makeInspectionButton, viewAllInspectionsButton;

    private DatabaseReference databaseReference;

    private LoggedInViewModel loggedInViewModel;

    private LottieAnimationView animationView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseReference = FirebaseDatabase.getInstance().getReference("Inspection");
        loggedInViewModel = new ViewModelProvider(this).get(LoggedInViewModel.class);


        loggedInViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    animationView.playAnimation();
                    loggedInUserTextView.setText("Hello User:" + firebaseUser.getEmail());

                    logOutButton.setEnabled(true);
                } else {
                    logOutButton.setEnabled(false);
                }
            }
        });




        loggedInViewModel.getLoggedOutMutableData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loggedOut) {
                if (loggedOut) {
                    animationView.playAnimation();
                    Toast.makeText(getContext(), "User Logged Out", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(getView()).navigate(R.id.action_loggedInFragment_to_loginRegisterFragment);
                }
            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.logged_in_fragment, container,false);

        loggedInUserTextView = view.findViewById(R.id.fragment_loggedin_loggedInUser);
        logOutButton = view.findViewById(R.id.fragment_loggedin_logOut);
        makeInspectionButton = view.findViewById(R.id.fragment_make_inspection);
        viewAllInspectionsButton = view.findViewById(R.id.fragment_see_all_inspection);
        animationView = view.findViewById(R.id.logged_in_animation);



        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationView.playAnimation();
                loggedInViewModel.logOut();
            }
        });

        makeInspectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                animationView.playAnimation();
                Navigation.findNavController(getView()).navigate(R.id.makeInspectionFragment);

            }
        });
        viewAllInspectionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationView.playAnimation();
                Navigation.findNavController(getView()).navigate(R.id.allInspectionsFragment);

            }
        });


        return view;
    }
}
