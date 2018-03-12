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

public class Registration extends AppCompatActivity implements View.OnClickListener {

    private ApiInterface apiInterface;
    private EditText inputName, inputSurname, inputPhone, inputEmail, inputUser, inputPass;
    private TextView labelMsg2;
    private static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        inputName = (EditText) findViewById(R.id.inputName);
        inputSurname = (EditText) findViewById(R.id.inputSurname);
        inputUser = (EditText) findViewById(R.id.inputUser);
        inputPass = (EditText) findViewById(R.id.inputPass);
        inputPhone = (EditText) findViewById(R.id.inputPhone);
        inputEmail = (EditText) findViewById(R.id.inputEmail);

        labelMsg2 = (TextView) findViewById(R.id.labelMsg2);

        ((Button) findViewById(R.id.buttonRegister)).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        User newUser = new User();
        newUser.setName(inputName.getText().toString());
        newUser.setSurname(inputSurname.getText().toString());
        newUser.setUsername(inputUser.getText().toString());
        newUser.setPassword(inputPass.getText().toString());
        newUser.setEmail(inputEmail.getText().toString());
        newUser.setPhoneNumber(inputPhone.getText().toString());

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<User> call = apiInterface.register(newUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code() == 201){
                    user = response.body();
                    labelMsg2.setText("Pozdrav  " + response.code());
                }
                else
                {
                    labelMsg2.setText(":((  " + response.code() + "  " + response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                labelMsg2.setText("neeeeeeee");
                t.printStackTrace();
            }
        });

        startActivity(new Intent(Registration.this, Pop.class));
    }

    public static User getUser() { return user; }

    public static void setUser(User newUser) { user = newUser; }
}
