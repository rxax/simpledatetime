package com.datetime.simple;

import java.util.List;

public class DateTimeToolbox {
    public static void main(String[] args) {
        List<String> zones = TimeZones.getAll();
        for(String zone : zones){
            System.out.println(zone);
        }
    }
}
