package com.example.appinspection.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.airbnb.lottie.LottieAnimationView;
import com.example.appinspection.R;
import com.example.appinspection.viewmodel.LoginRegisterViewModel;
import com.google.firebase.auth.FirebaseUser;

public class LoginRegisterFragment extends Fragment {
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText nameEditText;
    private Button loginButton;
    private Button registerButton;
    private LottieAnimationView animationView;

    private LoginRegisterViewModel loginRegisterViewModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginRegisterViewModel = new ViewModelProvider(this).get(LoginRegisterViewModel.class);

        loginRegisterViewModel.getUserLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Navigation.findNavController(getView()).navigate(R.id.action_loginRegisterFragment_to_loggedInFragment);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_register, container, false);

        emailEditText = view.findViewById(R.id.fragment_loginregister_email);
        passwordEditText = view.findViewById(R.id.fragment_loginregister_password);
        nameEditText = view.findViewById(R.id.fragment_login_register_name);
        loginButton = view.findViewById(R.id.fragment_loginregister_login);
        registerButton = view.findViewById(R.id.fragment_loginregister_register);
        animationView = view.findViewById(R.id.animation);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String name = nameEditText.getText().toString();
                if(email.length()> 0 && password.length() >0 && name.length() >0)
                {
                    animationView.playAnimation();
                    loginRegisterViewModel.register(email, password, name);
                    Toast.makeText(getContext(), "Hello "+name, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Please input all the fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String name = nameEditText.getText().toString();
                if(email.length()> 0 && password.length() >0 && name.length() >0)
                {
                    animationView.playAnimation();
                    loginRegisterViewModel.login(email, password);
                    Toast.makeText(getContext(), "Hello "+name, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Please input all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}
