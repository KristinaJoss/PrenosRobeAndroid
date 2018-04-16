package com.example.toshiba.prenosrobe.activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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
import com.example.toshiba.prenosrobe.fragments.NavigationFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity
{
    private ApiInterface apiInterface;
    private EditText inputName, inputSurname, inputPhone, inputEmail, inputUser, inputPass;
    private TextView labelMsg2;
    private static User user;
    private List<Language> languages;
    private List<Language> selectedLanguages = new ArrayList<>();
    private AlertDialog.Builder alertDialogBuilder;
    private boolean[] selectedTrueFalse;
    private Fragment navigationFragment;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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

        registerListeners();

        navigationFragment = new NavigationFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.bottom_navigation, navigationFragment);
        ft.commit();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        System.out.println("JOVU - onResume() REGISTRATION");

        getInitData();
        clearAllEditTexts();
        ((NavigationFragment) navigationFragment).setectItem(R.id.action_home);
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("JOVU - onPause() REGISTRATION");
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

    private boolean validateNewUser(final User newUser)
    {
        boolean isValid = true;

        if (newUser.getName().isEmpty())
        {
            inputName.setError(getResources().getString(R.string.name_empty));
            isValid = false;
        }
        if (newUser.getSurname().isEmpty())
        {
            inputSurname.setError(getResources().getString(R.string.surname_empty));
            isValid = false;
        }
        if (newUser.getUsername().isEmpty())
        {
            inputUser.setError(getResources().getString(R.string.surname_empty));
            isValid = false;
        }
        if (newUser.getPassword().isEmpty())
        {
            inputPass.setError(getResources().getString(R.string.password_empty));
            isValid = false;
        }
        if (newUser.getEmail().isEmpty())
        {
            inputEmail.setError(getResources().getString(R.string.email_empty));
            isValid = false;
        }
        if (newUser.getPhoneNumber().isEmpty())
        {
            inputPhone.setError(getResources().getString(R.string.phone_empty));
            isValid = false;
        }

        return isValid;
    }

    private void parseErrors(final List<String> errorList)
    {
        for(String error : errorList)
        {
            if (error.equals(getResources().getString(R.string.email_used)))
            {
                inputEmail.setText("");
            }
            if (error.equals(getResources().getString(R.string.email_format)))
            {
                inputEmail.setText("");
            }
            else if (error.equals(getResources().getString(R.string.username_used)))
            {
                inputUser.setText("");
            }
            else if (error.equals(getResources().getString(R.string.phone_used)))
            {
                inputPhone.setText("");
            }
        }
    }

    private void clearAllEditTexts()
    {
        inputName.setText("");
        inputName.setError(null);
        inputSurname.setText("");
        inputSurname.setError(null);
        inputPhone.setText("");
        inputPhone.setError(null);
        inputEmail.setText("");
        inputEmail.setError(null);
        inputUser.setText("");
        inputUser.setError(null);
        inputPass.setText("");
        inputPass.setError(null);

        labelMsg2.setText("");

        selectedLanguages.clear();
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

    private void registerListeners()
    {
        ((Button) findViewById(R.id.buttonRegister)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                User newUser = createUser();
                if (validateNewUser(newUser))
                {
                    apiInterface = ApiClient.getClient().create(ApiInterface.class);

                    Call<RestRespondeDto<User>> call = apiInterface.register(newUser);
                    call.enqueue(new Callback<RestRespondeDto<User>>()
                    {
                        @Override
                        public void onResponse(Call<RestRespondeDto<User>> call, Response<RestRespondeDto<User>> response)
                        {
                            if (response.code() == 201)
                            {
                                user = response.body().getData();
                                labelMsg2.setText("Pozdrav  " + response.code());

                                startNextActivity();
                            }
                            else if (response.code() == 208)
                            {
                                labelMsg2.setText(":((  " + response.code() + "  " + response.message());
                                parseErrors(response.body().getErrorList());

                                AlertDialog.Builder mBuilder = new AlertDialog.Builder(RegistrationActivity.this);
                                View mView = getLayoutInflater().inflate(R.layout.dialog_errors, null);
                                mView.findViewById(R.id.buttonOK).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog.dismiss();
                                    }
                                });

                                List<String> errorList = response.body().getErrorList();
                                List<TextView> errorTextViews = getErrorTextViews(mView);

                                int numberOfShownErrors = Math.min(errorTextViews.size(), errorList.size());
                                // Init text views
                                for (int i = 0; i < numberOfShownErrors; i++)
                                {
                                    errorTextViews.get(i).setText(errorList.get(i));
                                }

                                // Delete unused text views
                                for (int i = numberOfShownErrors; i < errorTextViews.size(); i++)
                                {
                                    errorTextViews.get(i).setHeight(0);
                                }

                                mBuilder.setView(mView);
                                alertDialog = mBuilder.create();
                                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

                                alertDialog.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RestRespondeDto<User>> call, Throwable t)
                        {
                            labelMsg2.setText("neeeeeeee");
                            t.printStackTrace();
                        }
                    });
                }
            }
        });

        ((Button) findViewById(R.id.buttonMultiSpinner)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                alertDialogBuilder = new AlertDialog.Builder(RegistrationActivity.this);

                String[] languagesNames = new String[languages.size()];
                selectedTrueFalse = new boolean[languages.size()];

                for (int i = 0; i < languages.size(); i++)
                {
                    languagesNames[i] = languages.get(i).getName();
                    selectedTrueFalse[i] = selectedLanguages.contains(languages.get(i));
                }

                alertDialogBuilder.setMultiChoiceItems(languagesNames, selectedTrueFalse, new DialogInterface.OnMultiChoiceClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {}
                });

                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setTitle("Selektujte jezike koje govorite.");

                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        selectedLanguages.clear();
                        for (int i = 0; i < selectedTrueFalse.length; i++)
                        {
                            boolean selected = selectedTrueFalse[i];

                            if (selected)
                                selectedLanguages.add(languages.get(i));
                        }
                    }
                });

                alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });

                AlertDialog dialog = alertDialogBuilder.create();
                dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.loginbg, null));

                dialog.show();
            }
        });
    }

    private List<TextView> getErrorTextViews(View mView)
    {
        List<TextView> errorTextViews = new ArrayList<>();

        errorTextViews.add((TextView) mView.findViewById(R.id.labelError1));
        errorTextViews.add((TextView) mView.findViewById(R.id.labelError2));
        errorTextViews.add((TextView) mView.findViewById(R.id.labelError3));
        errorTextViews.add((TextView) mView.findViewById(R.id.labelError4));
        errorTextViews.add((TextView) mView.findViewById(R.id.labelError5));

        return errorTextViews;
    }

    private void getInitData()
    {
        // Get all languages
        Call<RestRespondeDto<List<Language>>> call = apiInterface.getAllLanguages();
        call.enqueue(new Callback<RestRespondeDto<List<Language>>>() {
            @Override
            public void onResponse(Call<RestRespondeDto<List<Language>>> call, Response<RestRespondeDto<List<Language>>> response)
            {
                if (response.code() == 200)
                {
                    languages = response.body().getData();
                }
            }

            @Override
            public void onFailure(Call<RestRespondeDto<List<Language>>> call, Throwable t)
            {
                t.printStackTrace();
            }
        });
    }

    public static User getUser() { return user; }

    public static void setUser(User newUser) { user = newUser; }

    public void startNextActivity()
    {
        String nextActivityName = getIntent().getExtras().getString("class");
        Intent i = new Intent(RegistrationActivity.this, PopActivity.class);
        i.putExtra("class", nextActivityName);
        startActivity(i);
    }
}
