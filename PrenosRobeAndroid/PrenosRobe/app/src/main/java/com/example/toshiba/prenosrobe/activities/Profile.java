package com.example.toshiba.prenosrobe.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.View;
import com.example.toshiba.prenosrobe.R;


public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void ChangeFragment (View view){
        Fragment fragment;

        if (view == findViewById(R.id.buttonBookings)){
            fragment = new FragmentBookings();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragmentBookings, fragment);
            ft.commit(); //kad god se pocne transakcija, ona mora da se komituje!
        }

        else if (view == findViewById(R.id.buttonMyOffer)){
            fragment = new FragmentMyOffers();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragmentBookings, fragment);
            ft.commit();
        }
    }

}
