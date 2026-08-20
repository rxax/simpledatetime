/**
 * Here’s a practical Java 17+ implementation supporting the common java.time types:
 *
 * Instant
 * LocalDate
 * LocalTime
 * LocalDateTime
 * ZonedDateTime
 * OffsetDateTime
 * OffsetTime
 * Year
 * YearMonth
 * MonthDay
 *
 * It also handles timezone/offset conversion and provides a test class.
 */
package com.datetime.simple;

import java.time.*;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.UnsupportedTemporalTypeException;

public class DateTimeConverter {

    private DateTimeConverter() {
        // Utility class
    }

    /**
     * Converts between supported java.time types.
     *
     * @param value     source date/time
     * @param targetType target java.time class
     * @param zone       timezone used when a timezone is required
     */
    public static <T extends Temporal> T convert(
            TemporalAccessor value,
            Class<T> targetType,
            ZoneId zone) {

        if (value == null) {
            throw new IllegalArgumentException("Source value cannot be null");
        }

        if (targetType == null) {
            throw new IllegalArgumentException("Target type cannot be null");
        }

        if (zone == null) {
            zone = ZoneId.systemDefault();
        }

        /*
         * Instant
         */
        if (targetType == Instant.class) {
            return targetType.cast(toInstant(value, zone));
        }

        /*
         * ZonedDateTime
         */
        if (targetType == ZonedDateTime.class) {
            return targetType.cast(toZonedDateTime(value, zone));
        }

        /*
         * OffsetDateTime
         */
        if (targetType == OffsetDateTime.class) {
            return targetType.cast(
                    toZonedDateTime(value, zone).toOffsetDateTime()
            );
        }

        /*
         * LocalDateTime
         */
        if (targetType == LocalDateTime.class) {
            return targetType.cast(
                    toZonedDateTime(value, zone).toLocalDateTime()
            );
        }

        /*
         * LocalDate
         */
        if (targetType == LocalDate.class) {
            return targetType.cast(
                    toZonedDateTime(value, zone).toLocalDate()
            );
        }

        /*
         * LocalTime
         */
        if (targetType == LocalTime.class) {
            return targetType.cast(
                    toZonedDateTime(value, zone).toLocalTime()
            );
        }

        /*
         * OffsetTime
         */
        if (targetType == OffsetTime.class) {
            return targetType.cast(
                    toZonedDateTime(value, zone).toOffsetDateTime().toOffsetTime()
            );
        }

        /*
         * Year
         */
        if (targetType == Year.class) {
            return targetType.cast(
                    Year.from(toZonedDateTime(value, zone))
            );
        }

        /*
         * YearMonth
         */
        if (targetType == YearMonth.class) {
            return targetType.cast(
                    YearMonth.from(toZonedDateTime(value, zone))
            );
        }

        /*
         * MonthDay
         */
        if (targetType.equals(MonthDay.class)) {
            return targetType.cast(
                    MonthDay.from(toZonedDateTime(value, zone))
            );
        }

        throw new UnsupportedTemporalTypeException(
                "Unsupported target type: " + targetType.getName()
        );
    }

    public static <T extends Temporal> T convert(
            TemporalAccessor value,
            Class<T> targetType) {

        return convert(value, targetType, ZoneId.systemDefault());
    }

    /**
     * Converts a value to an Instant.
     *
     * Values that don't contain timezone information are interpreted
     * using the supplied ZoneId.
     */
    private static Instant toInstant(
            TemporalAccessor value,
            ZoneId zone) {

        if (value instanceof Instant instant) {
            return instant;
        }

        if (value instanceof ZonedDateTime zonedDateTime) {
            return zonedDateTime.toInstant();
        }

        if (value instanceof OffsetDateTime offsetDateTime) {
            return offsetDateTime.toInstant();
        }

        if (value instanceof LocalDateTime localDateTime) {
            return localDateTime.atZone(zone).toInstant();
        }

        if (value instanceof LocalDate localDate) {
            return localDate
                    .atStartOfDay(zone)
                    .toInstant();
        }

        if (value instanceof OffsetTime offsetTime) {
            return LocalDate.now(zone)
                    .atTime(offsetTime)
                    .toInstant();
        }

        if (value instanceof LocalTime localTime) {
            return LocalDate.now(zone)
                    .atTime(localTime)
                    .atZone(zone)
                    .toInstant();
        }

        throw new UnsupportedTemporalTypeException(
                "Cannot convert " + value.getClass().getName() +
                        " to Instant"
        );
    }

    /**
     * Converts any supported value to ZonedDateTime.
     */
    private static ZonedDateTime toZonedDateTime(
            TemporalAccessor value,
            ZoneId zone) {

        if (value instanceof ZonedDateTime zonedDateTime) {
            return zonedDateTime;
        }

        if (value instanceof OffsetDateTime offsetDateTime) {
            return offsetDateTime.toZonedDateTime();
        }

        if (value instanceof Instant instant) {
            return instant.atZone(zone);
        }

        if (value instanceof LocalDateTime localDateTime) {
            return localDateTime.atZone(zone);
        }

        if (value instanceof LocalDate localDate) {
            return localDate.atStartOfDay(zone);
        }

        if (value instanceof LocalTime localTime) {
            return LocalDate.now(zone)
                    .atTime(localTime)
                    .atZone(zone);
        }

        if (value instanceof OffsetTime offsetTime) {
            return LocalDate.now(zone)
                    .atTime(offsetTime)
                    .atZoneSameInstant(zone);
        }

        if (value instanceof Year year) {
            return year.atDay(1).atStartOfDay(zone);
        }

        if (value instanceof YearMonth yearMonth) {
            return yearMonth.atDay(1).atStartOfDay(zone);
        }

        if (value instanceof MonthDay monthDay) {
            return monthDay
                    .atYear(Year.now(zone).getValue())
                    .atStartOfDay(zone);
        }

        throw new UnsupportedTemporalTypeException(
                "Cannot convert " + value.getClass().getName() +
                        " to ZonedDateTime"
        );
    }
}