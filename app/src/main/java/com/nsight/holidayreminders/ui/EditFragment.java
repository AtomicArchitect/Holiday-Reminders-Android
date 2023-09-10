package com.nsight.holidayreminders.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.nsight.holidayreminders.R;
import com.nsight.holidayreminders.ui.edit.AddFragment;
import com.nsight.holidayreminders.ui.edit.DeleteFragment;
import com.nsight.holidayreminders.ui.edit.EditItemFragment;

public class EditFragment extends Fragment {

    private AddFragment addFragment = new AddFragment();
    private DeleteFragment deleteFragment = new DeleteFragment();
    private EditItemFragment editItemFragment = new EditItemFragment();
    private Button addButton, deleteButton, editItemButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener(view1 -> getChildFragmentManager().beginTransaction().replace(R.id.edit_fragment, addFragment).commit());

        deleteButton = view.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(view12 -> getChildFragmentManager().beginTransaction().replace(R.id.edit_fragment, deleteFragment).commit());

        editItemButton = view.findViewById(R.id.edit_button);
        editItemButton.setOnClickListener(view13 -> getChildFragmentManager().beginTransaction().replace(R.id.edit_fragment, editItemFragment).commit());
        return view;
    }
}