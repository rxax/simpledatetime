package com.datetime.simple;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

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
        assertEquals("2026-10-02T10:04:00+03:00", ""+dt);


    }
}
