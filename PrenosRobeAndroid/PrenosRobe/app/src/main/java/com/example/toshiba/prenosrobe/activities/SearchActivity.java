package com.example.toshiba.prenosrobe.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.toshiba.prenosrobe.R;
import com.example.toshiba.prenosrobe.fragments.NavigationFragment;

public class SearchActivity extends AppCompatActivity
{
    private  Fragment navigationFragment;
    private FloatingActionButton fab_menu, fab_logout, fab_settings;
    private Animation FabOpen, FabClose, FabRForward, FabRBackward;
    private boolean isOpen = false;

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

        fab_menu = (FloatingActionButton) findViewById(R.id.fab_menu);
        fab_logout = (FloatingActionButton) findViewById(R.id.fab_logout);
        fab_settings = (FloatingActionButton) findViewById(R.id.fab_settings);
        FabOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        FabClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        FabRForward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        FabRBackward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        fab_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen)
                {
                    fab_settings.startAnimation(FabClose);
                    fab_logout.startAnimation(FabClose);
                    fab_menu.startAnimation(FabRBackward);
                    fab_settings.setClickable(false);
                    fab_logout.setClickable(false);
                    isOpen = false;
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

    @Override
    protected void onResume()
    {
        super.onResume();

        ((NavigationFragment) navigationFragment).setectItem(R.id.action_search);
    }
}
