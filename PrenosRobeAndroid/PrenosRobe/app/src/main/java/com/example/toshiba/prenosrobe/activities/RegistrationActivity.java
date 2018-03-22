package com.example.toshiba.prenosrobe.activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.toshiba.prenosrobe.R;
import com.example.toshiba.prenosrobe.api.ApiClient;
import com.example.toshiba.prenosrobe.api.ApiInterface;
import com.example.toshiba.prenosrobe.data.Language;
import com.example.toshiba.prenosrobe.data.User;
import com.example.toshiba.prenosrobe.data.UserLanguage;
import com.example.toshiba.prenosrobe.dto.RestRespondeDto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private ApiInterface apiInterface;
    private EditText inputName, inputSurname, inputPhone, inputEmail, inputUser, inputPass;
    private TextView labelMsg2;
    private static User user;
    private List<Language> languages;
    private List<Language> selectedLanguages = new ArrayList<>();
    private AlertDialog.Builder alertdialogbuilder;
    private boolean[] selectedTrueFalse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        inputName = (EditText) findViewById(R.id.inputName);
        inputSurname = (EditText) findViewById(R.id.inputSurname);
        inputUser = (EditText) findViewById(R.id.inputUser);
        inputPass = (EditText) findViewById(R.id.inputPass);
        inputPhone = (EditText) findViewById(R.id.inputPhone);
        inputEmail = (EditText) findViewById(R.id.inputEmail);

        labelMsg2 = (TextView) findViewById(R.id.labelMsg2);

        ((Button) findViewById(R.id.buttonRegister)).setOnClickListener(this);
        ((Button) findViewById(R.id.buttonMultiSpinner)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                alertdialogbuilder = new AlertDialog.Builder(RegistrationActivity.this);

                String[] languagesNames = new String[languages.size()];
                selectedTrueFalse = new boolean[languages.size()];

                for (int i = 0; i < languages.size(); i++)
                {
                    languagesNames[i] = languages.get(i).getName();
                    selectedTrueFalse[i] = selectedLanguages.contains(languages.get(i));
                }

                alertdialogbuilder.setMultiChoiceItems(languagesNames, selectedTrueFalse, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                    }
                });

                alertdialogbuilder.setCancelable(false);
                alertdialogbuilder.setTitle("Selektujte jezike koje govorite.");

                alertdialogbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        selectedLanguages.clear();
                        for (int i = 0; i < selectedTrueFalse.length; i++)
                        {
                            boolean selected = selectedTrueFalse[i];

                            if (selected)
                                selectedLanguages.add(languages.get(i));
                        }
                    }
                });

                alertdialogbuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = alertdialogbuilder.create();

                dialog.show();
            }
        });

        getInitData();
    }

    @Override
    public void onClick(View view) {

        User newUser = createUser();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<RestRespondeDto<User>> call = apiInterface.register(newUser);
        call.enqueue(new Callback<RestRespondeDto<User>>() {
            @Override
            public void onResponse(Call<RestRespondeDto<User>> call, Response<RestRespondeDto<User>> response) {
                if(response.code() == 201){
                    user = response.body().getData();
                    labelMsg2.setText("Pozdrav  " + response.code());
                }
                else
                {
                    labelMsg2.setText(":((  " + response.code() + "  " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RestRespondeDto<User>> call, Throwable t) {
                labelMsg2.setText("neeeeeeee");
                t.printStackTrace();
            }
        });

        startActivity(new Intent(RegistrationActivity.this, PopActivity.class));
    }

    private User createUser()
    {
        User newUser = new User();
        newUser.setName(inputName.getText().toString());
        newUser.setSurname(inputSurname.getText().toString());
        newUser.setUsername(inputUser.getText().toString());
        newUser.setPassword(inputPass.getText().toString());
        newUser.setEmail(inputEmail.getText().toString());
        newUser.setPhoneNumber(inputPhone.getText().toString());
        newUser.setUserLanguages(getUserLanguagesByLanguages());

        return newUser;
    }

    private List<UserLanguage> getUserLanguagesByLanguages()
    {
        List<UserLanguage> userLanguages = new ArrayList<>();
        for(Language language : selectedLanguages)
        {
            UserLanguage userLanguage = new UserLanguage(null, language);
            userLanguages.add(userLanguage);
        }

        return userLanguages;
    }

    private void getInitData() {
        // Get all languages
        Call<RestRespondeDto<List<Language>>> call = apiInterface.getAllLanguages();
        call.enqueue(new Callback<RestRespondeDto<List<Language>>>() {
            @Override
            public void onResponse(Call<RestRespondeDto<List<Language>>> call, Response<RestRespondeDto<List<Language>>> response) {
                if (response.code() == 200) {
                    languages = response.body().getData();
                }
            }

            @Override
            public void onFailure(Call<RestRespondeDto<List<Language>>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static User getUser() { return user; }

    public static void setUser(User newUser) { user = newUser; }
}
