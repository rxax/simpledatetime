package com.datetime.simple;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateTime implements Comparable<DateTime>{

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

    /**
     * Create a DateTime from a Unix timestamp (seconds since epoch)
     * @param timestamp Unix timestamp in seconds
     */
    public DateTime(long timestamp){
        dateTime =
                Instant.ofEpochSecond(timestamp)
                        .atZone(ZoneId.of("UTC"));
    }

    /**
     * Create a new DateTime as a copy of an existing DateTime
     * @param dateTime The DateTime object to copy
     */
    public DateTime(DateTime dateTime){
        this.dateTime = ZonedDateTime.of(
                dateTime.dateTime.toLocalDateTime(),
                dateTime.dateTime.getZone()
        );
    }

    /**
     * Create a DateTime from a java.util.Date using system default timezone
     * @param date The java.util.Date object to convert
     */
    public DateTime(java.util.Date date){
        dateTime = date.toInstant().atZone(ZoneId.systemDefault());
    }

    /**
     * Create a DateTime from a java.util.Date with specified timezone
     * @param date The java.util.Date object to convert
     * @param timezone The timezone to use for the DateTime
     */
    public DateTime(java.util.Date date, ZoneId timezone){
        dateTime = date.toInstant().atZone(timezone);
    }

    /**
     * Create a DateTime from a java.sql.Date (time set to midnight)
     * Uses system default timezone
     * @param sqlDate The java.sql.Date object to convert
     */
    public DateTime(java.sql.Date sqlDate){
        dateTime = sqlDate.toLocalDate()
                .atStartOfDay(ZoneId.systemDefault());
    }

    /**
     * Create a DateTime from a java.sql.Date with specified timezone (time set to midnight)
     * @param sqlDate The java.sql.Date object to convert
     * @param timezone The timezone to use for the DateTime
     */
    public DateTime(java.sql.Date sqlDate, ZoneId timezone){
        dateTime = sqlDate.toLocalDate()
                .atStartOfDay(timezone);
    }

    /**
     * Create a DateTime from a Joda-Time DateTime object
     * @param yodaDateTime The Joda-Time DateTime object to convert
     */
    public DateTime(org.joda.time.DateTime yodaDateTime) {
        dateTime = ZonedDateTime.ofInstant(
                java.time.Instant.ofEpochMilli(yodaDateTime.getMillis()),
                java.time.ZoneId.of(yodaDateTime.getZone().getID())
        );
    }

    /**
     * Create a DateTime from a java.util.Calendar object
     * @param calendar The Calendar object to convert
     */
    public DateTime(java.util.Calendar calendar){
        dateTime = calendar.toInstant().atZone(calendar.getTimeZone().toZoneId());
    }

    /**
     * Clear the time portion (set to midnight) and return as new ZonedDateTime
     * @return A new ZonedDateTime with time set to 00:00:00
     */
    @SuppressWarnings("unused")
    public ZonedDateTime clearTime(){
        return dateTime.toLocalDate().atStartOfDay(dateTime.getZone());
    }

    /**
     * Convert DateTime to ISO 8601 string format
     * @return String in ISO 8601 format (YYYY-MM-DDThh:mm:ss±hh:mm[zone])
     */
    @Override
    public String toString(){
        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    /**
     * Compare this DateTime with another DateTime
     * @param other The DateTime to compare with
     * @return Negative if this is earlier, positive if later, 0 if equal
     */
    @Override
    public int compareTo(DateTime other) {
        return this.dateTime.compareTo(other.dateTime);
    }

    /**
     * Compare two DateTimes, check if they represent the same moment in time
     * @param o   the reference object with which to compare.
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof DateTime other)) {
            return false;
        }

        return dateTime.isEqual(other.dateTime);
    }

    /**
     * Generate hash code based on the instant in time
     * @return Hash code of the instant
     */
    @Override
    public int hashCode() {
        return dateTime.toInstant().hashCode();
    }

    /**
     * Get the year component
     * @return Year as integer
     */
    public int getYear() {
        return dateTime.getYear();
    }

    /**
     * Set the year component
     * @param year New year value
     */
    public void setYear(int year) {
        dateTime = dateTime.withYear(year);
    }

    /**
     * Get the month component (1-12)
     * @return Month as integer (1=January, 12=December)
     */
    public int getMonth() {
        return dateTime.getMonthValue();
    }

    /**
     * Set the month component
     * @param month New month value (1-12)
     */
    public void setMonth(int month) {
        dateTime = dateTime.withMonth(month);
    }

    /**
     * Get the day of month component
     * @return Day of month as integer (1-31)
     */
    public int getDay() {
        return dateTime.getDayOfMonth();
    }

    /**
     * Set the day of month component
     * @param day New day of month value (1-31)
     */
    public void setDay(int day) {
        dateTime = dateTime.withDayOfMonth(day);
    }

    /**
     * Get the hour component (0-23)
     * @return Hour as integer in 24-hour format
     */
    public int getHour() {
        return dateTime.getHour();
    }

    /**
     * Set the hour component
     * @param hour New hour value (0-23)
     */
    public void setHour(int hour) {
        dateTime = dateTime.withHour(hour);
    }

    /**
     * Get the minute component (0-59)
     * @return Minute as integer
     */
    public int getMinute() {
        return dateTime.getMinute();
    }

    /**
     * Set the minute component
     * @param minute New minute value (0-59)
     */
    public void setMinute(int minute) {
        dateTime = dateTime.withMinute(minute);
    }

    /**
     * Get the second component (0-59)
     * @return Second as integer
     */
    public int getSecond() {
        return dateTime.getSecond();
    }

    /**
     * Set the second component
     * @param second New second value (0-59)
     */
    public void setSecond(int second) {
        dateTime = dateTime.withSecond(second);
    }

    /**
     * Get the timezone offset (e.g., +03:00)
     * @return ZoneOffset representing the UTC offset
     */
    public ZoneOffset getOffset(){
        return dateTime.getOffset();
    }

    /**
     * Get the timezone ID (e.g., Europe/Dublin)
     * @return ZoneId representing the timezone
     */
    public ZoneId getZoneId(){
        return dateTime.getZone();
    }

    /**
     * Get the timezone (alias for getZoneId)
     * @return ZoneId representing the timezone
     */
    public ZoneId getTimezone(){
        return this.getZoneId();
    }

    /**
     * Change the timezone while keeping the same instant in time
     * @param timezone The new timezone to use
     */
    public void setTimezone(ZoneId timezone){
        dateTime = dateTime
                .withZoneSameInstant(timezone);
    }


    /**
     * Get the underlying ZonedDateTime object
     * @return The ZonedDateTime representation
     */
    public ZonedDateTime getZonedDateTime(){
        return dateTime;
    }

    /**
     * Get the DateTime as ZonedDateTime (alias for getZonedDateTime)
     * @return The ZonedDateTime representation
     */
    public ZonedDateTime getDateTime(){
        return getZonedDateTime();
    }

    /**
     * Get the time component as LocalTime
     * @return LocalTime representing the time portion
     */
    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    /**
     * Return time as HH:mm:ss String
     * @return String
     */
    public String getTimeStr() {
        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
