package com.example.toshiba.prenosrobe.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toshiba.prenosrobe.R;
import com.example.toshiba.prenosrobe.api.ApiClient;
import com.example.toshiba.prenosrobe.api.ApiInterface;
import com.example.toshiba.prenosrobe.fragments.FragmentBookings;
import com.example.toshiba.prenosrobe.fragments.FragmentMyOffers;
import com.example.toshiba.prenosrobe.fragments.NavigationFragment;

import br.com.bloder.magic.view.MagicButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileActivity extends AppCompatActivity {

    private ApiInterface apiInterface;
    private MagicButton magicButton;
    private TextView email, phone, nameSurname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        email = (TextView) findViewById(R.id.Email);
        phone = (TextView) findViewById(R.id.Phone);
        nameSurname = (TextView) findViewById(R.id.NameSurname);

        email.setText(RegistrationActivity.getUser().getEmail());
        phone.setText(RegistrationActivity.getUser().getPhoneNumber());
        nameSurname.setText(RegistrationActivity.getUser().getName() + " " + RegistrationActivity.getUser().getSurname());

        Fragment navfragment = new NavigationFragment();
        ((NavigationFragment) navfragment).setSelectedId(R.id.action_profile);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.bottom_navigation, navfragment);
        ft.commit();

        magicButton = (MagicButton) findViewById(R.id.magicButton);
        magicButton.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "Logout", Toast.LENGTH_SHORT).show();

                String token = RegistrationActivity.getUser().getToken();
                Call<Void> call = apiInterface.logout(token);
                call.enqueue(new Callback<Void>() {
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
