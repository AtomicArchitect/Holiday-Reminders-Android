package com.nsight.holidayreminders;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {

    protected final Fragment fragmentHome = new HomeFragment();
    protected final Fragment fragmentEdit = new EditFragment();
    protected final Fragment fragmentSettings = new SettingsFragment();
    protected final FragmentManager fragmentManager = getSupportFragmentManager();
    protected Fragment activeFragment = fragmentHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager.beginTransaction().add(R.id.frameLayout, fragmentSettings, "settings").hide(fragmentSettings).commit();
        fragmentManager.beginTransaction().add(R.id.frameLayout, fragmentEdit, "edit").hide(fragmentEdit).commit();
        fragmentManager.beginTransaction().add(R.id.frameLayout, fragmentHome, "home").commit();

        SmoothBottomBar smoothBottomBar = findViewById(R.id.bottomNavigation);
        smoothBottomBar.setOnItemSelectedListener((OnItemSelectedListener) i -> {
            switch (i) {
                case 0:
                    fragmentManager.beginTransaction().hide(activeFragment).show(fragmentHome).commit();
                    activeFragment = fragmentHome;
                    break;
                case 1:
                    fragmentManager.beginTransaction().hide(activeFragment).show(fragmentEdit).commit();
                    activeFragment = fragmentEdit;
                    break;
                case 2:
                    fragmentManager.beginTransaction().hide(activeFragment).show(fragmentSettings).commit();
                    activeFragment = fragmentSettings;
                    break;
            }
            return false;
        });
    }

}