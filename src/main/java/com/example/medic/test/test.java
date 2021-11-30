package com.example.medic.test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class test {
    public static void main(String[] args) {
        Date date=new Date();
        Date date1=new Date(date.getTime()+60000*30);

        System.out.println(date1);

    }
}
