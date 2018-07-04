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
import com.example.toshiba.prenosrobe.data.DriverOffer;
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
    private Fragment navigationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        inputMail = findViewById(R.id.inputMail);
        inputPassword = findViewById(R.id.inputPassword);
        labelMsg1 = findViewById(R.id.labelMsg1);

        findViewById(R.id.buttonSignIn).setOnClickListener(this);
        findViewById(R.id.buttonSignUp).setOnClickListener(this);

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

        clearAllEditTexts();
        ((NavigationFragment) navigationFragment).setectItem(R.id.action_home);
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

                Intent intentRegistration = new Intent(this, RegistrationActivity.class);
                String newActivityName = getIntent().getExtras().getString("class");
                intentRegistration.putExtra("class", newActivityName);

                DriverOffer driverOffer = (DriverOffer) getIntent().getSerializableExtra("driver");
                if (driverOffer != null)
                    intentRegistration.putExtra("driver", driverOffer);

                startActivity(intentRegistration);
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
                        labelMsg1.setText(getResources().getString(R.string.password_email_used));
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

        DriverOffer driverOffer = (DriverOffer) getIntent().getSerializableExtra("driver");
        if (driverOffer != null)
            i.putExtra("driver", driverOffer);

        startActivity(i);
    }

    private void clearAllEditTexts()
    {
        inputMail.setText("");
        inputMail.setError(null);
        inputPassword.setText("");
        inputPassword.setError(null);
        labelMsg1.setText("");
    }
}
