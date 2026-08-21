package com.datetime.simple;

import java.util.Date;
import java.util.List;

public class DateTimeToolbox {
    public static void main(String[] args) {
        List<String> zones = TimeZones.getAll();
        for(String zone : zones){
            System.out.println(zone);
        }

        Date d = new Date();
        System.out.println(d);

        DateTime dt = new DateTime(d);
        System.out.println(dt);

        dt.changeTimezone(TimeZones.findOrUTC("Europe/Sofia"));
        System.out.println(dt);

    }
}
