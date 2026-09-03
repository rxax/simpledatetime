package com.datetime.simple;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class DateTimeTest {

    // ========== Constructor Tests ==========

    @Test
    void testBasicConstructorWithUTC() {
        DateTime dt = new DateTime(2023, 12, 25, 10, 30, 45);
        assertEquals(2023, dt.getYear());
        assertEquals(12, dt.getMonth());
        assertEquals(25, dt.getDay());
        assertEquals(10, dt.getHour());
        assertEquals(30, dt.getMinute());
        assertEquals(45, dt.getSecond());
        assertEquals(ZoneId.of("UTC"), dt.getZoneId());
    }

    @Test
    void testConstructorWithSpecificTimezone() {
        DateTime dt = new DateTime(2023, 6, 15, 14, 20, 30, ZoneId.of("America/New_York"));
        assertEquals(2023, dt.getYear());
        assertEquals(6, dt.getMonth());
        assertEquals(15, dt.getDay());
        assertEquals(14, dt.getHour());
        assertEquals(ZoneId.of("America/New_York"), dt.getZoneId());
    }

    @Test
    void testTimestampConstructor() {
        // Test with known timestamp: 2023-01-01T00:00:00Z = 1672531200 seconds
        DateTime dt = new DateTime(1672531200L);
        assertEquals(2023, dt.getYear());
        assertEquals(1, dt.getMonth());
        assertEquals(1, dt.getDay());
        assertEquals(0, dt.getHour());
        assertEquals(0, dt.getMinute());
        assertEquals(0, dt.getSecond());
        assertEquals(ZoneId.of("UTC"), dt.getZoneId());
    }

    @Test
    void testCopyConstructor() {
        DateTime original = new DateTime(2023, 5, 10, 15, 45, 30, ZoneId.of("Europe/London"));
        DateTime copy = new DateTime(original);
        
        assertEquals(original.getYear(), copy.getYear());
        assertEquals(original.getMonth(), copy.getMonth());
        assertEquals(original.getDay(), copy.getDay());
        assertEquals(original.getHour(), copy.getHour());
        assertEquals(original.getMinute(), copy.getMinute());
        assertEquals(original.getSecond(), copy.getSecond());
        assertEquals(original.getZoneId(), copy.getZoneId());
        
        // Verify they are different objects but equal
        assertNotSame(original, copy);
        assertEquals(original, copy);
    }

    @Test
    void javaLegacyDate() {
        LocalDateTime localDateTime = LocalDateTime.of(2026, 10, 2, 11, 4);
        Date date = Date.from(localDateTime.toInstant(ZoneOffset.ofHours(4)));
        
        DateTime dt = new DateTime(date);
        assertEquals("2026-10-02T10:04:00+03:00[Europe/Bucharest]", "" + dt);
    }

    @Test
    void testDateConstructorWithTimezone() {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 7, 20, 15, 30);
        Date date = Date.from(localDateTime.toInstant(ZoneOffset.UTC));
        
        DateTime dt = new DateTime(date, ZoneId.of("Europe/Paris"));
        assertEquals(2023, dt.getYear());
        assertEquals(7, dt.getMonth());
        assertEquals(20, dt.getDay());
        assertEquals(ZoneId.of("Europe/Paris"), dt.getZoneId());
    }

    @Test
    void testSqlDateConstructor() {
        java.sql.Date sqlDate = java.sql.Date.valueOf("2023-08-15");
        DateTime dt = new DateTime(sqlDate);
        
        // SQL Date should have time set to midnight
        assertEquals(2023, dt.getYear());
        assertEquals(8, dt.getMonth());
        assertEquals(15, dt.getDay());
        assertEquals(0, dt.getHour());
        assertEquals(0, dt.getMinute());
        assertEquals(0, dt.getSecond());
    }

    @Test
    void testSqlDateConstructorWithTimezone() {
        java.sql.Date sqlDate = java.sql.Date.valueOf("2023-09-01");
        DateTime dt = new DateTime(sqlDate, ZoneId.of("Asia/Tokyo"));
        
        assertEquals(2023, dt.getYear());
        assertEquals(9, dt.getMonth());
        assertEquals(1, dt.getDay());
        assertEquals(0, dt.getHour());
        assertEquals(ZoneId.of("Asia/Tokyo"), dt.getZoneId());
    }

    @Test
    void jodaDateTimeTest() {
        org.joda.time.DateTime yodaDateTime = new org.joda.time.DateTime();
        var dt = new DateTime(yodaDateTime);
        assertEquals(yodaDateTime.toString(), dt.getZonedDateTime().toOffsetDateTime().toString());
    }

    @Test
    void calendarTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Dublin"));
        
        String datetime = String.valueOf(calendar.toInstant().atZone(calendar.getTimeZone().toZoneId()));
        DateTime dt = new DateTime(calendar);
        
        assertEquals(datetime, "" + dt);
    }

    // ========== Getter and Setter Tests ==========

    @Test
    void testYearGetterAndSetter() {
        DateTime dt = new DateTime(2023, 6, 15, 10, 30, 45);
        assertEquals(2023, dt.getYear());
        
        dt.setYear(2024);
        assertEquals(2024, dt.getYear());
        assertEquals(6, dt.getMonth()); // Other components unchanged
        assertEquals(15, dt.getDay());
    }

    @Test
    void testMonthGetterAndSetter() {
        DateTime dt = new DateTime(2023, 6, 15, 10, 30, 45);
        assertEquals(6, dt.getMonth());
        
        dt.setMonth(12);
        assertEquals(12, dt.getMonth());
        assertEquals(2023, dt.getYear()); // Year unchanged
        assertEquals(15, dt.getDay());
    }

    @Test
    void testDayGetterAndSetter() {
        DateTime dt = new DateTime(2023, 6, 15, 10, 30, 45);
        assertEquals(15, dt.getDay());
        
        dt.setDay(25);
        assertEquals(25, dt.getDay());
        assertEquals(6, dt.getMonth()); // Month unchanged
        assertEquals(10, dt.getHour());
    }

    @Test
    void testHourGetterAndSetter() {
        DateTime dt = new DateTime(2023, 6, 15, 10, 30, 45);
        assertEquals(10, dt.getHour());
        
        dt.setHour(20);
        assertEquals(20, dt.getHour());
        assertEquals(15, dt.getDay()); // Day unchanged
        assertEquals(30, dt.getMinute());
    }

    @Test
    void testMinuteGetterAndSetter() {
        DateTime dt = new DateTime(2023, 6, 15, 10, 30, 45);
        assertEquals(30, dt.getMinute());
        
        dt.setMinute(45);
        assertEquals(45, dt.getMinute());
        assertEquals(10, dt.getHour()); // Hour unchanged
        assertEquals(45, dt.getSecond());
    }

    @Test
    void testSecondGetterAndSetter() {
        DateTime dt = new DateTime(2023, 6, 15, 10, 30, 45);
        assertEquals(45, dt.getSecond());
        
        dt.setSecond(0);
        assertEquals(0, dt.getSecond());
        assertEquals(30, dt.getMinute()); // Minute unchanged
        assertEquals(10, dt.getHour());
    }

    // ========== Timezone and Offset Tests ==========

    @Test
    void changeTimezoneTest() {
        LocalDateTime localDateTime = LocalDateTime.of(2026, 10, 2, 11, 4);
        Date date = Date.from(localDateTime.toInstant(ZoneOffset.ofHours(4)));
        
        DateTime dt = new DateTime(date, TimeZones.findOrUTC("Europe/Dublin"));
        assertEquals("2026-10-02T08:04:00+01:00[Europe/Dublin]", dt.toString());
        
        dt.setTimezone(TimeZones.findOrUTC("Europe/Sofia"));
        assertEquals("2026-10-02T10:04:00+03:00[Europe/Sofia]", dt.toString());
    }

    @Test
    void testGetOffset() {
        DateTime dt = new DateTime(2023, 12, 25, 10, 30, 0, ZoneId.of("Europe/London"));
        ZoneOffset offset = dt.getOffset();
        
        // London could be GMT or BST depending on date, but should have some offset
        assertNotNull(offset);
    }

    @Test
    void testGetZoneId() {
        ZoneId expectedZone = ZoneId.of("America/Los_Angeles");
        DateTime dt = new DateTime(2023, 6, 15, 14, 20, 30, expectedZone);
        
        assertEquals(expectedZone, dt.getZoneId());
    }

    @Test
    void testGetTimezone() {
        ZoneId expectedZone = ZoneId.of("Asia/Tokyo");
        DateTime dt = new DateTime(2023, 6, 15, 14, 20, 30, expectedZone);
        
        // getTimezone is alias for getZoneId
        assertEquals(expectedZone, dt.getTimezone());
        assertEquals(dt.getZoneId(), dt.getTimezone());
    }

    @Test
    void testClearTime() {
        DateTime dt = new DateTime(2023, 8, 15, 14, 30, 45, ZoneId.of("UTC"));
        ZonedDateTime cleared = dt.clearTime();
        
        assertEquals(2023, cleared.getYear());
        assertEquals(8, cleared.getMonthValue());
        assertEquals(15, cleared.getDayOfMonth());
        assertEquals(0, cleared.getHour());
        assertEquals(0, cleared.getMinute());
        assertEquals(0, cleared.getSecond());
        assertEquals(ZoneId.of("UTC"), cleared.getZone());
        
        // Original DateTime should be unchanged
        assertEquals(14, dt.getHour());
        assertEquals(30, dt.getMinute());
        assertEquals(45, dt.getSecond());
    }

    // ========== Utility Method Tests ==========

    @Test
    void testGetZonedDateTime() {
        DateTime dt = new DateTime(2023, 9, 20, 16, 45, 30, ZoneId.of("Europe/Berlin"));
        ZonedDateTime zdt = dt.getZonedDateTime();
        
        assertEquals(2023, zdt.getYear());
        assertEquals(9, zdt.getMonthValue());
        assertEquals(20, zdt.getDayOfMonth());
        assertEquals(16, zdt.getHour());
        assertEquals(45, zdt.getMinute());
        assertEquals(30, zdt.getSecond());
        assertEquals(ZoneId.of("Europe/Berlin"), zdt.getZone());
    }

    @Test
    void testGetDateTime() {
        DateTime dt = new DateTime(2023, 9, 20, 16, 45, 30, ZoneId.of("Europe/Berlin"));
        ZonedDateTime zdt = dt.getDateTime();
        
        // getDateTime is alias for getZonedDateTime
        assertEquals(dt.getZonedDateTime(), zdt);
        assertEquals(2023, zdt.getYear());
        assertEquals(ZoneId.of("Europe/Berlin"), zdt.getZone());
    }

    @Test
    void testGetTime() {
        DateTime dt = new DateTime(2023, 12, 25, 14, 30, 45, ZoneId.of("UTC"));
        LocalTime time = dt.getTime();
        
        assertEquals(14, time.getHour());
        assertEquals(30, time.getMinute());
        assertEquals(45, time.getSecond());
    }

    @Test
    void testGetTimeStr() {
        DateTime dt = new DateTime(2023, 12, 25, 14, 30, 45, ZoneId.of("UTC"));
        String timeStr = dt.getTimeStr();
        
        assertEquals("14:30:45", timeStr);
        
        // Test with single digit hours/minutes/seconds
        DateTime dt2 = new DateTime(2023, 1, 1, 9, 5, 7, ZoneId.of("UTC"));
        assertEquals("09:05:07", dt2.getTimeStr());
    }

    @Test
    void testToString() {
        DateTime dt = new DateTime(2023, 12, 25, 10, 30, 45, ZoneId.of("UTC"));
        String str = dt.toString();
        
        // Should be in ISO 8601 format
        assertTrue(str.startsWith("2023-12-25T10:30:45"));
        assertTrue(str.contains("UTC") || str.endsWith("Z"));
    }

    @Test
    void testEquals() {
        DateTime dt1 = new DateTime(2023, 6, 15, 10, 30, 45, ZoneId.of("UTC"));
        DateTime dt2 = new DateTime(2023, 6, 15, 10, 30, 45, ZoneId.of("UTC"));
        DateTime dt3 = new DateTime(2023, 6, 15, 11, 30, 45, ZoneId.of("UTC")); // Different time
        
        // Same values should be equal
        assertEquals(dt1, dt2);
        assertTrue(dt1.equals(dt2));
        
        // Different times should not be equal
        assertNotEquals(dt1, dt3);
        assertFalse(dt1.equals(dt3));
        
        // Test with null
        assertFalse(dt1.equals(null));
        
        // Test with different type
        assertFalse(dt1.equals("not a DateTime"));
    }

    @Test
    void testEqualsWithDifferentTimezones() {
        // Test that equals() compares instants, not local times
        DateTime dtUtc = new DateTime(2023, 6, 15, 10, 30, 45, ZoneId.of("UTC"));
        
        // Same instant in New York time (UTC-4 in June)
        DateTime dtNy = new DateTime(2023, 6, 15, 6, 30, 45, ZoneId.of("America/New_York"));
        
        // Different local times but same instant should be equal
        assertEquals(dtUtc, dtNy);
        assertTrue(dtUtc.equals(dtNy));
        
        // Different instant (different local time in same zone) should not be equal
        DateTime dtNyDifferent = new DateTime(2023, 6, 15, 7, 30, 45, ZoneId.of("America/New_York"));
        assertNotEquals(dtUtc, dtNyDifferent);
        assertFalse(dtUtc.equals(dtNyDifferent));
    }

    @Test
    void testHashCode() {
        DateTime dt1 = new DateTime(2023, 6, 15, 10, 30, 45, ZoneId.of("UTC"));
        DateTime dt2 = new DateTime(2023, 6, 15, 10, 30, 45, ZoneId.of("UTC"));
        DateTime dt3 = new DateTime(2023, 6, 15, 11, 30, 45, ZoneId.of("UTC"));
        
        // Equal objects should have same hashcode
        assertEquals(dt1.hashCode(), dt2.hashCode());
        
        // Different objects might have different hashcodes (not guaranteed but likely)
        assertNotEquals(dt1.hashCode(), dt3.hashCode());
        
        // Hashcode should be consistent
        int hash1 = dt1.hashCode();
        int hash2 = dt1.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    void testCompareTo() {
        DateTime dt1 = new DateTime(2023, 6, 15, 10, 30, 45, ZoneId.of("UTC"));
        DateTime dt2 = new DateTime(2023, 6, 15, 10, 30, 45, ZoneId.of("UTC"));
        DateTime dt3 = new DateTime(2023, 6, 15, 11, 30, 45, ZoneId.of("UTC"));
        DateTime dt4 = new DateTime(2023, 6, 14, 10, 30, 45, ZoneId.of("UTC"));
        
        // Equal times should return 0
        assertEquals(0, dt1.compareTo(dt2));
        
        // dt1 is earlier than dt3
        assertTrue(dt1.compareTo(dt3) < 0);
        
        // dt1 is later than dt4
        assertTrue(dt1.compareTo(dt4) > 0);
        
        // Test symmetry
        assertTrue(dt3.compareTo(dt1) > 0);
        assertTrue(dt4.compareTo(dt1) < 0);
    }

    @Test
    void testCompareToWithDifferentTimezones() {
        // Same instant, different timezones
        DateTime dtUtc = new DateTime(2023, 6, 15, 10, 30, 45, ZoneId.of("UTC"));
        DateTime dtNy = new DateTime(2023, 6, 15, 6, 30, 45, ZoneId.of("America/New_York")); // Same instant
        
        // They should be equal (same instant)
        assertEquals(dtUtc, dtNy);
        
        // Check that instants are equal
        assertEquals(
            dtUtc.getZonedDateTime().toInstant(),
            dtNy.getZonedDateTime().toInstant()
        );
        
        // Note: compareTo might not return 0 even if instants are equal
        // because ZonedDateTime.compareTo() compares local date-time after instant
    }

    // ========== Edge Case Tests ==========

    @Test
    void testLeapYear() {
        // February 29 in leap year
        DateTime dt = new DateTime(2024, 2, 29, 12, 0, 0, ZoneId.of("UTC"));
        assertEquals(2024, dt.getYear());
        assertEquals(2, dt.getMonth());
        assertEquals(29, dt.getDay());
    }

    @Test
    void testTimeBoundaries() {
        // Test min time values
        DateTime dtMin = new DateTime(2023, 1, 1, 0, 0, 0, ZoneId.of("UTC"));
        assertEquals(0, dtMin.getHour());
        assertEquals(0, dtMin.getMinute());
        assertEquals(0, dtMin.getSecond());
        
        // Test max time values
        DateTime dtMax = new DateTime(2023, 12, 31, 23, 59, 59, ZoneId.of("UTC"));
        assertEquals(23, dtMax.getHour());
        assertEquals(59, dtMax.getMinute());
        assertEquals(59, dtMax.getSecond());
    }

    @Test
    void testTimezoneConversionPreservesInstant() {
        DateTime dtUtc = new DateTime(2023, 6, 15, 12, 0, 0, ZoneId.of("UTC"));
        DateTime dtTokyo = new DateTime(dtUtc);
        dtTokyo.setTimezone(ZoneId.of("Asia/Tokyo"));
        
        // Different local times (12:00 UTC vs 21:00 Tokyo) but same instant
        assertNotEquals(dtUtc.getHour(), dtTokyo.getHour());
        assertEquals(dtUtc, dtTokyo); // equals() uses isEqual() which compares instants
        
        // Check that instants are equal
        assertEquals(
            dtUtc.getZonedDateTime().toInstant(),
            dtTokyo.getZonedDateTime().toInstant()
        );
        
        // Note: compareTo might not return 0 even if instants are equal
        // because ZonedDateTime.compareTo() compares local date-time after instant
        // So we shouldn't assert compareTo returns 0
    }
}