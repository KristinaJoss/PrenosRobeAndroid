package com.example.toshiba.prenosrobetest;

//import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
//import android.widget.EditText;

public class RegistracijaActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extra = getIntent().getExtras();

        String username = extra.getString("username");

        String message = "Korisnik" + " " + username + " " + "je registrovan.";

        //TextView poruka = (TextView) findViewById(R.id.textView2);

       // poruka.setText(message);

        /*EditText inputName = (EditText) findViewById(R.id.inputName);
        EditText inputSurname = (EditText) findViewById(R.id.inputSurname);
        EditText inputEmail = (EditText) findViewById(R.id.inputEmail);
        EditText inputPhone = (EditText) findViewById(R.id.inputPhone);
        EditText inputUsername = (EditText) findViewById(R.id.inputUsername);
        EditText inputPassword = (EditText) findViewById(R.id.inputPassword);
        EditText inputInfo = (EditText) findViewById(R.id.inputInfo);

        findViewById(R.id.buttonSignUp).setOnClickListener(this);

        //INTENT skoci na HOME*/
    }

   /* @Override
    public void onClick(View v) {
        if (view.getId() == R.id.buttonSignUp){
            //Intent i = new Intent(this, HomeActivity.class);

        }
    } */
}
