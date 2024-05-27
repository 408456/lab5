package ru.itmo.lab5.utility;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class DateTimeHandler {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static Instant parseDate(String dateString) {
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(dateString, FORMATTER);
            return localDateTime.toInstant(ZoneOffset.UTC);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Ошибка парсинга строки даты", e);
        }
    }

    public static Date instantToDate(Instant instant) {
        return Date.from(instant);
    }

    public static Instant dateToInstant(Date date) {
        return date.toInstant();
    }

    public static String formatInstantToString(Instant instant) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        return FORMATTER.format(dateTime);
    }
}
