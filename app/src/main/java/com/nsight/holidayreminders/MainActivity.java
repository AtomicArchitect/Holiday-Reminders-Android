package com.nsight.holidayreminders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {

    protected final Fragment fragmentHome = new HomeFragment();
    protected final Fragment fragmentEdit = new EditFragment();
    protected final Fragment fragmentSettings = new SettingsFragment();
    protected final FragmentManager fragmentManager = getSupportFragmentManager();
    protected Fragment activeFragment = fragmentHome;
    private SharedPreferences preferences;
    private SharedPreferences.Editor preferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences("hr_settings", MODE_PRIVATE);
        preferencesEditor = preferences.edit();

        if (preferences.getBoolean("theme_light", false))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        setContentView(R.layout.activity_main);

        fragmentManager.beginTransaction().add(R.id.frameLayout, fragmentSettings, "settings").hide(fragmentSettings).commit();
        fragmentManager.beginTransaction().add(R.id.frameLayout, fragmentEdit, "edit").hide(fragmentEdit).commit();
        fragmentManager.beginTransaction().add(R.id.frameLayout, fragmentHome, "home").hide(fragmentHome).commit();

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

        switch (preferences.getString("active_fragment", "home")) {
            case "home":
                smoothBottomBar.setItemActiveIndex(0);
                fragmentManager.beginTransaction().hide(activeFragment).show(fragmentHome).commit();
                activeFragment = fragmentHome;
                break;
            case "edit":
                smoothBottomBar.setItemActiveIndex(1);
                fragmentManager.beginTransaction().hide(activeFragment).show(fragmentEdit).commit();
                activeFragment = fragmentEdit;
                break;
            case "settings":
                smoothBottomBar.setItemActiveIndex(2);
                fragmentManager.beginTransaction().hide(activeFragment).show(fragmentSettings).commit();
                activeFragment = fragmentSettings;
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        fragmentManager.beginTransaction().hide(activeFragment).commit();
        preferencesEditor.putString("active_fragment", activeFragment.getTag());
        preferencesEditor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fragmentManager.beginTransaction().show(activeFragment).commit();
    }
}