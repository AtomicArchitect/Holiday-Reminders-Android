package com.nsight.holidayreminders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SettingsFragment extends Fragment {
    private SharedPreferences.Editor sharedPreferencesEditor;
    private Button buttonTheme;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint({"MissingInflatedId", "ResourceType"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        sharedPreferencesEditor = getActivity().getSharedPreferences("hr_settings", Context.MODE_PRIVATE).edit();
        buttonTheme = view.findViewById(R.id.button_theme);
        buttonTheme.setOnClickListener(view_btn -> {
            int currentTheme = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            sharedPreferencesEditor.putBoolean("theme_light", currentTheme != Configuration.UI_MODE_NIGHT_NO);
            sharedPreferencesEditor.apply();
            getActivity().recreate();
        });
        return view;
    }
}