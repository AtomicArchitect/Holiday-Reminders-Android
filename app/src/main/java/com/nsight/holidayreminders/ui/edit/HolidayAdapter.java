package com.nsight.holidayreminders.ui.edit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nsight.holidayreminders.R;
import com.nsight.holidayreminders.db.Holiday;

import java.util.ArrayList;
import java.util.List;

public class HolidayAdapter extends ArrayAdapter<Holiday> {

    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Holiday> holidays;

    public HolidayAdapter(Context context, int resource, List<Holiday> holidays) {
        super(context, resource, holidays);
        this.holidays = (ArrayList<Holiday>) holidays;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Holiday holiday = holidays.get(position);

        viewHolder.nameView.setText(holiday.getName());
        viewHolder.dateView.setText(holiday.getDate());

        return convertView;
    }

    private class ViewHolder {

        final TextView nameView, dateView;

        ViewHolder(View view) {
            nameView = view.findViewById(R.id.name_item_text_view);
            dateView = view.findViewById(R.id.date_item_text_view);
        }
    }
}
