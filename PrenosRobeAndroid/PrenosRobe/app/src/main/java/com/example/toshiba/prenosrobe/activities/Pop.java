package com.example.toshiba.prenosrobe.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import com.example.toshiba.prenosrobe.R;


public class Pop extends Activity {

    Button buttonOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup);

        buttonOK = (Button) findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Pop.this, Home.class));
            }
        });

        /* Podesavanje velicine PopUp prozora tako sto se uzme velicina naseg ekrana
        * u pikselima i kreiraju se nove vrednosti na osnovu njih.
        * Uzima se rezolucija ekrana i velicina prozora se definise kao
        * procenat te rezolucije. */

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.4)); //sirina 80% ekrana, visina 60% ekrana

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);


    }

}