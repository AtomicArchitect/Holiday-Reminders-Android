package com.nsight.holidayreminders.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;

import com.madrapps.pikolo.HSLColorPicker;
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener;
import com.nsight.holidayreminders.R;

import me.ibrahimsn.lib.SmoothBottomBar;

public class SettingsFragment extends Fragment {
    private SharedPreferences.Editor preferencesEditor;
    private ToggleButton buttonTheme;
    private TextView tvTheme;
    private HSLColorPicker hslColorPicker;
    private Button saveButton, defaultButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
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

        hslColorPicker = view.findViewById(R.id.hsl_color_picker);

        int color = preferences.getInt("main_color", 0);
        hslColorPicker.setColor(color == 0 ? Color.MAGENTA : color);

        hslColorPicker.setColorSelectionListener(new SimpleColorSelectionListener() {
            @Override
            public void onColorSelected(int color) {
                getActivity().getWindow().setNavigationBarColor(color);
                ((SmoothBottomBar) getActivity().findViewById(R.id.bottomNavigation)).setBarBackgroundColor(color);
            }
        });

        saveButton = view.findViewById(R.id.save_settings_btn);
        saveButton.setOnClickListener(view1 -> saveSettings());

        defaultButton = view.findViewById(R.id.default_settings_button);
        defaultButton.setOnClickListener(view12 -> defaultSettings());

        return view;
    }

    private void saveSettings() {
        preferencesEditor.putInt("main_color", getActivity().getWindow().getNavigationBarColor());
        preferencesEditor.apply();
        Toast.makeText(getActivity().getApplicationContext(), "Настройки сохранены", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("ResourceAsColor")
    private void defaultSettings() {
        preferencesEditor.putInt("main_color", 0);
        preferencesEditor.apply();
        Toast.makeText(getActivity().getApplicationContext(), "Настройки сброшены", Toast.LENGTH_SHORT).show();
        getActivity().recreate();
    }
}