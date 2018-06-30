package com.example.toshiba.prenosrobe.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toshiba.prenosrobe.R;
import com.example.toshiba.prenosrobe.api.ApiClient;
import com.example.toshiba.prenosrobe.api.ApiInterface;
import com.example.toshiba.prenosrobe.fragments.FragmentBookings;
import com.example.toshiba.prenosrobe.fragments.FragmentMyOffers;
import com.example.toshiba.prenosrobe.fragments.FragmentComments;
import com.example.toshiba.prenosrobe.fragments.NavigationFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity
{
    private ApiInterface apiInterface;
    private TextView email, phone, nameSurname;
    private Fragment navigationFragment;
    private FloatingActionButton fab_menu, fab_logout, fab_settings;
    private Animation FabOpen, FabClose, FabRForward, FabRBackward;
    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        System.out.println("JOVU - onCreate() PROFILE");

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        email = findViewById(R.id.Email);
        phone = findViewById(R.id.Phone);
        nameSurname = findViewById(R.id.NameSurname);

        navigationFragment = new NavigationFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.bottom_navigation, navigationFragment);
        ft.commit();

        fab_menu = findViewById(R.id.fab_menu);
        fab_logout = findViewById(R.id.fab_logout);
        fab_settings = findViewById(R.id.fab_settings);
        FabOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        FabClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        FabRForward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        FabRBackward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);

        registerListeners();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        System.out.println("JOVU - onResume() PROFILE");

        ((NavigationFragment) navigationFragment).setectItem(R.id.action_profile);
        changeFragment(findViewById(R.id.buttonBookings));

        email.setText(RegistrationActivity.getUser().getEmail());
        phone.setText(RegistrationActivity.getUser().getPhoneNumber());
        nameSurname.setText(RegistrationActivity.getUser().getName() + " " + RegistrationActivity.getUser().getSurname());
        closeFloatingButtons();
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("JOVU - onPause() PROFILE");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("JOVU - onDestroy() PROFILE");
    }

    private void registerListeners()
    {
        fab_logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(ProfileActivity.this, "Logout", Toast.LENGTH_SHORT).show();

                String token = RegistrationActivity.getUser().getToken();
                Call<Void> call = apiInterface.logout(token);
                call.enqueue(new Callback<Void>()
                {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response)
                    {
                        if(response.code() == 200)
                        {
                            RegistrationActivity.setUser(null);
                            Intent j = new Intent(ProfileActivity.this, MainActivity.class);
                            startActivity(j);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t)
                    {
                        t.printStackTrace();
                    }
                });
            }
        });

        fab_menu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(isOpen)
                {
                    closeFloatingButtons();
                }
                else
                {
                    fab_settings.startAnimation(FabOpen);
                    fab_logout.startAnimation(FabOpen);
                    fab_menu.startAnimation(FabRForward);
                    fab_settings.setClickable(true);
                    fab_logout.setClickable(true);
                    isOpen = true;
                }
            }
        });
    }

    private void closeFloatingButtons()
    {
        fab_settings.startAnimation(FabClose);
        fab_logout.startAnimation(FabClose);
        fab_menu.startAnimation(FabRBackward);
        fab_settings.setClickable(false);
        fab_logout.setClickable(false);
        isOpen = false;
    }

    public void changeFragment(View view)
    {
        Fragment fragment;

        switch (view.getId())
        {
            case R.id.buttonBookings:
                fragment = new FragmentBookings();
                FragmentManager fm1 = getFragmentManager();
                FragmentTransaction ft1 = fm1.beginTransaction();
                ft1.replace(R.id.fragmentProfile, fragment);
                ft1.commit(); //kad god se pocne transakcija, ona mora da se komituje!
                break;

            case R.id.buttonMyOffer:
                fragment = new FragmentMyOffers();
                FragmentManager fm2 = getFragmentManager();
                FragmentTransaction ft2 = fm2.beginTransaction();
                ft2.replace(R.id.fragmentProfile, fragment);
                ft2.commit();
                break;

            case R.id.buttonComments:
                fragment = new FragmentComments();
                FragmentManager fm3 = getFragmentManager();
                FragmentTransaction ft3 = fm3.beginTransaction();
                ft3.replace(R.id.fragmentProfile, fragment);
                ft3.commit();
                break;
        }

        emphasizePressedButton(view.getId());
    }

    private void emphasizePressedButton(int buttonId)
    {
        ((Button) findViewById(R.id.buttonBookings)).setTextColor(Color.BLACK);
        ((Button) findViewById(R.id.buttonMyOffer)).setTextColor(Color.BLACK);
        ((Button) findViewById(R.id.buttonComments)).setTextColor(Color.BLACK);

        Button pressedButton = findViewById(buttonId);
        if (pressedButton != null)
            pressedButton.setTextColor(getResources().getColor(R.color.colorPrimary, null));
    }
}
