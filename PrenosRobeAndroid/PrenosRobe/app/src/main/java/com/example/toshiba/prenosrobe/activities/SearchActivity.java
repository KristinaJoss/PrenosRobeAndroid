package com.example.toshiba.prenosrobe.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.toshiba.prenosrobe.R;
import com.example.toshiba.prenosrobe.fragments.NavigationFragment;

public class SearchActivity extends AppCompatActivity
{
    private  Fragment navigationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        navigationFragment = new NavigationFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.bottom_navigation, navigationFragment);
        ft.commit();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        ((NavigationFragment) navigationFragment).setectItem(R.id.action_search);
    }
}
