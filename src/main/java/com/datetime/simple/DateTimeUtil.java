package com.datetime.simple;

import java.time.temporal.ChronoUnit;


public class DateTimeUtil {

    /**
     * Calculate days between two dates
     */
    public static long daysBetween(DateTime start, DateTime end){
        return ChronoUnit.DAYS.between(
                start.getZonedDateTime().toLocalDate(),
                end.getZonedDateTime().toLocalDate()
        );
    }
}
