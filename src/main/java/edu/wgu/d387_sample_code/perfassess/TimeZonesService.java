package edu.wgu.d387_sample_code.perfassess;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TimeZonesService {
    public String getPresentationTimes(String timeZoneId) {
        LocalTime fixedTime = LocalTime.of(15, 0);

        ZonedDateTime eastern = ZonedDateTime.of(easternZonedDate(fixedTime), ZoneId.of("US/Eastern"));
        ZonedDateTime mountain = eastern.withZoneSameInstant(ZoneId.of("US/Mountain"));
        ZonedDateTime utc = eastern.withZoneSameInstant(ZoneId.of("UTC"));


        String easternFormatted = eastern.format(DateTimeFormatter.ofPattern("hh:mm a")) + " ET";
        String mountainFormatted = mountain.format(DateTimeFormatter.ofPattern("hh:mm a")) + " MT";
        String utcFormatted = utc.format(DateTimeFormatter.ofPattern("hh:mm a")) + " UTC";

        return switch (timeZoneId) {
            case "eastern" -> "The time of the live online presentation is: " + easternFormatted;
            case "mountain" -> "The time of the live online presentation is: " + mountainFormatted;
            case "utc" -> "The time of the live online presentation is: " + utcFormatted;
            default -> null;
        };
    }

    private LocalDateTime easternZonedDate(LocalTime fixedTime) {
        return ZonedDateTime.of(2025, 2, 18, fixedTime.getHour(), fixedTime.getMinute(), 0, 0, ZoneId.of("US/Eastern")).toLocalDateTime();
    }
}
