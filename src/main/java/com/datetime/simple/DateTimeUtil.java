package com.datetime.simple;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;


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

    public static long monthsBetween(DateTime start, DateTime end){
        return ChronoUnit.MONTHS.between(
                start.getZonedDateTime().toLocalDate(),
                end.getZonedDateTime().toLocalDate()
        );
    }

    public static long yearsBetween(DateTime start, DateTime end){
        return ChronoUnit.YEARS.between(
                start.getZonedDateTime().toLocalDate(),
                end.getZonedDateTime().toLocalDate()
        );
    }


    public static long hoursBetween(DateTime start, DateTime end){
        return ChronoUnit.HOURS.between(
                start.getZonedDateTime().toLocalDate(),
                end.getZonedDateTime().toLocalDate()
        );
    }

    public static long minutesBetween(DateTime start, DateTime end){
        return ChronoUnit.MINUTES.between(
                start.getZonedDateTime().toLocalDate(),
                end.getZonedDateTime().toLocalDate()
        );
    }

    public static long secondsBetween(DateTime start, DateTime end){
        return ChronoUnit.SECONDS.between(
                start.getZonedDateTime().toLocalDate(),
                end.getZonedDateTime().toLocalDate()
        );
    }

    /**
     * Get the elapsed time between two DateTimes
     * @param start
     * @param end
     * @return
     */
    public static Duration getElapsedTime(DateTime start, DateTime end) {
        return Duration.between(
                start.getZonedDateTime().toLocalDate(),
                end.getZonedDateTime().toLocalDate()
        );
    }

    /**
     * Helper function to deal with durations
     * Returns a Duration object as %d days, %d hours, %d minutes, %d seconds String
     * @param duration
     * @return
     */
    public static String getFormattedDuration(Duration duration){
        long days = duration.toDays();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        return "%d days, %d hours, %d minutes, %d seconds"
                .formatted(days, hours, minutes, seconds);
    }

    /**
     * Get the latest date from a list of DateTimes
     */
    public static DateTime getLatest(List<DateTime> dates){
        return dates.stream()
                .max(DateTime::compareTo)
                .orElse(null);
    }

    /**
     * Get the earliest date from a list of DateTimes
     */
    public static DateTime getEarliest(List<DateTime> dates){
        return dates.stream()
                .min(DateTime::compareTo)
                .orElse(null);
    }

    public static List<DateTime> removeDuplicates(List<DateTime> dates){
        return dates.stream()
                .distinct()
                .toList();
    }

    public static List<DateTime> sort(List<DateTime> dates){
        return dates.stream()
                .sorted()
                .toList();
    }

    public static List<DateTime> sortDescending(List<DateTime> dates){
        return dates.stream()
                .sorted(Comparator.reverseOrder())
                .toList();
    }
}
