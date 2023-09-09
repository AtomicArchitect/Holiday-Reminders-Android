package com.nsight.holidayreminders;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {

    protected final Fragment fragmentHome = new HomeFragment();
    protected final Fragment fragmentEdit = new EditFragment();
    protected final Fragment fragmentSettings = new SettingsFragment();
    protected final FragmentManager fragmentManager = getSupportFragmentManager();
    protected int activeFragment = 0;
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

        SmoothBottomBar smoothBottomBar = findViewById(R.id.bottomNavigation);

        switch (preferences.getInt("active_fragment", 0)) {
            case 0:
                smoothBottomBar.setItemActiveIndex(0);
                fragmentManager.beginTransaction().replace(R.id.frameLayout, fragmentHome).commit();
                break;
            case 1:
                smoothBottomBar.setItemActiveIndex(1);
                fragmentManager.beginTransaction().replace(R.id.frameLayout, fragmentEdit).commit();
                activeFragment = 1;
                break;
            case 2:
                smoothBottomBar.setItemActiveIndex(2);
                fragmentManager.beginTransaction().replace(R.id.frameLayout, fragmentSettings).commit();
                activeFragment = 2;
                break;
        }

        smoothBottomBar.setOnItemSelectedListener((OnItemSelectedListener) i -> {
            switch (i) {
                case 0:
                    changeFragment(fragmentHome);
                    break;
                case 1:
                    changeFragment(fragmentEdit);
                    break;
                case 2:
                    changeFragment(fragmentSettings);
                    break;
            }
            return false;
        });
    }

    private int getIdFragment(Fragment fragment) {
        if (fragmentEdit == fragment) return 1;
        else if (fragmentSettings == fragment) return 2;
        return 0;
    }

    private Fragment getFragmentOnId(int id) {
        switch (id) {
            case 1:
                return fragmentEdit;
            case 2:
                return fragmentSettings;
            default:
                return fragmentHome;
        }
    }

    private void changeFragment(Fragment toFragment) {
        int idToFragment = getIdFragment(toFragment);
        if (activeFragment < idToFragment) {
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.left_in, R.anim.left_out, R.anim.right_in, R.anim.right_out)
                    .replace(R.id.frameLayout, toFragment).commit();
        } else if (activeFragment > idToFragment) {
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.right_in, R.anim.right_out, R.anim.left_in, R.anim.left_out)
                    .replace(R.id.frameLayout, toFragment).commit();
        }
        activeFragment = idToFragment;
    }

    @Override
    protected void onPause() {
        super.onPause();
        fragmentManager.beginTransaction().hide(getFragmentOnId(activeFragment)).commit();
        preferencesEditor.putInt("active_fragment", activeFragment);
        preferencesEditor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fragmentManager.beginTransaction().show(getFragmentOnId(activeFragment)).commit();
    }
}