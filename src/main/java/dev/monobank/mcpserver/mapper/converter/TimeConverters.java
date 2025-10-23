package dev.monobank.mcpserver.mapper.converter;

import dev.monobank.mcpserver.mapper.qualifier.TimestampToDate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeConverters {

    @TimestampToDate
    public static LocalDateTime timestampToDate(long timestamp) {
        return Instant.ofEpochSecond(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
