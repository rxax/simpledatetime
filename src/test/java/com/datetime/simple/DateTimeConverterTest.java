package com.datetime.simple;

import com.datetime.simple.DateTimeConverter;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.UnsupportedTemporalTypeException;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeConverterTest {

    private static final ZoneId BUCHAREST =
            ZoneId.of("Europe/Bucharest");

    private static final ZoneId NEW_YORK =
            ZoneId.of("America/New_York");


    @Test
    void instantToZonedDateTime() {

        Instant instant =
                Instant.parse("2026-08-20T08:00:00Z");

        ZonedDateTime result =
                DateTimeConverter.convert(
                        instant,
                        ZonedDateTime.class,
                        BUCHAREST
                );

        assertEquals(
                ZonedDateTime.of(
                        2026,
                        8,
                        20,
                        11,
                        0,
                        0,
                        0,
                        BUCHAREST
                ),
                result
        );
    }


    @Test
    void zonedDateTimeToInstant() {

        ZonedDateTime bucharest =
                ZonedDateTime.of(
                        2026,
                        8,
                        20,
                        11,
                        0,
                        0,
                        0,
                        BUCHAREST
                );

        Instant result =
                DateTimeConverter.convert(
                        bucharest,
                        Instant.class
                );

        assertEquals(
                Instant.parse("2026-08-20T08:00:00Z"),
                result
        );
    }


    @Test
    void timezoneConversion() {

        Instant instant =
                Instant.parse("2026-08-20T08:00:00Z");

        ZonedDateTime bucharest =
                DateTimeConverter.convert(
                        instant,
                        ZonedDateTime.class,
                        BUCHAREST
                );

        ZonedDateTime newYork =
                DateTimeConverter.convert(
                        instant,
                        ZonedDateTime.class,
                        NEW_YORK
                );

        assertEquals(11, bucharest.getHour());
        assertEquals(4, newYork.getHour());
    }


    @Test
    void localDateTimeToInstant() {

        LocalDateTime local =
                LocalDateTime.of(
                        2026,
                        8,
                        20,
                        11,
                        0
                );

        Instant result =
                DateTimeConverter.convert(
                        local,
                        Instant.class,
                        BUCHAREST
                );

        assertEquals(
                Instant.parse("2026-08-20T08:00:00Z"),
                result
        );
    }


    @Test
    void instantToLocalDateTime() {

        Instant instant =
                Instant.parse("2026-08-20T08:00:00Z");

        LocalDateTime result =
                DateTimeConverter.convert(
                        instant,
                        LocalDateTime.class,
                        BUCHAREST
                );

        assertEquals(
                LocalDateTime.of(
                        2026,
                        8,
                        20,
                        11,
                        0
                ),
                result
        );
    }


    @Test
    void zonedDateTimeToLocalDate() {

        ZonedDateTime value =
                ZonedDateTime.of(
                        2026,
                        8,
                        20,
                        11,
                        30,
                        0,
                        0,
                        BUCHAREST
                );

        LocalDate result =
                DateTimeConverter.convert(
                        value,
                        LocalDate.class
                );

        assertEquals(
                LocalDate.of(2026, 8, 20),
                result
        );
    }


    @Test
    void zonedDateTimeToLocalTime() {

        ZonedDateTime value =
                ZonedDateTime.of(
                        2026,
                        8,
                        20,
                        11,
                        30,
                        15,
                        0,
                        BUCHAREST
                );

        LocalTime result =
                DateTimeConverter.convert(
                        value,
                        LocalTime.class
                );

        assertEquals(
                LocalTime.of(11, 30, 15),
                result
        );
    }


    @Test
    void instantToOffsetDateTime() {

        Instant instant =
                Instant.parse("2026-08-20T08:00:00Z");

        OffsetDateTime result =
                DateTimeConverter.convert(
                        instant,
                        OffsetDateTime.class,
                        BUCHAREST
                );

        assertEquals(
                OffsetDateTime.of(
                        2026,
                        8,
                        20,
                        11,
                        0,
                        0,
                        0,
                        ZoneOffset.ofHours(3)
                ),
                result
        );
    }


    @Test
    void localDateToZonedDateTime() {

        LocalDate date =
                LocalDate.of(2026, 8, 20);

        ZonedDateTime result =
                DateTimeConverter.convert(
                        date,
                        ZonedDateTime.class,
                        BUCHAREST
                );

        assertEquals(
                LocalDateTime.of(2026, 8, 20, 0, 0),
                result.toLocalDateTime()
        );

        assertEquals(
                BUCHAREST,
                result.getZone()
        );
    }


    @Test
    void yearToLocalDate() {

        Year year = Year.of(2026);

        LocalDate result =
                DateTimeConverter.convert(
                        year,
                        LocalDate.class,
                        BUCHAREST
                );

        assertEquals(
                LocalDate.of(2026, 1, 1),
                result
        );
    }


    @Test
    void yearMonthToLocalDate() {

        YearMonth yearMonth =
                YearMonth.of(2026, 8);

        LocalDate result =
                DateTimeConverter.convert(
                        yearMonth,
                        LocalDate.class,
                        BUCHAREST
                );

        assertEquals(
                LocalDate.of(2026, 8, 1),
                result
        );
    }


    @Test
    void unsupportedTypeThrowsException() {

        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> DateTimeConverter .convert(
                        Instant.now(),
                        Duration.class
                )
        );
    }
}