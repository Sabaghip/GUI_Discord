package server;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Date implements Serializable {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    public Date(LocalDateTime date){
        year = date.getYear();
        month = date.getMonthValue();
        day = date.getDayOfMonth();
        hour = date.getHour();
        minute = date.getMinute();
        second = date.getSecond();
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getMonth() {
        return month;
    }

    public int getSecond() {
        return second;
    }

    public int getYear() {
        return year;
    }

    public int compare(Date date){
        if(year < date.getDay()){
            return 1;
        }
        if(year > date.getDay()){
            return -1;
        }
        if(month < date.getMonth()){
            return 1;
        }
        if(month > date.getMonth()){
            return -1;
        }
        if(day < date.getDay()){
            return 1;
        }
        if(day > date.getDay()){
            return -1;
        }
        if(hour < date.getHour()){
            return 1;
        }
        if(hour > date.getHour()){
            return -1;
        }
        if(minute < date.getMinute()){
            return 1;
        }
        if(minute > date.getMinute()){
            return -1;
        }
        if(second > date.getSecond()){
            return 1;
        }
        if(second < date.getSecond()){
            return -1;
        }
        return 0;

    }
}
