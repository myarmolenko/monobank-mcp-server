package dev.monobank.mcpserver.provider.tool;

import com.joestelmach.natty.DateGroup;
import lombok.RequiredArgsConstructor;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;
import com.joestelmach.natty.Parser;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeToolProvider {

    final Parser parser;

    @McpTool(
            name = "resolve_time_to_epoch",
            description = "Parse a natural-language date/time expression and return Unix epoch seconds (UTC)."
    )
    public long resolveTimeToEpochSeconds(
            @McpToolParam(
                    description = "Natural-language date/time expression (e.g. 'two weeks ago', 'December 1, 2025', '2026-01-15 14:30')"
            )
            final String time
    ) {
        if (time == null || time.isBlank()) {
            throw new IllegalArgumentException("time must be provided and not blank");
        }

        final String input = time.trim();
        final List<DateGroup> parsedDateGroups = parser.parse(input);

        if (parsedDateGroups == null || parsedDateGroups.isEmpty()) {
            throw new IllegalArgumentException("Could not parse time expression: " + input);
        }

        final Date resolved = parsedDateGroups.stream()
                .flatMap(group -> group.getDates().stream())
                .min(Comparator.naturalOrder())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Parsed expression produced no concrete date: " + input
                ));

        return resolved.toInstant().getEpochSecond();
    }
}
