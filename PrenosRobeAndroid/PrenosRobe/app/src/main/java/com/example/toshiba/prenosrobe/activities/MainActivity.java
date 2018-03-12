package com.example.toshiba.prenosrobe.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.toshiba.prenosrobe.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText inputUsername, inputPassword;
    TextView labelMsg1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputUsername = (EditText) findViewById(R.id.inputUsername);
        inputPassword = (EditText) findViewById(R.id.inputPassword);

        ((Button) findViewById(R.id.buttonSignIn)).setOnClickListener(this);
        ((Button) findViewById(R.id.buttonSignUp)).setOnClickListener(this);
        
        labelMsg1 = (TextView) findViewById(R.id.labelMsg1);
}

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.buttonSignIn:

                Intent i = new Intent(this, Home.class);
                startActivity(i);
                break;
                
            case R.id.buttonSignUp:

                Intent j = new Intent(this, Registration.class);
                startActivity(j);
                break;
        }
    }
}
