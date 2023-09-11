package com.nsight.holidayreminders.utils;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    public static int dateDiffDays(String dateStart, String dateEnd) {
        String[] argsE = dateEnd.split(".");
        Calendar end = Calendar.getInstance();
        end.set(Calendar.DAY_OF_MONTH, Integer.parseInt(argsE[0]));
        end.set(Calendar.MONTH, Integer.parseInt(argsE[1]));

        String[] argsS = dateEnd.split(".");
        Calendar start = Calendar.getInstance();
        start.set(Calendar.DAY_OF_MONTH, Integer.parseInt(argsS[0]));
        start.set(Calendar.MONTH, Integer.parseInt(argsS[1]));
        long diffInMillies = Math.abs(end.getTimeInMillis() - start.getTimeInMillis());
        return (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static int dateDiffDaysFromToday(String dateEnd) {
        String[] args = dateEnd.split("\\.");
        Calendar end = Calendar.getInstance();
        end.set(Calendar.DAY_OF_MONTH, Integer.parseInt(args[0]));
        end.set(Calendar.MONTH, Integer.parseInt(args[1]) - 1);
        Calendar start = Calendar.getInstance();
        if (start.after(end)) end.set(Calendar.YEAR, end.get(Calendar.YEAR)+1);
        long diffInMillies = Math.abs(end.getTimeInMillis() - start.getTimeInMillis());
        return (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static String getDayAddition(int num) {
        int preLastDigit = num % 100 / 10;
        if (preLastDigit == 1) return "дней";
        switch (num % 10) {
            case 1:
                return "день";
            case 2:
            case 3:
            case 4:
                return "дня";
            default:
                return "дней";
        }
    }

}
