package com.datetime.simple;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateTimeTest {


    @Test
    void javaLegacyDate() {
        LocalDateTime localDateTime =
                LocalDateTime.of(2026, 10, 2, 11, 4);

        Date date = Date.from(
                localDateTime.toInstant(ZoneOffset.ofHours(4))
        );

        DateTime dt = new DateTime(date);
        //System.out.print(dt);
        assertEquals("2026-10-02T10:04:00+03:00[Europe/Bucharest]", ""+dt);


    }

    @Test
    void changeTimezoneTest(){
        // Test timezone change
        LocalDateTime localDateTime =
                LocalDateTime.of(2026, 10, 2, 11, 4);

        Date date = Date.from(
                localDateTime.toInstant(ZoneOffset.ofHours(4))
        );


        DateTime dt = new DateTime(date, TimeZones.findOrUTC("Europe/Dublin"));
        assertEquals("2026-10-02T08:04:00+01:00[Europe/Dublin]",dt.toString());
        //System.out.print(dt);

        dt.changeTimezone(TimeZones.findOrUTC("Europe/Sofia"));
        //System.out.print(dt);
        assertEquals("2026-10-02T10:04:00+03:00[Europe/Sofia]",dt.toString());
    }

    @Test
    void jodaDateTimeTest(){
        /*
         * Expect jodaDateTime to be the same as OffsetDateTime from ZonedDateTime
         */
        org.joda.time.DateTime yodaDateTime = new org.joda.time.DateTime();
        //System.out.println(yodaDateTime);

        var dt = new DateTime(yodaDateTime);
        //System.out.println(dt);
        //System.out.println(dt.getZonedDateTime().toOffsetDateTime());

        assertEquals(yodaDateTime.toString(), dt.getZonedDateTime().toOffsetDateTime().toString());

    }

    @Test
    void calendarTest(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Dublin"));

        String datetime = String.valueOf(calendar.toInstant().atZone(calendar.getTimeZone().toZoneId()));
        //System.out.println(datetime);

        DateTime dt = new DateTime(calendar);
        //System.out.println(dt);

        assertEquals(datetime, ""+dt);

    }
}
