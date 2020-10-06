package com.test.automation.bigtable.emulator;



import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
	
	 public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
	            .withZone(ZoneId.of("UTC"));
    public static long getTimestamp(DateTimeFormatter dateFormat, String date) throws DateTimeException {
        return ZonedDateTime.parse(date, dateFormat).toInstant().toEpochMilli();
    }

    public static String getDate(DateTimeFormatter dateFormat, long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.of("UTC")).format(dateFormat);
    }

    /***
     * Returns timestamp diff in milliseconds between two dates using the specified date format.
     * @param dateFormat The expected date format
     * @param start The start time
     * @param end The end time
     * @return Difference in time in milliseconds
     */
    public static long getTimestampDiff(DateTimeFormatter dateFormat, String start, String end) {
        long startTimestamp = getTimestamp(dateFormat, start);
        long endTimestamp = getTimestamp(dateFormat, end);

        return endTimestamp - startTimestamp;
    }

    /***
     * Returns a timestamp rounded to the nearest specified time period.
     * @param timestamp The timestamp to be rounded.
     * @param period The time period to round to.
     * @return A rounded timestamp to the specified time period.
     */
    public static long getRoundedTimestamp(long timestamp, int period) {
        return ((long) ((double) timestamp / period)) * period;
    }
}
