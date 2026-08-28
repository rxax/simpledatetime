package com.datetime.simple;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateTimeUtilTest {

    @Test
    public void daysBetweenTest(){
        DateTime start = new DateTime(2010,1,20,0,0,0, ZoneId.of("Europe/Dublin"));
        DateTime end = new DateTime(2010,2,10,0,0,0, ZoneId.of("Europe/Dublin"));
        //System.out.println("" + start + end);
        long result = DateTimeUtil.daysBetween(start, end);
        //System.out.println(result);
        assertEquals(21, result);

        long result2 = DateTimeUtil.daysBetween(end, start);
        assertEquals(-21, result2);

    }
}
