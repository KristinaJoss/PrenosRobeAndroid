package com.example.toshiba.prenosrobe.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import com.example.toshiba.prenosrobe.data.User;
import com.example.toshiba.prenosrobe.dto.RestRespondeDto;
import com.example.toshiba.prenosrobe.fragments.NavigationFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener
{
    private ApiInterface apiInterface;
    private EditText inputMail, inputPassword;
    private TextView labelMsg1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        inputMail = (EditText) findViewById(R.id.inputMail);
        inputPassword = (EditText) findViewById(R.id.inputPassword);

        ((Button) findViewById(R.id.buttonSignIn)).setOnClickListener(this);
        ((Button) findViewById(R.id.buttonSignUp)).setOnClickListener(this);
        
        labelMsg1 = (TextView) findViewById(R.id.labelMsg1);

        Fragment fragment = new NavigationFragment();
        ((NavigationFragment) fragment).setSelectedId(R.id.action_home);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.bottom_navigation, fragment);
        ft.commit();
}

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.buttonSignIn:

                login();
                break;
                
            case R.id.buttonSignUp:

                Intent j = new Intent(this, RegistrationActivity.class);
                String newActivityName = getIntent().getExtras().getString("class");
                j.putExtra("class", newActivityName);
                startActivity(j);
                break;
        }
    }

    private boolean validateNewUser(final User newUser)
    {
        boolean isValid = true;

        if (newUser.getPassword().isEmpty())
        {
            inputPassword.setError(getResources().getString(R.string.password_empty));
            isValid = false;
        }
        if (newUser.getEmail().isEmpty())
        {
            inputMail.setError(getResources().getString(R.string.email_empty));
            isValid = false;
        }

        return  isValid;
    }

    public void login()
    {
        User newUser = new User();
        newUser.setEmail(inputMail.getText().toString());
        newUser.setPassword(inputPassword.getText().toString());

        if (validateNewUser(newUser))
        {
            apiInterface = ApiClient.getClient().create(ApiInterface.class);

            Call<RestRespondeDto<User>> call = apiInterface.login(newUser);
            call.enqueue(new Callback<RestRespondeDto<User>>()
            {
                @Override
                public void onResponse(Call<RestRespondeDto<User>> call, Response<RestRespondeDto<User>> response)
                {
                    if (response.code() == 200)
                    {
                        RegistrationActivity.setUser(response.body().getData());

                        startNextActivity();
                    }
                    else if (response.code() == 204)
                    {
                        ((TextView) findViewById(R.id.labelMsg1)).setText(getResources().getString(R.string.password_email_used));
                        inputMail.setText("");
                        inputPassword.setText("");
                    }
                }

                @Override
                public void onFailure(Call<RestRespondeDto<User>> call, Throwable t)
                {
                    t.printStackTrace();
                }
            });
        }
    }

    public void startNextActivity()
    {
        Bundle extras = getIntent().getExtras();
        String nextActivityName = extras.getString("class");
        Class<?> newActivityClass;
        try
        {
            newActivityClass = Class.forName(nextActivityName);
        }
        catch (ClassNotFoundException e)
        {
            newActivityClass = MainActivity.class;
        }
        Intent i = new Intent(LogInActivity.this, newActivityClass);
        startActivity(i);
    }
}
