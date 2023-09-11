package com.nsight.holidayreminders.ui;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.nsight.holidayreminders.R;
import com.nsight.holidayreminders.db.DBAdapter;
import com.nsight.holidayreminders.ui.edit.HolidayAdapter;

import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat finalDateFormat = new SimpleDateFormat("dd.MM");
    private DBAdapter dbAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbAdapter = new DBAdapter(getContext());
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        String date = finalDateFormat.format(new Date());

        TextView currentDate = (TextView) view.findViewById(R.id.current_date_tv);
        currentDate.setText(date);

        TextView todayHoliday = (TextView) view.findViewById(R.id.today_holiday);
        dbAdapter.open();
        todayHoliday.setText(dbAdapter.getNameHolidayOnDate(date));

        HolidayAdapter adapter = new HolidayAdapter(getContext(), R.layout.home_row, dbAdapter.getHolidays());
        ListView holidaysLV = view.findViewById(R.id.holidays_list_view);
        holidaysLV.setAdapter(adapter);
        dbAdapter.close();

        return view;
    }
}