package com.nsight.holidayreminders.ui.edit;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.nsight.holidayreminders.R;
import com.nsight.holidayreminders.db.DBAdapter;
import com.nsight.holidayreminders.db.Holiday;

import java.util.Calendar;

public class AddFragment extends Fragment {
    private final Calendar date = Calendar.getInstance();
    private TextView dateSettedTextView;
    private EditText nameEditText;
    private DBAdapter dbAdapter;

    private DatePickerDialog.OnDateSetListener d;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbAdapter = new DBAdapter(getContext());
    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        dateSettedTextView = view.findViewById(R.id.date_setted_tv);
        Button dateSetButton = view.findViewById(R.id.set_date_btn);
        Button doneButton = view.findViewById(R.id.done_add_btn);
        nameEditText = view.findViewById(R.id.name_et);

        d = (datePicker, i, i1, i2) -> {
            date.set(Calendar.YEAR, i);
            date.set(Calendar.MONTH, i1);
            date.set(Calendar.DAY_OF_MONTH, i2);
            i1++;
            dateSettedTextView.setText(((i2 < 10) ? "0" + i2 : i2) + "." + ((i1 < 10) ? "0" + i1 : i1));
        };

        dateSetButton.setOnClickListener(view1 -> {
            new DatePickerDialog(getContext(), (DatePickerDialog.OnDateSetListener) d,
                    date.get(Calendar.YEAR),
                    date.get(Calendar.MONTH),
                    date.get(Calendar.DAY_OF_MONTH))
                    .show();
        });

        doneButton.setOnClickListener(view12 -> {
            if (dateSettedTextView.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Дата не установлена", Toast.LENGTH_SHORT).show();
            } else {
                dbAdapter.open();
                dbAdapter.insert(new Holiday(String.valueOf(nameEditText.getText()), String.valueOf(dateSettedTextView.getText())));
                dbAdapter.close();
                Toast.makeText(getContext(), "Праздник добавлен", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}