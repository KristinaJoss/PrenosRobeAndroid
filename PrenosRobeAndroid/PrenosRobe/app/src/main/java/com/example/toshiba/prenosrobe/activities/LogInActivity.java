package com.example.toshiba.prenosrobe.activities;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private ApiInterface apiInterface;
    EditText inputMail, inputPassword;
    TextView labelMsg1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        inputMail = (EditText) findViewById(R.id.inputMail);
        inputPassword = (EditText) findViewById(R.id.inputPassword);

        ((Button) findViewById(R.id.buttonSignIn)).setOnClickListener(this);
        ((Button) findViewById(R.id.buttonSignUp)).setOnClickListener(this);
        
        labelMsg1 = (TextView) findViewById(R.id.labelMsg1);
}

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.buttonSignIn:

                login();
                break;
                
            case R.id.buttonSignUp:

                Intent j = new Intent(this, RegistrationActivity.class);
                startActivity(j);
                break;
        }
    }

    public void login() {
        User newUser = new User();
        newUser.setEmail(inputMail.getText().toString());
        newUser.setPassword(inputPassword.getText().toString());

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<User> call = apiInterface.login(newUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    RegistrationActivity.setUser(response.body());

                    Intent i = new Intent(LogInActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
