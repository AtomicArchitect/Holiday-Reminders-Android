package com.nsight.holidayreminders.ui;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.nsight.holidayreminders.R;
import com.nsight.holidayreminders.db.DBAdapter;
import com.nsight.holidayreminders.ui.edit.HolidayAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;

public class HomeFragment extends Fragment {

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat finalDateFormat = new SimpleDateFormat("dd.MM");
    private DBAdapter dbAdapter;
    private static final int MAX_NHI_COUNT = 10;
    private ListView holidaysLV;

    private Button local, network;
    private HolidayAdapter localHolidayAdapter;
    private ArrayAdapter<String> networkHolidayAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbAdapter = new DBAdapter(getContext());
        dbAdapter.open();
        localHolidayAdapter = new HolidayAdapter(getContext(), R.layout.home_row, dbAdapter.getHolidays());
        dbAdapter.close();
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

        holidaysLV = view.findViewById(R.id.holidays_list_view);
        holidaysLV.setAdapter(localHolidayAdapter);

        local = view.findViewById(R.id.local_btn);
        network = view.findViewById(R.id.network_btn);

        local.setOnClickListener(view1 -> holidaysLV.setAdapter(localHolidayAdapter));
        network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (networkHolidayAdapter != null) {
                    holidaysLV.setAdapter(networkHolidayAdapter);
                } else {
                    Toast.makeText(getContext(), "Загружаю", Toast.LENGTH_SHORT).show();
                    NetworkLoaderTask networkLoaderTask = new NetworkLoaderTask();
                    networkLoaderTask.execute();
                }
            }
        });

        return view;
    }

    class NetworkLoaderTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... voids) {
            String[] items = new String[MAX_NHI_COUNT];

            try {
                Document document = Jsoup.connect("https://kakoysegodnyaprazdnik.ru").get();
                Elements elements = document.select("span[itemprop=text]");
                for (int i = 0; i < MAX_NHI_COUNT; i++) {
                    items[i] = "◉ " + elements.get(i).text();
                }
            } catch (IOException e) {
                items[0] = "Не удалось загрузить";
            }
            return items;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
            networkHolidayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, strings);
            holidaysLV.setAdapter(networkHolidayAdapter);
        }
    }
}