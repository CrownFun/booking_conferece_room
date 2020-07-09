package pl.filewicz.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateMapper {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static LocalDateTime toLocalDateTime(String date) {
        return LocalDateTime.parse(date, formatter);
    }
}
