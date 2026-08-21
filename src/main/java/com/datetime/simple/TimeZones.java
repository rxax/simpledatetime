package com.datetime.simple;

import java.time.ZoneId;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TimeZones {
    // Get IANA time zones supported by the JVM
    private static final List<String> zones = ZoneId.getAvailableZoneIds()
            .stream()
            .sorted()
            .toList();

    public static List<String> getAll() {
       return zones;
    }

    public static ZoneId find(String timezone){
        // find a timezone by name
        Optional<String> item = getAll().stream().filter(name -> (name.equals(timezone))).findAny();

        if(item.isEmpty()) throw new NoSuchElementException(timezone+" not found");

        try {
            return ZoneId.of(item.get());
        }catch(Exception e){
            throw new NoSuchElementException(e.getMessage());
        }

    }
}