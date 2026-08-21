package com.datetime.simple;

import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TimeZonesTest {

    @Test
    void timezoneFindTest(){
        ZoneId zone = TimeZones.find("US/East-Indiana");
        ZoneId zone2 = ZoneId.of("US/East-Indiana");
        assertEquals(zone2.toString(), zone.toString());
        assertEquals(zone, zone2);
        //System.out.println(zone);
    }

    @Test
    void timezoneWrongTest(){
        ZoneId zone = TimeZones.find("UTC");
        ZoneId zone2 = ZoneId.of("US/East-Indiana");
        assertNotEquals(zone2.toString(), zone.toString());
        assertNotEquals(zone, zone2);
        //System.out.println(zone);
    }

    @Test
    void timezoneNotFoundTest(){
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> TimeZones.find("US/East-IndianaState")
        );

        assertEquals(
                "US/East-IndianaState not found",
                exception.getMessage()
        );
    }
}
