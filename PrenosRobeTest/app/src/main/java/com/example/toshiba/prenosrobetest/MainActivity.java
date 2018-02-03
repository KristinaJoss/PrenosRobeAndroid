package com.example.toshiba.prenosrobetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements OnClickListener {

      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonSignIn).setOnClickListener(this);
        findViewById(R.id.buttonSignUp).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSignIn:
                this.doSignIn();
                break;
            case R.id.buttonSignUp:
                this.doSignUp();
                break;
        }
    }

    private void doSignIn() {
        Intent i;
        i = new Intent(MainActivity.this, RegistracijaActivity.class);

        //Bundle extra = new Bundle();

        //extra.putString("username", ((EditText) findViewById(R.id.inputUsername)).getText().toString());

       // i.putExtras(extra);

        startActivity(i);

    }

    private void doSignUp() {
        Intent i;
        i = new Intent(MainActivity.this, RegistracijaActivity.class);

        //Bundle extra = new Bundle();

       // extra.putString("username", ((EditText) findViewById(R.id.inputUsername)).getText().toString());

        //i.putExtras(extra);

        startActivity(i);

    }
}
