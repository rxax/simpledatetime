package com.datetime.simple;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTime {

    /**
     * This Class uses ZonedDateTime for obvious reasons
     * Class complies with ISO 8601: YYYY-MM-DDThh:mm:ss±hh:mm
     * Example: 2026-08-21T08:05:27Z
     * The key difference is whether the timestamp carries its UTC offset.
     */

    private ZonedDateTime dateTime;

    /**
     * Create a datetime object, default timezone is set to UTC
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     */
    public DateTime(int year,
                    int month,
                    int day,
                    int hour,
                    int minute,
                    int second){
        this(year, month, day, hour, minute, second, ZoneId.of("UTC"));
    }
    /**
     * Create a datetime object using explicit values
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @param timeZone
     */
    public DateTime(int year,
                    int month,
                    int day,
                    int hour,
                    int minute,
                    int second,
                    ZoneId timeZone){
        dateTime = ZonedDateTime.of(
                year,
                month,
                day,
                hour,
                minute,
                second,
                0,
                timeZone
        );
    }

    public DateTime(Date date){
        dateTime = date.toInstant().atZone(ZoneId.systemDefault());
    }

    public DateTime(Date date, ZoneId timezone){
        dateTime = date.toInstant().atZone(timezone);
    }

    public DateTime(java.sql.Date sqlDate){
        dateTime = sqlDate.toLocalDate()
                .atStartOfDay(ZoneId.systemDefault());
    }

    public DateTime(java.sql.Date sqlDate, ZoneId timezone){
        dateTime = sqlDate.toLocalDate()
                .atStartOfDay(timezone);
    }

    public DateTime(org.joda.time.DateTime yodaDateTime) {
        /*
         * Create a ZoneDateTime from joda.time 2.0
         */
        dateTime = ZonedDateTime.ofInstant(
                java.time.Instant.ofEpochMilli(yodaDateTime.getMillis()),
                java.time.ZoneId.of(yodaDateTime.getZone().getID())
        );
    }

    public DateTime(java.util.Calendar calendar){
        dateTime = calendar.toInstant().atZone(calendar.getTimeZone().toZoneId());
    }

    @SuppressWarnings("unused")
    public ZonedDateTime clearTime(){
        /*
        Set the time at midnight, returns new ZonedDateTime
         */
        return dateTime.toLocalDate().atStartOfDay(dateTime.getZone());
    }

    @SuppressWarnings("unused")
    public ZoneOffset getOffset(){
        // returns the offset, example +03:00
         return dateTime.getOffset();
    }

    @SuppressWarnings("unused")
    public ZoneId getZoneId(){
        // // returns the timezone, example Europe/Dublin
        return dateTime.getZone();
    }

    @SuppressWarnings("unused")
    public void changeTimezone(ZoneId timezone){
        dateTime = dateTime
                .withZoneSameInstant(timezone);
    }

    @SuppressWarnings("unused")
    public ZonedDateTime getZonedDateTime(){
        return dateTime;
    }

    @Override
    public String toString(){
        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

}
