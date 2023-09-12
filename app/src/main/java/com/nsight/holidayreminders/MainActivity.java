package com.nsight.holidayreminders;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.nsight.holidayreminders.ui.EditFragment;
import com.nsight.holidayreminders.ui.HomeFragment;
import com.nsight.holidayreminders.ui.SettingsFragment;

import java.util.Calendar;

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

        int main_color = preferences.getInt("main_color", 0);
        if (main_color != 0) {
            getWindow().setNavigationBarColor(main_color);
            smoothBottomBar.setBarBackgroundColor(main_color);
        }

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

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent(this, AlarmReceiver.class);
        @SuppressLint("WrongConstant")
        PendingIntent alarm = PendingIntent.getBroadcast(this, 0, intent, Intent.FLAG_ACTIVITY_CLEAR_TASK | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (preferences.getBoolean("notifications", false)) {
            String[] timeArray = preferences.getString("notification_time", "9:00").split(":");
            Calendar now = Calendar.getInstance();
            now.set(Calendar.SECOND, 0);
            now.set(Calendar.MILLISECOND, 0);
            Calendar targetTime = now;
            targetTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
            targetTime.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
            if (targetTime.before(now)) targetTime.add(Calendar.DATE, 1);
            alarmManager.cancel(alarm);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, targetTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarm);
        } else {
            alarmManager.cancel(alarm);
        }
    }
}