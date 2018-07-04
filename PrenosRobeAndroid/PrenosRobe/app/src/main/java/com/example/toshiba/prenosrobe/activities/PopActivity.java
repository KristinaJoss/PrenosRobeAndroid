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
import com.example.toshiba.prenosrobe.data.DriverOffer;


public class PopActivity extends Activity
{
    private Button buttonOK;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup);

        buttonOK = findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startNextActivity();
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

    public void startNextActivity()
    {
        Bundle extras = getIntent().getExtras();
        String nextActivityName = extras.getString("class");
        Class<?> newActivityClass;
        try
        {
            newActivityClass = Class.forName(nextActivityName);
        } catch (ClassNotFoundException e)
        {
            newActivityClass = MainActivity.class;
        }
        Intent i = new Intent(PopActivity.this, newActivityClass);

        DriverOffer driverOffer = (DriverOffer) getIntent().getSerializableExtra("driver");
        if (driverOffer != null)
            i.putExtra("driver", driverOffer);

        startActivity(i);
    }
}