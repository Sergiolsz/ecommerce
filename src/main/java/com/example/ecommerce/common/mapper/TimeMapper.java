package com.example.ecommerce.common.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class TimeMapper {

    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Convert LocalDateTime to String in ISO format.
     *
     * @param value the LocalDateTime to convert
     * @return the formatted String or null if input is null
     */
    public String ldtToString(LocalDateTime value) {
        return value == null ? null : value.format(ISO);
    }

    /**
     * Convert String in ISO format to LocalDateTime.
     *
     * @param value the String to convert
     * @return the LocalDateTime or null if input is null or blank
     */
    public LocalDateTime stringToLdt(String value) {
        return value == null || value.isBlank() ? null : LocalDateTime.parse(value, ISO);
    }

    /**
     * Identity mapping de CharSequence a String (útil para AVRO)
     *
     * @param value the CharSequence to convert
     * @return the String or null if input is null
     */
    public String charSequenceToString(CharSequence value) {
        return value == null ? null : value.toString();
    }

}