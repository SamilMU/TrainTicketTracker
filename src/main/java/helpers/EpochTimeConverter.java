package helpers;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class EpochTimeConverter {

    static String timeZone = "Asia/Baghdad";

    /**
     * Converts epoch time to human-readable date and time in a specified time zone.
     *
     * @param epochTimeMillis The epoch time in milliseconds.
     * @return A formatted string with the conversion result.
     */
    public static String convertEpochToDateTime(long epochTimeMillis) {
        try {
            // Convert epoch time to seconds
            long epochTimeSeconds = epochTimeMillis / 1000;

            // Convert epoch time to ZonedDateTime
            Instant instant = Instant.ofEpochMilli(epochTimeMillis);
            ZoneId zoneId = ZoneId.of(timeZone);
            ZonedDateTime zonedDateTime = instant.atZone(zoneId);

            // Format the date and time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE MMMM d, yyyy HH:mm:ss (a)");
            String formattedDate = zonedDateTime.format(formatter);

            // Get the time zone offset
            String zoneOffset = zonedDateTime.getOffset().getId();

            // Return the result string
//            return String.format("%d converts to %s in time zone %s (%s)", epochTimeSeconds, formattedDate, timeZone, zoneOffset);
            return formattedDate + " (" + timeZone + ")" + zoneOffset;
        } catch (Exception e) {
            return "Error: Unable to convert epoch time. " + e.getMessage();
        }
    }


}
