package com.crm.gym.app.util;

import com.crm.gym.app.model.exception.ParseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static com.crm.gym.app.util.Constants.PARSE_INVALID_BOOLEAN;
import static com.crm.gym.app.util.Constants.PARSE_INVALID_FORMAT_DATE;
import static com.crm.gym.app.util.Constants.PARSE_INVALID_FORMAT_DATETIME;
import static com.crm.gym.app.util.Constants.PARSE_INVALID_NUMBER;
import static com.crm.gym.app.util.Constants.PARSE_NULL_VALUE;
import static java.util.Objects.isNull;

@Component
@Slf4j
@RequiredArgsConstructor
public class ParseUtils {

    private final LoggingMessageUtils messageUtils;

    public void checkNotNull(String value) {
        if (isNull(value)) {
            throw logAndReturnParseException(messageUtils.getMessage(PARSE_NULL_VALUE));
        }
    }

    public int parseInt(String value) {
        checkNotNull(value);

        try {
            return Integer.parseInt(value);

        } catch (NumberFormatException e) {
            throw logAndReturnParseException(messageUtils.getMessage(PARSE_INVALID_NUMBER, value));
        }
    }

    public long parseLong(String value) {
        checkNotNull(value);

        try {
            return Long.parseLong(value);

        } catch (NumberFormatException e) {
            throw logAndReturnParseException(messageUtils.getMessage(PARSE_INVALID_NUMBER, value));
        }
    }

    public boolean parseBoolean(String value) {
        checkNotNull(value);

        try {
            return Boolean.parseBoolean(value);

        } catch (NumberFormatException e) {
            throw logAndReturnParseException(messageUtils.getMessage(PARSE_INVALID_BOOLEAN, value));
        }
    }

    public LocalDate parseDate(String value) {
        checkNotNull(value);

        try {
            return LocalDate.parse(value);

        } catch (DateTimeParseException e) {
            throw logAndReturnParseException(messageUtils.getMessage(PARSE_INVALID_FORMAT_DATE, value));
        }
    }

    public LocalDateTime parseDateTime(String value) {
        checkNotNull(value);

        try {
            return LocalDateTime.parse(value);

        } catch (DateTimeParseException e) {
            throw logAndReturnParseException(messageUtils.getMessage(PARSE_INVALID_FORMAT_DATETIME, value));
        }
    }

    private ParseException logAndReturnParseException(String message) {
        log.error(message);

        return new ParseException(message);
    }
}
