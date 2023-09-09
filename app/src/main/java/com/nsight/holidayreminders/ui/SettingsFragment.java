package com.nsight.holidayreminders.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;

import com.nsight.holidayreminders.R;

public class SettingsFragment extends Fragment {
    private SharedPreferences.Editor preferencesEditor;
    private ToggleButton buttonTheme;
    private TextView tvTheme;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint({"MissingInflatedId", "ResourceType"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        SharedPreferences preferences = getActivity().getSharedPreferences("hr_settings", Context.MODE_PRIVATE);
        preferencesEditor = preferences.edit();

        tvTheme = view.findViewById(R.id.tv_theme);
        buttonTheme = view.findViewById(R.id.button_theme);
        buttonTheme.setTextOn("OFF");
        buttonTheme.setTextOff("ON");

        if (preferences.getBoolean("theme_light", false)) {
            buttonTheme.setChecked(true);
            tvTheme.setText("Тема: светлая ");
        } else {
            buttonTheme.setChecked(false);
            tvTheme.setText("Тема: темная  ");
        }

        buttonTheme.setOnCheckedChangeListener((compoundButton, b) -> {
            preferencesEditor.putBoolean("theme_light", b);
            preferencesEditor.apply();
            getActivity().recreate();
        });
        return view;
    }
}