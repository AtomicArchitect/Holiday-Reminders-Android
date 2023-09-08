package com.nsight.holidayreminders;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Date;

public class HomeFragment extends Fragment {

    private static final SimpleDateFormat finalDateFormat = new SimpleDateFormat("dd.MM.y");
    private TextView currentDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        currentDate = (TextView) view.findViewById(R.id.current_date_tv);
        currentDate.setText(finalDateFormat.format(new Date()));

        return view;
    }
}