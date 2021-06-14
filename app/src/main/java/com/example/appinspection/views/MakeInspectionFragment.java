package com.example.appinspection.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.appinspection.R;
import com.example.appinspection.viewmodel.LoggedInViewModel;
import com.example.appinspection.viewmodel.MakeInspectionViewModel;
import com.google.firebase.auth.FirebaseUser;

public class MakeInspectionFragment extends Fragment
{
    private EditText question1EditText, question2EditText, question3EditText, typeTitleEditText;
    private EditText rate1, rate2, rate3;
    private Switch na1switch, na2switch, na3switch;
    private TextView username;
    private Button saveTheInspectionButton, tempSaveTheInspectionButton, tempClearTheInspectionButton;
    private LoggedInViewModel loggedInViewModel;
    private MakeInspectionViewModel makeInspectionViewModel;

    public static final String SAVED_QUESTIONS ="savedQuestions";

    public static final String TEXT_TITLE = "text_title";

    public static final String TEXT = "text";
    public static final String TEXT2 = "text2";
    public static final String TEXT3 = "text3";

    public static final String SWITCH1 = "switch1";
    public static final String SWITCH2 = "switch2";
    public static final String SWITCH3 = "switch3";

    public static final String RATING1 = "rating1";
    public static final String RATING2 = "rating2";
    public static final String RATING3 = "rating3";

    private String text, text2, text3, textTitle, rating1, rating2, rating3;
    private boolean switchOnOff1, switchOnOff2, switchOnOff3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        loggedInViewModel = new ViewModelProvider(this).get(LoggedInViewModel.class);


        loggedInViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    username.setText("User:" + firebaseUser.getEmail());

                }
            }
        });
        makeInspectionViewModel = new ViewModelProvider(this).get(MakeInspectionViewModel.class);


    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.make_inspection_fragment, container,false);


        question1EditText = view.findViewById(R.id.editTextQuestion1);
        question2EditText = view.findViewById(R.id.editTextQuestion2);
        question3EditText = view.findViewById(R.id.editTextQuestion3);

        typeTitleEditText = view.findViewById(R.id.editTextTextTypeTitle);

        rate1 = view.findViewById(R.id.editTextNumber2);
        rate2 = view.findViewById(R.id.editTextNumber3);
        rate3 = view.findViewById(R.id.editTextNumber4);


        na1switch = view.findViewById(R.id.na_button1);
        na2switch = view.findViewById(R.id.na_button2);
        na3switch = view.findViewById(R.id.na_button3);

        saveTheInspectionButton = view.findViewById(R.id.saveTheInspectionButton);
        tempSaveTheInspectionButton = view.findViewById(R.id.tempSaveTheInspectionButton);
        tempClearTheInspectionButton = view.findViewById(R.id.tempClearTheInspectionButton);

        username = view.findViewById(R.id.fragment_loggedin_loggedInUser_inspector);



        saveTheInspectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question1ans = question1EditText.getText().toString();
                String question2ans = question2EditText.getText().toString();
                String question3ans = question3EditText.getText().toString();

                String typeTitle = typeTitleEditText.getText().toString();


                if(question1ans.length() > 0 && question2ans.length() > 0 && question3ans.length() > 0 && typeTitle.length() > 0 && rate1.length()>0 && rate2.length()>0 && rate3.length()>0)
                {
                    int rate1val = Integer.parseInt(rate1.getText().toString());
                    int rate2val = Integer.parseInt(rate2.getText().toString());
                    int rate3val = Integer.parseInt(rate3.getText().toString());

                    int TotalRate = ((rate1val+rate2val+rate3val)/3);
                    makeInspectionViewModel.makeInspectionQ1(question1ans, question2ans, question3ans, typeTitle, TotalRate);
                    Navigation.findNavController(getView()).navigate(R.id.loggedInFragment);
                    SharedPreferences settings = getContext().getSharedPreferences(SAVED_QUESTIONS, Context.MODE_PRIVATE);
                    settings.edit().clear().commit();
                    Navigation.findNavController(getView()).navigate(R.id.loggedInFragment);
                }
                else {
                    Toast.makeText(getContext(), "Please answer every question!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        tempSaveTheInspectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    SaveData();
            }
        });

        tempClearTheInspectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getContext().getSharedPreferences(SAVED_QUESTIONS, Context.MODE_PRIVATE);
                settings.edit().clear().commit();
                Navigation.findNavController(getView()).navigate(R.id.loggedInFragment);
            }
        });

        na1switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked) {
                    rate1.setFocusable(false);
                    rate1.setText("0");
                }
                else
                {
                    rate1.setFocusable(true);
                }
            }
        });

        na2switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked) {
                    rate2.setFocusable(false);
                    rate2.setText("0");
                }
                else
                {
                    rate2.setFocusable(true);
                }
            }
        });

        na3switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked) {
                    rate3.setFocusable(false);
                    rate3.setText("0");
                }
                else
                {
                    rate3.setFocusable(true);
                }
            }
        });

        loadData();
        updateViews();

        return view;
    }

    private void SaveData()
    {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SAVED_QUESTIONS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT_TITLE, typeTitleEditText.getText().toString());
        editor.putString(TEXT, question1EditText.getText().toString());
        editor.putString(TEXT2, question2EditText.getText().toString());
        editor.putString(TEXT3, question3EditText.getText().toString());
        editor.putBoolean(SWITCH1, na1switch.isChecked());
        editor.putBoolean(SWITCH2, na2switch.isChecked());
        editor.putBoolean(SWITCH3, na3switch.isChecked());
        editor.putString(RATING1, rate1.getText().toString());
        editor.putString(RATING2, rate2.getText().toString());
        editor.putString(RATING3, rate3.getText().toString());
        editor.apply();

        Toast.makeText(getContext(), "Data temp saved", Toast.LENGTH_SHORT).show();
    }


    public void loadData()
    {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SAVED_QUESTIONS, Context.MODE_PRIVATE);
        textTitle = sharedPreferences.getString(TEXT_TITLE, "");
        text = sharedPreferences.getString(TEXT, "");
        text2 = sharedPreferences.getString(TEXT2, "");
        text3 = sharedPreferences.getString(TEXT3, "");
        switchOnOff1 = sharedPreferences.getBoolean(SWITCH1,false);
        switchOnOff2 = sharedPreferences.getBoolean(SWITCH2,false);
        switchOnOff3 = sharedPreferences.getBoolean(SWITCH3,false);
        rating1 = sharedPreferences.getString(RATING1, "");
        rating2 = sharedPreferences.getString(RATING2, "");
        rating3 = sharedPreferences.getString(RATING3, "");
    }
    public void updateViews()
    {
           typeTitleEditText.setText(textTitle);
           question1EditText.setText(text);
           question2EditText.setText(text2);
           question3EditText.setText(text3);
           na1switch.setChecked(switchOnOff1);
           na2switch.setChecked(switchOnOff2);
           na3switch.setChecked(switchOnOff3);
           rate1.setText(rating1);
           rate2.setText(rating2);
           rate3.setText(rating3);
    }

}
