package com.datetime.simple;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTime {

    /**
     * This Class uses OffsetDateTime for obvious reasons
     * Class complies with ISO 8601: YYYY-MM-DDThh:mm:ss±hh:mm
     * Example: 2026-08-21T08:05:27Z
     * The key difference is whether the timestamp carries its UTC offset.
     */

    private final OffsetDateTime dateTime;

    public DateTime(java.util.Date date){
        // Convert java.util.Date to OffsetDateTime
        dateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toOffsetDateTime();
    }

    public DateTime(java.sql.Date sqlDate){
        dateTime = sqlDate.toLocalDate()
                .atStartOfDay(ZoneId.systemDefault())
                .toOffsetDateTime();
    }

    @Override
    public String toString(){
        return dateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

}
