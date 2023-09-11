package com.nsight.holidayreminders.db;

public class Holiday {
    private final long id;
    private String name, date;

    public Holiday(String name, String date) {
        this.id = -1;
        this.name = name;
        this.date = date;
    }

    public Holiday(long id, String name, String date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
