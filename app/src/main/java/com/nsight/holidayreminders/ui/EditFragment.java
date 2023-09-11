package com.nsight.holidayreminders.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.nsight.holidayreminders.R;
import com.nsight.holidayreminders.ui.edit.AddFragment;
import com.nsight.holidayreminders.ui.edit.DeleteFragment;

public class EditFragment extends Fragment {

    private AddFragment addFragment = new AddFragment();
    private DeleteFragment deleteFragment = new DeleteFragment();
    private Button addButton, deleteButton;
    private Fragment lastFragment;
    private TextView title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        title = view.findViewById(R.id.edit_fragment_title);

        addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener(view1 -> {
            lastFragment = addFragment;
            update();
        });

        deleteButton = view.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(view12 -> {
            lastFragment = deleteFragment;
            update();
        });

        update();
        return view;
    }

    private void update() {
        if (lastFragment != null) {
            title.setText(lastFragment == addFragment ? "Добавить" : "Удалить");
            getChildFragmentManager().beginTransaction().replace(R.id.edit_fragment, lastFragment).commit();
        }
    }
}