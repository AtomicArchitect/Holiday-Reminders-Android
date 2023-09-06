package com.nsight.holidayreminders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {

    private SmoothBottomBar smoothBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new HomeFragment());
        fragmentTransaction.commit();

        smoothBottomBar = findViewById(R.id.bottomNavigation);
        smoothBottomBar.setOnItemSelectedListener((OnItemSelectedListener) i -> {
            FragmentManager fm = getSupportFragmentManager(); // При использовании внешних переменных крашится
            FragmentTransaction ft = fm.beginTransaction();
            switch (i) {
                case 0:
                    ft.replace(R.id.frameLayout, new HomeFragment());
                    ft.commit();
                    break;
                case 1:
                    ft.replace(R.id.frameLayout, new EditFragment());
                    ft.commit();
                    break;
                case 2:
                    ft.replace(R.id.frameLayout, new SettingsFragment());
                    ft.commit();
                    break;
            }
            return false;
        });
    }
}