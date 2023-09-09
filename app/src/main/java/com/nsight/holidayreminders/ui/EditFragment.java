package com.nsight.holidayreminders.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.nsight.holidayreminders.R;

public class EditFragment extends Fragment {

    private AddFragment addFragment = new AddFragment();
    private Button addButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener(view1 -> getChildFragmentManager().beginTransaction().replace(R.id.edit_fragment, addFragment).commit());

        return view;
    }
}