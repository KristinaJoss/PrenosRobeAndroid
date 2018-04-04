package com.example.toshiba.prenosrobe.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.toshiba.prenosrobe.R;
import com.example.toshiba.prenosrobe.activities.LogInActivity;
import com.example.toshiba.prenosrobe.activities.MainActivity;
import com.example.toshiba.prenosrobe.activities.OfferActivity;
import com.example.toshiba.prenosrobe.activities.ProfileActivity;
import com.example.toshiba.prenosrobe.activities.RegistrationActivity;

public class NavigationFragment extends Fragment
{
    private BottomNavigationView bottomNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_navigation, container, false);

        bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                Intent i;
                switch (item.getItemId())
                {
                    case R.id.action_home:
                        //Toast.makeText(getActivity(), "Home", Toast.LENGTH_SHORT).show();
                        i = new Intent(getActivity(), MainActivity.class);
                        startActivity(i);
                        break;

                    case R.id.action_search:
                        //Toast.makeText(getActivity(), "Pretraga", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.action_offer:
                        //Toast.makeText(getActivity(), "Ponude", Toast.LENGTH_SHORT).show();
                        if (RegistrationActivity.getUser() == null)
                        {
                            i = new Intent(getActivity(), LogInActivity.class);
                            i.putExtra("class", "com.example.toshiba.prenosrobe.activities.OfferActivity");
                        }
                        else
                            i = new Intent(getActivity(), OfferActivity.class);

                        startActivity(i);
                        break;

                    case R.id.action_notification:
                        //Toast.makeText(getActivity(), "Notifikacije", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.action_profile:
                        //Toast.makeText(getActivity(), "Profil", Toast.LENGTH_SHORT).show();
                        if (RegistrationActivity.getUser() == null)
                        {
                            i = new Intent(getActivity(), LogInActivity.class);
                            i.putExtra("class", "com.example.toshiba.prenosrobe.activities.ProfileActivity");
                        }
                        else
                            i = new Intent(getActivity(), ProfileActivity.class);

                        startActivity(i);
                        break;
                }
                return true;
            }
        });

        return view;
    }

    public void setectItem(final int selectedId)
    {
        bottomNavigationView.getMenu().findItem(selectedId).setChecked(true);
    }
}
