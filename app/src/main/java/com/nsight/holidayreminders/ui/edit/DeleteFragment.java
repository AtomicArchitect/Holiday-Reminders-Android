package com.nsight.holidayreminders.ui.edit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.nsight.holidayreminders.R;
import com.nsight.holidayreminders.db.DBAdapter;

public class DeleteFragment extends Fragment {

    private DBAdapter dbAdapter;
    private HolidayAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbAdapter = new DBAdapter(getContext());
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete, container, false);
        ListView deleteLV = view.findViewById(R.id.delete_list_view);

        dbAdapter.open();
        adapter = new HolidayAdapter(getContext(), R.layout.home_row, dbAdapter.getHolidays());
        dbAdapter.close();
        deleteLV.setAdapter(adapter);

        deleteLV.setOnItemClickListener((adapterView, view1, i, l) -> {
            TextView itemTV = view1.findViewById(R.id.name_item_text_view);
            dbAdapter.open();
            dbAdapter.delete(itemTV.getText().toString());
            dbAdapter.close();

            adapter.remove(adapter.getItem(i));
            adapter.notifyDataSetChanged();
        });
        return view;
    }
}